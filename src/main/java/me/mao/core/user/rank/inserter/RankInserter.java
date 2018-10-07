package me.mao.core.user.rank.inserter;

import me.mao.core.Core;
import me.mao.core.user.rank.Rank;
import me.mao.core.utils.Messenger;
import me.mao.system.data.mysql.async.Update;
import me.mao.system.data.redis.async.RedisUpdate;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RankInserter {

    private Rank rank;

    private Connection connection;
    private RedissonClient redissonClient;

    private Core core = Core.getInstance();


    public RankInserter(Rank rank) {
        this.rank = rank;
    }

    public RankInserter setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public RankInserter setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        return this;
    }

    public RankInserter toSql() {
        if(connection == null) connection = core.getCommunicator().getMySQLConnection().getMySQLAcces().getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO 'ranks_data' VALUES(?, ?, ?, ?, ?, ?) ON DUPLICATE UPDATE name=?");
            ps.setString(1,rank.getName());
            ps.setString(2, rank.getPrefix());
            ps.setString(3, rank.getSuffix());
            ps.setBoolean(4, rank.isStaff());
            ps.setBoolean(5, rank.isDonator());
            ps.setString(6, rank.getPermissions().toString().replace("[","").replace("]", ""));
            ps.setString(7,rank.getName());

            core.getProxy().getScheduler().runAsync(core, new Update(ps, connection, ((result, thrown) -> {

                if(thrown == null) {
                    if(result != null) {
                        Messenger.info("&aSuccessufly inserted a rank into the Sql database!");
                        return;
                    }
                }

            })));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public RankInserter toRedis() {
        if(redissonClient == null) redissonClient = core.getCommunicator().getRedisConnection().getRedisAcces().getRedissonClient();
        final String key = "ranks:" + rank.getName();

        core.getProxy().getScheduler().runAsync(core, new RedisUpdate(redissonClient, key, rank, ((result, thrown) -> {
            if(thrown == null) {
                if(result != null) {
                    Messenger.info("&aSuccessufly inserted a rank into the Redis cache!");
                    return;
                }
            }
        })));

        return this;
    }

    public RankInserter sofStave() {
        toRedis();
        return this;
    }

    public RankInserter save() {
        toRedis();
        toSql();
        return this;
    }


}
