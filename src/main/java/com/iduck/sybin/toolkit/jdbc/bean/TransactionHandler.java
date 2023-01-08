package com.iduck.sybin.toolkit.jdbc.bean;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * 事务管理器工具
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/28
 **/
public class TransactionHandler {

    private final DataSourceTransactionManager dataSourceTransactionManager;

    public TransactionHandler(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    /**
     * 开启事务
     *
     * @return 事务状态
     */
    public TransactionStatus begin() {
        return beginTransaction(this.dataSourceTransactionManager);
    }

    /**
     * 提交事务
     *
     * @param status 事务状态
     */
    public void commit(TransactionStatus status) {
        this.dataSourceTransactionManager.commit(status);
    }

    /**
     * 回滚事务
     *
     * @param status 事务状态
     */
    public void rollback(TransactionStatus status) {
        this.dataSourceTransactionManager.rollback(status);
    }

    public static void rollbackTransaction(DataSourceTransactionManager dataSourceTransactionManager, TransactionStatus status) {
        dataSourceTransactionManager.rollback(status);
    }

    public static void commitTransaction(DataSourceTransactionManager dataSourceTransactionManager, TransactionStatus status) {
        dataSourceTransactionManager.commit(status);
    }

    public static TransactionStatus beginTransaction(DataSourceTransactionManager dataSourceTransactionManager) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return dataSourceTransactionManager.getTransaction(def);
    }
}
