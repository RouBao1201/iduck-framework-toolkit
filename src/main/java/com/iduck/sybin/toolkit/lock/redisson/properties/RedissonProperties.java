package com.iduck.sybin.toolkit.lock.redisson.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/25
 **/
@ConfigurationProperties(prefix = "idk.redisson.config")
public class RedissonProperties {

    public static final String ADDRESS_PREFIX = "redis://";

    /**
     * 枚举类型（默认single）：single/master/sentinel/master-salve
     */
    private String model = "single";

    /**
     * 链接地址（集群时可配置多个,使用英文,隔开）
     */
    private String address = "127.0.0.1:6379";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库编号
     */
    private Integer database = 0;

    /**
     * 连接池最小空闲连接数
     */
    private Integer connectionMinimumIdleSize;

    /**
     * 连接池最大线程数
     */
    private Integer connectionPoolSize;

    /**
     * 线程超时时间
     */
    private Integer idleConnectionTimeOut;

    /**
     * 客户端程序获取redis链接超时时间
     */
    private Integer connectTimeout;

    /**
     * 响应超时时间
     */
    private Integer timeout;

    /**
     * 集群状态扫描间隔时间,单位是毫秒
     */
    private Integer scanInterval;

    /**
     * 读取操作的负载均衡模式
     * 枚举值为： salve（只在从服务节点里读取）、master（只在主服务节点里读取）、master_slave（在主从服务节点里都可以读取）
     */
    private String readMode;

    /**
     * 负载均衡算法类的选择
     * 枚举值：round-轮询调度算法（默认算法）、weight-权重轮询调度算法（必须配置权重对）、random-随机调度算法
     */
    private String loadBalancer;

    /**
     * 权重键值对
     */
    private String weights;

    /**
     * 默认权重值
     */
    private Integer defaultWeight;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public Integer getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    public void setConnectionMinimumIdleSize(Integer connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public Integer getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(Integer connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public Integer getIdleConnectionTimeOut() {
        return idleConnectionTimeOut;
    }

    public void setIdleConnectionTimeOut(Integer idleConnectionTimeOut) {
        this.idleConnectionTimeOut = idleConnectionTimeOut;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(Integer scanInterval) {
        this.scanInterval = scanInterval;
    }

    public String getReadMode() {
        return readMode;
    }

    public void setReadMode(String readMode) {
        this.readMode = readMode;
    }

    public String getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(String loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public Integer getDefaultWeight() {
        return defaultWeight;
    }

    public void setDefaultWeight(Integer defaultWeight) {
        this.defaultWeight = defaultWeight;
    }

    @Override
    public String toString() {
        return "RedissonProperties{" +
                "model='" + model + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", database=" + database +
                ", connectionMinimumIdleSize=" + connectionMinimumIdleSize +
                ", connectionPoolSize=" + connectionPoolSize +
                ", idleConnectionTimeOut=" + idleConnectionTimeOut +
                ", connectTimeout=" + connectTimeout +
                ", timeout=" + timeout +
                ", scanInterval=" + scanInterval +
                ", readMode='" + readMode + '\'' +
                ", loadBalancer='" + loadBalancer + '\'' +
                ", weights='" + weights + '\'' +
                ", defaultWeight=" + defaultWeight +
                '}';
    }
}
