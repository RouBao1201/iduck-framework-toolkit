package com.iduck.sybin.toolkit.lock.redisson.locker;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/25
 **/
public class RedissonLocker {
    private static final Logger log = LoggerFactory.getLogger(RedissonLocker.class);

    private static final String LOCK_KEY_PREFIX = "lock:";

    private String lockKey;

    private RedissonClient redissonClient;

    private long expire = 30;

    public boolean tryLock() {
        return tryLock(-1, TimeUnit.SECONDS);
    }

    public boolean tryLock(long expire, TimeUnit timeUnit) {
        if (expire != -1) {
            setExpire(expire);
        }
        RLock lock = this.redissonClient.getLock(LOCK_KEY_PREFIX + this.lockKey);
        try {
            return lock.tryLock(timeUnit.toSeconds(this.expire), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("RedissonLocker ==> TryLock error. ErrorMessage:{}", e.getMessage());
            return false;
        }
    }

    public void unLock() {
        this.redissonClient.getLock(LOCK_KEY_PREFIX + this.lockKey).unlock();
    }

    public RedissonLocker(String lockKey, RedissonClient redissonClient) {
        this.lockKey = lockKey;
        this.redissonClient = redissonClient;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
