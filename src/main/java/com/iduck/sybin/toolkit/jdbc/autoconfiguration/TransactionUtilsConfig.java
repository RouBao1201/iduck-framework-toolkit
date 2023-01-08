package com.iduck.sybin.toolkit.jdbc.autoconfiguration;

import com.iduck.sybin.toolkit.jdbc.util.TransactionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 事务助手配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/28
 **/
@Configuration
public class TransactionUtilsConfig {

    @Bean
    @ConditionalOnMissingBean(TransactionUtils.class)
    public TransactionUtils transactionUtils(DataSourceTransactionManager dataSourceTransaction) {
        return new TransactionUtils(dataSourceTransaction);
    }
}
