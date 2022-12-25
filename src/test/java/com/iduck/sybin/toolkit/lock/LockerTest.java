package com.iduck.sybin.toolkit.lock;

import com.iduck.sybin.toolkit.lock.factory.DCLockerBuilder;
import com.iduck.sybin.toolkit.lock.redis.locker.RedisLocker;
import com.iduck.sybin.toolkit.lock.redisson.locker.RedissonLocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/24
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class LockerTest {

    @Autowired
    DCLockerBuilder dcLocker;

    @Test
    public void testRedisLocker() {
        RedisLocker lttLocker = dcLocker.redisLocker("ltt");
        lttLocker.tryLock();
    }

    @Test
    public void testRedissonLocker() {
        RedissonLocker redisson = dcLocker.redissonLocker("syb");
        redisson.tryLock();
    }

}
