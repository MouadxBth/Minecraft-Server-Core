package me.mao.core.user.provider;

import me.mao.core.Core;

import me.mao.core.user.User;
import me.mao.core.user.inserter.UserInserter;
import me.mao.core.user.rank.Rank;
import me.mao.core.user.rank.provider.RankProvider;
import me.mao.system.data.mysql.MySQLConnection;
import me.mao.system.data.mysql.async.Query;
import me.mao.system.data.redis.RedisAcces;
import me.mao.system.data.redis.RedisConnection;
import me.mao.system.data.redis.async.RedisQuery;
import me.mao.system.exceptions.UserNotFoundException;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class UserProvider {

    private Core core;
    private UUID uuid;

    private Connection connection;
    private RedissonClient redissonClient;

    public UserProvider(ProxiedPlayer proxiedPlayer) {
        this.core = Core.getInstance();
        this.uuid = proxiedPlayer.getUniqueId();
    }

    public UserProvider(UUID uuid) {
        this.core = Core.getInstance();
        this.uuid = uuid;
    }


    public User getUser() throws UserNotFoundException {
        connection = core.getCommunicator().getMySQLConnection().getMySQLAcces().getConnection();
        redissonClient = core.getCommunicator().getRedisConnection().getRedisAcces().getRedissonClient();

        User user = getFromRedis();

        if (user == null) {

            user = getFromSql();
            new UserInserter(user).setRedisClient(redissonClient).toRedis();

            if (user == null) {

                new UserInserter(user).setConnection(connection).setRedisClient(redissonClient).toSql().toRedis();
            }
        }

        return user;

    }

    private User getFromRedis() {

        final String key = "accounts:" + uuid.toString();
        final RBucket<User> userRBucket = redissonClient.getBucket(key);
        final User[] user = new User[]{};

        core.getProxy().getScheduler().runAsync(core, new RedisQuery(redissonClient, key, ((result, thrown) -> {
            if(thrown == null) {
                if(result != null) {
                    user[0] = (User) result.get();
                }
            }
        })));

        return user[0];
    }


    private User getFromSql() {

        final PreparedStatement preparedStatement;
        final User[] user = new User[]{};

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM 'players_data' WHERE uuid=?");
            String uuid = this.uuid.toString();
            preparedStatement.setString(1, uuid);

            core.getProxy().getScheduler().runAsync(core, new Query(preparedStatement, connection, (((result, thrown) ->  {
                if (thrown == null) {
                    try {
                        if(result.next()) {
                            int level = result.getInt("level");
                            int coins = result.getInt("coins");
                            double exp = result.getDouble("exp");
                            Rank rank = new RankProvider(result.getString("rank")).getRank();
                            Rank subrank = new RankProvider(result.getString("subrank")).getRank();
                            List<String> list = Arrays.asList(result.getString("permissions"));
                            LinkedList<String> perms = new LinkedList<>();
                            perms.addAll(list);

                            user[0] = new User(UUID.fromString(uuid), level, coins, exp, rank, subrank, perms);
                        }else {
                            Rank rank = new RankProvider("").getDefaultRank();
                            Rank subrank = new RankProvider("").getDefaultRank();
                            LinkedList<String> perms = new LinkedList<>();
                            perms.addAll(rank.getPermissions());
                            user[0] = new User(UUID.fromString(uuid), 0, 0, 0, rank, subrank, perms);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }))));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user[0];
    }

}
