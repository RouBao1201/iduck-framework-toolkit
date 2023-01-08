package com.iduck.sybin.toolkit.redis.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis适配工具
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
public class RedisAdapter {
    private static final Logger log = LoggerFactory.getLogger(RedisAdapter.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisAdapter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置key-value
     */
    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置key-value,并指定过期时间
     */
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置key的过期时间
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(this.redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 设置key具体在哪个时间过期
     */
    public void expireAt(String key, Date date) {
        this.redisTemplate.expireAt(key, date);
    }

    /**
     * 获取数据
     */
    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取数据,指定返回类型
     */
    public <T> T get(String key, Class<T> clazz) {
        ValueOperations<String, T> value = (ValueOperations<String, T>) this.redisTemplate.opsForValue();
        return value.get(key);
    }

    /**
     * 删除单个key
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(this.redisTemplate.delete(key));
    }

    /**
     * 判断是否存在key
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(key));
    }

    /**
     * 修改key的名称
     */
    public void rename(String oldKey, String newKey) {
        this.redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 如果存在key,则修改key的名称
     */
    public void renameIfAbsent(String oldKey, String newKey) {
        this.redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 根据key获取value类型
     */
    public DataType type(String key) {
        return this.redisTemplate.type(key);
    }

    /**
     * 获取某个key的剩余过期时间
     */
    public long getExpire(String key) {
        return Long.parseLong(String.valueOf(this.redisTemplate.getExpire(key)));
    }

    /**
     * 获取某个key的剩余过期时间,指定单位
     */
    public long getExpire(String key, TimeUnit unit) {
        return Long.parseLong(String.valueOf(this.redisTemplate.getExpire(key, unit)));
    }

    /**
     * 在key原本的value后追加数据
     */
    public void append(String key, String value) {
        this.redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 若key存在,则重新设置value值
     */
    public void setIfAbsent(String key, String value) {
        this.redisTemplate.opsForValue().setIfAbsent(key, value);
    }
}
