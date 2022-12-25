package com.iduck.sybin.toolkit.lock.factory;

import com.iduck.sybin.toolkit.lock.redis.locker.RedisLocker;
import com.iduck.sybin.toolkit.lock.redisson.locker.RedissonLocker;
import com.iduck.sybin.toolkit.lock.zookeeper.locker.ZookeeperLocker;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 分布式锁持有工具类 Distributed Controller
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
public class DCLockerBuilder {

    private final StringRedisTemplate stringRedisTemplate;

    private final RedissonClient redissonClient;

    public DCLockerBuilder(StringRedisTemplate stringRedisTemplate, RedissonClient redissonClient) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redissonClient = redissonClient;
    }

    /**
     * 构建redis分布式锁
     *
     * @param key 锁key
     * @return redis分布式锁
     */
    public RedisLocker redisLocker(String key) {
        return new RedisLocker(key, this.stringRedisTemplate);
    }

    public ZookeeperLocker zkLocker() {
        return new ZookeeperLocker();
    }

    /**
     * 构键redisson分布式锁
     *
     * @param key 锁key
     * @return redisson分布式锁
     */
    public RedissonLocker redissonLocker(String key) {
        return new RedissonLocker(key, this.redissonClient);
    }
}
