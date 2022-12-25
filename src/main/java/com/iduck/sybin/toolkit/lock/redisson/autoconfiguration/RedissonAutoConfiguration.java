package com.iduck.sybin.toolkit.lock.redisson.autoconfiguration;

import cn.hutool.core.util.StrUtil;
import com.iduck.sybin.toolkit.lock.redisson.properties.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SingleServerConfig;
import org.redisson.connection.balancer.RandomLoadBalancer;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.redisson.connection.balancer.WeightedRoundRobinBalancer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/25
 **/
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Bean("redissonClient")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(RedissonProperties redissonProperties) {
        Config config = new Config();
        String model = redissonProperties.getModel();
        switch (model) {
            case "single":
                createSingleConfig(config, redissonProperties);
                break;
            case "cluster":
                createClusterConfig(config, redissonProperties);
                break;
            case "sentinel":
                System.out.println("TODO:...sentinel...");
                break;
            case "master-salve":
                System.out.println("TODO:...master-salve...");
                break;
            default:
                break;
        }
        return Redisson.create(config);
    }

    private void createClusterConfig(Config config, RedissonProperties properties) {
        String[] nodeAddress = properties.getAddress().split(",");
        for (int i = 0; i < nodeAddress.length; i++) {
            nodeAddress[i] = RedissonProperties.ADDRESS_PREFIX + nodeAddress[i];
        }

        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .addNodeAddress(nodeAddress);

        // 集群状态扫描间隔时间,单位是毫秒
        if (properties.getScanInterval() != null) {
            clusterServersConfig.setScanInterval(properties.getScanInterval());
        }

        // 读取操作的负载均衡模式
        // 枚举值为： salve（只在从服务节点里读取）、master（只在主服务节点里读取）、master_slave（在主从服务节点里都可以读取）
        if (StrUtil.isNotEmpty(properties.getReadMode())) {
            switch (properties.getReadMode()) {
                case "master":
                    clusterServersConfig.setReadMode(ReadMode.MASTER);
                    break;
                case "slave":
                    clusterServersConfig.setReadMode(ReadMode.SLAVE);
                    break;
                case "master_slave":
                    clusterServersConfig.setReadMode(ReadMode.MASTER_SLAVE);
                    break;
                default:
                    break;
            }
        }

        // 负载均衡算法类的选择
        if (StrUtil.isNotEmpty(properties.getLoadBalancer())) {
            switch (properties.getLoadBalancer()) {
                case "round":
                    // 轮询调度算法（默认算法）
                    clusterServersConfig.setLoadBalancer(new RoundRobinLoadBalancer());
                    break;
                case "weight":
                    // 权重轮询调度算法（必须配置权重对）
                    if (StrUtil.isNotEmpty(properties.getWeights()) && properties.getDefaultWeight() != null) {
                        String[] weightPairs = properties.getWeights().split(",");
                        HashMap<String, Integer> map = new HashMap<>();
                        for (String weightPair : weightPairs) {
                            String[] weight = weightPair.split("=");
                            map.put(weight[0], Integer.parseInt(weight[1]));
                        }
                        clusterServersConfig.setLoadBalancer(new WeightedRoundRobinBalancer(map, properties.getDefaultWeight()));
                    }
                    break;
                case "random":
                    // 随机调度算法
                    clusterServersConfig.setLoadBalancer(new RandomLoadBalancer());
                    break;
                default:
                    break;

            }
        }
    }

    private void createSingleConfig(Config config, RedissonProperties properties) {
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(RedissonProperties.ADDRESS_PREFIX + properties.getAddress());
        if (StrUtil.isNotEmpty(properties.getUsername()) && StrUtil.isNotEmpty(properties.getPassword())) {
            singleServerConfig.setUsername(properties.getUsername())
                    .setPassword(properties.getPassword());
        }
        if (properties.getDatabase() != null) {
            singleServerConfig.setDatabase(properties.getDatabase());
        }

        // 连接池最小空闲连接数
        if (properties.getConnectionMinimumIdleSize() != null) {
            singleServerConfig.setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize());
        }

        // 连接池最大线程数
        if (properties.getConnectionPoolSize() != null) {
            singleServerConfig.setConnectionPoolSize(properties.getConnectionPoolSize());
        }

        // 线程超时时间
        if (properties.getIdleConnectionTimeOut() != null) {
            singleServerConfig.setIdleConnectionTimeout(properties.getIdleConnectionTimeOut());
        }

        // 客户端程序获取redis链接超时时间
        if (properties.getConnectTimeout() != null) {
            singleServerConfig.setConnectTimeout(properties.getConnectTimeout());
        }

        // 响应超时时间
        if (properties.getTimeout() != null) {
            singleServerConfig.setTimeout(properties.getTimeout());
        }
    }
}
