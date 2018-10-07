package me.mao.core.user.rank.provider;

import me.mao.core.Core;
import me.mao.core.user.rank.Rank;
import me.mao.core.user.rank.inserter.RankInserter;
import me.mao.core.utils.Messenger;
import me.mao.system.data.mysql.async.Query;
import me.mao.system.data.redis.async.RedisQuery;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RankProvider {

    private Core core;
    private String rankName;


    public RankProvider(String rankName) {
        this.core = core = Core.getInstance();
        this.rankName = rankName;
    }

    public Rank getRank() {
        Connection connection = core.getCommunicator().getMySQLConnection().getMySQLAcces().getConnection();
        RedissonClient redissonClient = core.getCommunicator().getRedisConnection().getRedisAcces().getRedissonClient();

        Rank rank = getFromRedis();

        if (rank == null) {

            rank = getFromSql();
            new RankInserter(rank).setRedissonClient(redissonClient).toRedis();

            if (rank == null) {
                new RankInserter(rank).setConnection(connection).setRedissonClient(redissonClient).toSql().toRedis();
            }
        }

        return rank;
    }

    private Rank getFromRedis() {
        final RedissonClient redissonClient = core.getCommunicator().getRedisConnection().getRedisAcces().getRedissonClient();
        final String key = "ranks:" + rankName;
        final Rank[] rank = new Rank[]{};

        core.getProxy().getScheduler().runAsync(core, new RedisQuery(redissonClient, key, ((result, thrown) -> {
            if(thrown == null) {
                if(result != null) rank[0] = (Rank) result.get();
            }
        })));

        return rank[0];
    }

    private Rank getFromSql() {
        final Rank[] rank = new Rank[]{};

        try {
            Connection connection = core.getCommunicator().getMySQLConnection().getMySQLAcces().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM 'ranks_data' WHERE name=?");

            preparedStatement.setString(1, rankName);

            core.getProxy().getScheduler().runAsync(core, new Query(preparedStatement, connection, ((result, thrown) -> {
                if (thrown != null) {
                    try {
                        if (result.next()) {
                            String name = result.getString("name");
                            String prefix = result.getString("prefix");
                            String suffix = result.getString("suffix");
                            boolean staff = result.getBoolean("staff");
                            boolean donator = result.getBoolean("donator");
                            String permissions = result.getString("permissions");

                            List<String> list = Arrays.asList(permissions);
                            LinkedList<String> perms = new LinkedList<>();
                            perms.addAll(list);

                            rank[0] = (new Rank(name, prefix, suffix, staff, donator, perms));
                        } else {
                            rank[0] = (getDefaultRank());
                            Messenger.warn("&cCould not find a rank with the name: {" + rankName + "} returned the default rank");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            })));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rank[0];
    }

    public Rank getDefaultRank() {
        return new Rank("regular", "&7", "", false, false, new LinkedList<>());
    }
}
