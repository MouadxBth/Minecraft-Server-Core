package me.mao.system.communicator;

import me.mao.core.Core;
import me.mao.system.data.mysql.MySQLConnection;
import me.mao.system.data.redis.RedisConnection;
import me.mao.system.exceptions.MySqlNotConnectedException;
import me.mao.system.exceptions.RedisNotConnectedException;

public class Communicator {

    private MySQLConnection mySQLConnection;
    private RedisConnection redisConnection;

    private Core core;

    public Communicator(Core core) {
        this.core = core;
        this.mySQLConnection = new MySQLConnection();
        this.redisConnection = new RedisConnection();
    }

    public void startConnection() {
        if(!mySQLConnection.startConnection()) {
            try {
                throw new MySqlNotConnectedException(core);
            } catch (MySqlNotConnectedException e) {
                e.printStackTrace();
            }
            return;
        }
        if(!redisConnection.startConnection()) {
            try {
                throw new RedisNotConnectedException(core);
            } catch (RedisNotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopConnection() {
        mySQLConnection.stopConnection();
        redisConnection.stopConnection();
    }

    public boolean isMySQLConnected() {
        return mySQLConnection.isConnected();
    }

    public boolean isRedisConnected() {
        return redisConnection.isConnected();
    }

    public MySQLConnection getMySQLConnection() {
        return mySQLConnection;
    }

    public void setMySQLConnection(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    public RedisConnection getRedisConnection() {
        return redisConnection;
    }

    public void setRedisConnection(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }
}
