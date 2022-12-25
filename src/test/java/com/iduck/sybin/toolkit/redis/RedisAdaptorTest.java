package com.iduck.sybin.toolkit.redis;

import com.iduck.sybin.toolkit.redis.util.RedisAdaptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisAdaptorTest {

    @Autowired
    RedisAdaptor redisAdaptor;

    @Test
    public void test01() {
        redisAdaptor.set("boss", "SongYanBin");
        redisAdaptor.set("boss-ZH", "宋延彬");
    }
}
