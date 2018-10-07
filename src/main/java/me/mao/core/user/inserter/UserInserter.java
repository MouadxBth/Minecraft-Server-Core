package me.mao.core.user.inserter;

import me.mao.core.Core;
import me.mao.core.user.User;
import me.mao.core.utils.Messenger;
import me.mao.system.data.mysql.async.Update;
import me.mao.system.data.redis.async.RedisUpdate;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInserter {

    private User user;
    private RedissonClient redissonClient;
    private Connection connection;

    private Core core = Core.getInstance();

    public UserInserter(User user) {
        this.user = user;
    }

    public UserInserter setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public UserInserter setRedisClient(RedissonClient redisClient) {
        this.redissonClient = redisClient;
        return this;
    }

    public UserInserter toSql() {
        if(connection == null) connection = core.getCommunicator().getMySQLConnection().getMySQLAcces().getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO 'players_data' VALUES(?, ?, ?, ?, ?) ON DUPLICATE UPDATE uuid=?");

            ps.setString(1, user.getUuid().toString());
            ps.setInt(2, user.getLevel());
            ps.setInt(3, user.getCoins());
            ps.setDouble(4, user.getExp());
            ps.setString(5, user.getRank().getName());
            ps.setString(6, user.getSubrank().getName());
            ps.setString(7, user.getUuid().toString());

            core.getProxy().getScheduler().runAsync(core, new Update(ps, connection, ((result, thrown) -> {
                if(thrown == null) {
                    if(result != null) {
                        Messenger.info("&aSuccessufly inserted a User to the Sql database");
                        return;
                    }
                }
            })));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public UserInserter toRedis() {
        if(redissonClient == null) redissonClient = core.getCommunicator().getRedisConnection().getRedisAcces().getRedissonClient();

        final String key = "accounts:" + user.getUuid();

        core.getProxy().getScheduler().runAsync(core, new RedisUpdate(redissonClient, key, user, ((result, thrown) -> {
            if(thrown == null) {
                if(result != null) {
                    Messenger.info("&aSuccessufly inserted a User to the Sql database");
                    return;
                }
            }
        })));

        return this;
    }

    public UserInserter softSave() {
        toRedis();
        return this;
    }

    public UserInserter save() {
        toSql();
        toRedis();
        return this;
    }

}
