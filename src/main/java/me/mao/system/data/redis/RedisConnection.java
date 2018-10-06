package me.mao.system.data.redis;

public class RedisConnection {

    private RedisAcces redisAcces;
    private RedisInfo redisInfo;

    public RedisConnection() {
    }

    public boolean startConnection() {
        this.redisInfo = new RedisInfo("communicator");
        this.redisAcces = new RedisAcces(redisInfo.getRedisCredentials());
        redisAcces.init();
        return true;
    }

    public boolean stopConnection() {
        if(redisAcces == null) return false;
        if(redisAcces.getRedissonClient().isShutdown()) return true;
        redisAcces.shutdown();
        return true;
    }

    public boolean isConnected() {
        return redisAcces.isConnected();
    }

    public RedisAcces getRedisAcces() {
        return redisAcces;
    }
}
