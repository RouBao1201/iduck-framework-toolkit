package com.iduck.sybin.toolkit.security.autoconfiguration;

import com.iduck.sybin.toolkit.security.properties.SecurityKeyProperties;
import com.iduck.sybin.toolkit.security.bean.AESUtil;
import com.iduck.sybin.toolkit.security.bean.MD5Util;
import com.iduck.sybin.toolkit.security.bean.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 安全加密工具自动装配类
 *
 * @author SongYanBin
 * @copyright ©2022-2099 SongYanBin. All rights reserved.
 * @since 2022/12/9
 **/
@Configuration
@EnableConfigurationProperties(SecurityKeyProperties.class)
public class SecurityAutoConfiguration {

    @Autowired
    SecurityKeyProperties securityKeyProperties;

    @Bean("aesUtils")
    @ConditionalOnMissingBean(AESUtil.class)
    public AESUtil aesUtils() {
        return new AESUtil(securityKeyProperties);
    }

    @Bean("md5Utils")
    @ConditionalOnMissingBean(MD5Util.class)
    public MD5Util md5Utils() {
        return new MD5Util(securityKeyProperties);
    }

    @Bean("rsaUtils")
    @ConditionalOnMissingBean(RSAUtil.class)
    public RSAUtil rsaUtils() {
        return new RSAUtil(securityKeyProperties);
    }
}
