package me.mao.system.data.redis.async;

import me.mao.system.data.Callback;
import me.mao.system.data.redis.RedisAcces;
import org.redisson.api.RBucket;

public class RedisQuery implements Runnable {

    private RedisAcces redisAcces;
    private String key;
    private Object value;
    private RBucket<Object> bucket;
    private Callback<RBucket<?>, Exception> callback;

    private boolean exists;


    public RedisQuery(RedisAcces redisAcces, String key, Object value, Callback<RBucket<?>, Exception> callback) {
        this.redisAcces = redisAcces;
        this.key = key;
        this.value = value;
        this.callback = callback;
    }

    @Override
    public void run() {
        bucket = redisAcces.getRedissonClient().getBucket(key);

        if((!bucket.isExists()) || bucket == null) {
            exists = false;
            try {
                throw new Exception("Could not find a redis object with the key: " + key);
            } catch (Exception e) {
                e.printStackTrace();
                callback.call(null, e);
            }
            return;
        }
        callback.call(bucket, null);
        exists = true;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public RBucket<Object> getBucket() {
        return bucket;
    }

    public boolean isExists() {
        return exists;
    }

    public void setRedisAcces(RedisAcces redisAcces) {
        this.redisAcces = redisAcces;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
