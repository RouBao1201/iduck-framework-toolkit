package com.iduck.sybin.toolkit.lock.factory.config;

import com.iduck.sybin.toolkit.lock.factory.DCLockerBuilder;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 分布式锁构造器配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
@Configuration
public class DCLockerBuilderConfig {

    @Bean
    @ConditionalOnMissingBean(DCLockerBuilder.class)
    public DCLockerBuilder Locker(StringRedisTemplate stringRedisTemplate, RedissonClient redissonClient) {
        return new DCLockerBuilder(stringRedisTemplate, redissonClient);
    }
}
