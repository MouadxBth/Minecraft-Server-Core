package me.mao.system.data.redis;

import me.mao.core.Core;
import me.mao.core.utils.Config;

public class RedisInfo {

    private String communicator;

    private Config settings;

    private RedisCredentials redisCredentials;

    protected RedisInfo(String communicator) {
        this.communicator = communicator;
        this.settings = new Config("settings");
        getRedisInfo();
    }

    protected void getRedisInfo() {
        if (settings.getKeys().isEmpty()) {
            settings.set(communicator + ".redis.ip", "");
            settings.set(communicator + ".redis.password", "");
            settings.set(communicator + ".redis.port", "");
            settings.set(communicator + ".redis.clientName", "");
            return;
        }

        String ip = settings.getString(communicator + ".redis.ip");
        String password = settings.getString(communicator + ".redis.password");
        String clientName = settings.getString(communicator + ".redis.clientName");
        int port = settings.getInteger(communicator + ".redis.port");

        redisCredentials = new RedisCredentials(ip, port, password, clientName);
    }

    protected RedisCredentials getRedisCredentials() {
        return redisCredentials;
    }
}
