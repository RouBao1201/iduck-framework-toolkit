package com.iduck.sybin.toolkit.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.iduck.sybin.toolkit.jdbc.bean.JdbcAdapter;
import com.iduck.sybin.toolkit.jdbc.bean.TransactionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * JdbcTemplate测试
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/28
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcAdaptorTest {

    @Autowired
    JdbcAdapter jdbcAdaptor;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TransactionUtils transactionUtils;

    /**
     * 测试主键冲突异常,未处理事务回滚
     */
    @Test
    public void testError() {
        try {
            ArrayList<String> dataList = new ArrayList<>();
            dataList.add("song");
            dataList.add("song");
            jdbcAdaptor.batchInsert("insert into userinfo (username) values (?)", dataList, 1,
                    (ps, data) -> {
                        ps.setObject(1, data);
                    });
            System.out.println("插入成功!");
        } catch (Exception e) {
            System.out.println("插入失败,事务没有回滚!");
        }
    }

    /**
     * 方式一：手动控制事务（异常回滚）
     */
    @Test
    public void test01() {
        TransactionStatus status = transactionUtils.begin();
        try {
            ArrayList<String> dataList = new ArrayList<>();
            dataList.add("song");
            dataList.add("song");
            jdbcAdaptor.batchInsert("insert into userinfo (username) values (?)",
                    dataList, 1,
                    (ps, data) -> {
                        ps.setObject(1, data);
                    });
            transactionUtils.commit(status);
            System.out.println("插入成功!");
        } catch (Exception e) {
            System.out.println("插入失败,手动事务回滚!");
            transactionUtils.rollback(status);
        }
    }

    /**
     * 方式二：声明式事务（异常回滚）
     */
    @Test
    @Transactional
    public void test02() {
        try {
            ArrayList<String> dataList = new ArrayList<>();
            dataList.add("song");
            dataList.add("song");
            jdbcAdaptor.batchInsert("insert into userinfo (username) values (?)",
                    dataList, 1,
                    (ps, data) -> {
                        ps.setObject(1, data);
                    });
            System.out.println("插入成功!");
        } catch (Exception e) {
            System.out.println("插入失败,注解事务回滚!");
        }
    }

    /**
     * 额外创建的JdbcTemplate声明式事务无用（只能用手动事务）
     */
    @Test
    public void test03() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/storagecoms");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(druidDataSource);
        TransactionStatus status = TransactionUtils.beginTransaction(dataSourceTransactionManager);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(druidDataSource);
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("song");
        dataList.add("song");
        try {
            jdbcAdaptor.batchUpdate(jdbcTemplate, "insert into userinfo (username) values (?)",
                    dataList, 1,
                    (ps, data) -> {
                        ps.setObject(1, data);
                    });
            TransactionUtils.commitTransaction(dataSourceTransactionManager, status);
            System.out.println("插入成功!");
        } catch (Exception e) {
            System.out.println("插入失败,手动事务回滚!");
            TransactionUtils.rollbackTransaction(dataSourceTransactionManager, status);
        }
    }
}
