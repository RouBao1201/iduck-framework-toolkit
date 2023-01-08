package com.iduck.sybin.toolkit.thread.autoconfiguration;

import com.iduck.sybin.toolkit.thread.properties.ThreadPoolProperties;
import com.iduck.sybin.toolkit.thread.bean.ThreadPoolHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 线程池工具自动装配类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolAutoConfiguration {

    @Autowired
    ThreadPoolProperties threadPoolProperties;

    @Bean("threadPoolHolder")
    @ConditionalOnMissingBean(ThreadPoolHolder.class)
    public ThreadPoolHolder threadPoolHolder() {
        return new ThreadPoolHolder(threadPoolProperties);
    }
}
