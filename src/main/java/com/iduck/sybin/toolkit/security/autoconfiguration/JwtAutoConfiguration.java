package com.iduck.sybin.toolkit.security.autoconfiguration;

import com.iduck.sybin.toolkit.security.properties.JwtProperties;
import com.iduck.sybin.toolkit.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jwt自动装配
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/9
 **/
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {

    @Autowired
    JwtProperties jwtProperties;

    @Bean("jwtUtils")
    @ConditionalOnMissingBean(JwtUtils.class)
    public JwtUtils jwtUtils() {
        return new JwtUtils(jwtProperties);
    }

}
