package com.iduck.sybin.toolkit.jdbc.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * JdbcTemplate封装工具
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/28
 **/
public class JdbcAdapter {

    private static final Logger log = LoggerFactory.getLogger(JdbcAdapter.class);

    /**
     * 分段提交默认数量
     */
    private static final int DEFAULT_SUBMIT_SIZE = 900;

    private final JdbcTemplate jdbcTemplate;

    public JdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * JdbcTemplate批量插入【分段提交】
     *
     * @param sql        sql
     * @param dataList   数据集合
     * @param submitSize 批量提交数量
     * @param consumer   参数设置回调
     * @param <T>        数据类型枚举
     */
    public <T> void batchInsert(String sql,
                                List<T> dataList,
                                int submitSize,
                                DiInjectConsumer<PreparedStatement, T> consumer) {
        batchUpdate(this.jdbcTemplate, sql, dataList, submitSize, consumer);
    }

    /**
     * JdbcTemplate批量插入【分段提交】
     *
     * @param sql      sql
     * @param dataList 数据集合
     * @param consumer 参数设置回调
     * @param <T>      数据类型枚举
     */
    public <T> void batchInsert(String sql,
                                List<T> dataList,
                                DiInjectConsumer<PreparedStatement, T> consumer) {
        batchUpdate(this.jdbcTemplate, sql, dataList, DEFAULT_SUBMIT_SIZE, consumer);
    }

    /**
     * 批量修改【分段提交】
     *
     * @param mapperFunc mapper方法
     * @param list       插入的数据集合
     * @param size       一次提交的数量
     * @param <T>        数据枚举
     */
    public <T> void batchInsert(Consumer<List<T>> mapperFunc, List<T> list, int size) {
        batchUpdate(mapperFunc, list, size);
    }

    /**
     * 批量插入【分段提交】
     *
     * @param mapperFunc mapper方法
     * @param list       插入的数据集合
     * @param <T>        数据枚举
     */
    public <T> void batchInsert(Consumer<List<T>> mapperFunc, List<T> list) {
        batchUpdate(mapperFunc, list, DEFAULT_SUBMIT_SIZE);
    }

    /**
     * JdbcTemplate批量修改【分段提交】
     *
     * @param sql      sql
     * @param dataList 数据集合
     * @param consumer 参数设置回调
     * @param <T>      数据类型枚举
     */
    public <T> void batchUpdate(String sql,
                                List<T> dataList,
                                int submitSize,
                                DiInjectConsumer<PreparedStatement, T> consumer) {
        batchUpdate(this.jdbcTemplate, sql, dataList, submitSize, consumer);
    }

    /**
     * JdbcTemplate批量修改【分段提交】
     *
     * @param sql      sql
     * @param dataList 数据集合
     * @param consumer 参数设置回调
     * @param <T>      数据类型枚举
     */
    public <T> void batchUpdate(String sql,
                                List<T> dataList,
                                DiInjectConsumer<PreparedStatement, T> consumer) {
        batchUpdate(this.jdbcTemplate, sql, dataList, DEFAULT_SUBMIT_SIZE, consumer);
    }

    /**
     * 批量修改【分段提交】
     *
     * @param mapperFunc 插入的数据集合
     * @param list       插入的数据集合
     * @param <T>        数据枚举
     */
    public <T> void batchUpdate(Consumer<List<T>> mapperFunc, List<T> list) {
        batchUpdate(mapperFunc, list, DEFAULT_SUBMIT_SIZE);
    }

    /**
     * JdbcTemplate批量修改【分段提交】
     *
     * @param jdbcTemplate jdbcTemplate
     * @param sql          sql
     * @param dataList     数据集合
     * @param intervalSize 提交数量
     * @param consumer     参数设置回调
     * @param <T>          数据枚举类型
     */
    public <T> void batchUpdate(JdbcTemplate jdbcTemplate,
                                String sql,
                                List<T> dataList,
                                int submitSize,
                                DiInjectConsumer<PreparedStatement, T> consumer) {
        int begin = 0;
        int end = begin + submitSize;
        int maxSize = dataList.size();
        List<T> subList = new ArrayList<>();

        // todo 这一块逻辑还需要优化,一时间不会写了
        if (maxSize < submitSize) {
            subList.addAll(dataList.subList(begin, maxSize));
            execute(jdbcTemplate, sql, subList, consumer);
            subList.clear();
        } else {
            while (begin < maxSize) {
                subList.addAll(dataList.subList(begin, end));
                execute(jdbcTemplate, sql, subList, consumer);
                subList.clear();
                begin = end;
                end += submitSize;
            }
            if (begin > maxSize) {
                begin -= submitSize;
                subList.addAll(dataList.subList(begin, maxSize));
                execute(jdbcTemplate, sql, subList, consumer);
                subList.clear();
            }
        }
    }

    /**
     * 批量插入【分段提交】
     *
     * @param mapperFunc mapper方法
     * @param list       插入的数据集合
     * @param size       一次提交的数量
     * @param <T>        数据枚举
     */
    public <T> void batchUpdate(Consumer<List<T>> mapperFunc, List<T> list, int size) {
        List<T> subList = new ArrayList<>();
        for (int i = 1; i < list.size() + 1; i++) {
            subList.add(list.get(i - 1));
            if (i % size == 0) {
                // 执行mapper方法
                mapperFunc.accept(subList);
                subList.clear();
            }
        }
        if (subList.size() != 0) {
            mapperFunc.accept(subList);
            subList.clear();
        }
    }

    private <T> void execute(JdbcTemplate jdbcTemplate, String sql, List<T> subList,
                             DiInjectConsumer<PreparedStatement, T> consumer) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            // SQL参数设置回调
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                consumer.accept(ps, subList.get(i));
            }

            // 设置提交数量
            @Override
            public int getBatchSize() {
                return subList.size();
            }
        });
    }

    @FunctionalInterface
    public interface DiInjectConsumer<P, T> {
        /**
         * 回调方法
         *
         * @param p PreparedStatement
         * @param t 提交的数据
         * @throws SQLException 异常
         */
        void accept(P p, T t) throws SQLException;
    }
}
