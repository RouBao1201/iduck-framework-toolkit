package com.iduck.sybin.toolkit.lock.redisson.autoconfiguration;

import cn.hutool.core.util.StrUtil;
import com.iduck.sybin.toolkit.lock.redisson.properties.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/25
 **/
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Bean("redissonClient")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(RedissonProperties redissonProperties) {
        Config config = new Config();
        String model = redissonProperties.getModel();
        switch (model) {
            case "single":
                config = createSingleConfig(redissonProperties);
                break;
            default:
                break;
        }
        return Redisson.create(config);
    }

    private Config createSingleConfig(RedissonProperties properties) {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(RedissonProperties.ADDRESS_PREFIX + properties.getAddress());
        if (StrUtil.isNotEmpty(properties.getUsername()) && StrUtil.isNotEmpty(properties.getPassword())) {
            singleServerConfig.setUsername(properties.getUsername())
                    .setPassword(properties.getPassword());
        }
        if (properties.getDatabase() != null) {
            singleServerConfig.setDatabase(properties.getDatabase());
        }
        return config;
    }
}
