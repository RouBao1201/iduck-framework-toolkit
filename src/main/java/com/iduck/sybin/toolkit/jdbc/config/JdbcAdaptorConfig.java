package com.iduck.sybin.toolkit.jdbc.config;

import com.iduck.sybin.toolkit.jdbc.util.JdbcAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JdbcAdaptor工具配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/28
 **/
@Configuration
public class JdbcAdaptorConfig {

    @Bean
    @ConditionalOnMissingBean(JdbcAdapter.class)
    public JdbcAdapter jdbcAdaptor(JdbcTemplate jdbcTemplate) {
        return new JdbcAdapter(jdbcTemplate);
    }
}
