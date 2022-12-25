package com.iduck.sybin.toolkit.lock.redis.locker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis锁(可重入锁)
 * 采用lua脚本的形式保证原子性
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
public class RedisLocker {
    private static final Logger log = LoggerFactory.getLogger(RedisLocker.class);

    private static final String LOCK_KEY_PREFIX = "lock:";

    private final String lockKey;

    private final String singleId;

    private final StringRedisTemplate stringRedisTemplate;

    private long expire = 30;

    public RedisLocker(String lockKey, StringRedisTemplate stringRedisTemplate) {
        this.lockKey = lockKey;
        this.stringRedisTemplate = stringRedisTemplate;
        this.singleId = Thread.currentThread().getId() + ":" + UUID.randomUUID();
    }

    /**
     * 加锁(锁过期时间为默认30s)
     *
     * @return 是否成功加锁
     */
    public boolean tryLock() {
        return tryLock(-1, TimeUnit.SECONDS);
    }

    /**
     * 加锁
     *
     * @param expire   锁过期时间
     * @param timeUnit 时间单位
     * @return 是否成功加锁
     */
    public boolean tryLock(long expire, TimeUnit timeUnit) {
        // 若没有设置过期时间,则设置默认值30s（避免死锁）
        if (expire != -1) {
            this.expire = expire;
        }
        String luaScript = "if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 " +
                "then " +
                "   redis.call('hincrby', KEYS[1], ARGV[1], 1) " +
                "   redis.call('expire', KEYS[1], ARGV[2]) " +
                "   return 1 " +
                "else " +
                "   return 0 " +
                "end";

        List<String> keys = Arrays.asList(LOCK_KEY_PREFIX + this.lockKey);
        String expireTime = String.valueOf(timeUnit.toSeconds(this.expire));

        // 循环获取锁(1s获取一次),直到获取到锁为止
        while (Boolean.FALSE.equals(this.stringRedisTemplate.execute(new DefaultRedisScript<>(luaScript, Boolean.class), keys, this.singleId, expireTime))) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("RedisLocker ==> TryLock success. The lockKey is:[{}]", this.lockKey);
        return true;
    }

    /**
     * 解锁
     */
    public void unLock() {
        String luaScript = "if redis.call('hexists', KEYS[1], ARGV[1]) == 0 " +
                "then " +
                "   return nil " +
                "elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 " +
                "   then return redis.call('del', KEYS[1]) " +
                "else " +
                "   return 0 " +
                "end";
        List<String> keys = Arrays.asList(LOCK_KEY_PREFIX + this.lockKey);
        Long execute = this.stringRedisTemplate.execute(new DefaultRedisScript<>(luaScript, Long.class), keys, this.singleId);

        // 若尝试解开并不属于你的锁则抛出异常
        if (execute == null) {
            throw new IllegalMonitorStateException("RedisLock ==> This lock doesn't belong to you!");
        }

//        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] " +
//                "then " +
//                "   return redis.call('del', KEYS[1]) " +
//                "else " +
//                "   return 0 " +
//                "end";
//        this.redisTemplate.execute(new DefaultRedisScript<>(luaScript, Boolean.class),
//                Collections.singletonList(lockKey), this.uuid);

//        String lockValue = (String) this.redisTemplate.opsForValue().get(LOCK_KEY_PREFIX + lockKey);
//        if (value.equals(lockValue)) {
//            this.redisTemplate.delete(LOCK_KEY_PREFIX + lockKey);
    }
}
