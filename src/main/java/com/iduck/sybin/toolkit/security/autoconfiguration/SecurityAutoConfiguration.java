package com.iduck.sybin.toolkit.security.autoconfiguration;

import com.iduck.sybin.toolkit.security.properties.SecurityKeyProperties;
import com.iduck.sybin.toolkit.security.util.AESUtils;
import com.iduck.sybin.toolkit.security.util.MD5Utils;
import com.iduck.sybin.toolkit.security.util.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SongYanBin
 * @copyright ©2022-2099 SongYanBin. All rights reserved.
 * @since 2022/12/9
 **/
@Configuration
@EnableConfigurationProperties(SecurityKeyProperties.class)
public class SecurityAutoConfiguration {

    @Autowired
    SecurityKeyProperties securityKeyProperties;

    @Bean
    @ConditionalOnMissingBean(AESUtils.class)
    public AESUtils aesUtils() {
        return new AESUtils(securityKeyProperties);
    }

    @Bean
    @ConditionalOnMissingBean(MD5Utils.class)
    public MD5Utils md5Utils() {
        return new MD5Utils(securityKeyProperties);
    }

    @Bean
    @ConditionalOnMissingBean(RSAUtils.class)
    public RSAUtils rsaUtils() {
        return new RSAUtils(securityKeyProperties);
    }
}
