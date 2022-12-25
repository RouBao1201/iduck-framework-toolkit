package com.iduck.sybin.toolkit.lock.redisson.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/25
 **/
@ConfigurationProperties(prefix = "idk.redisson.config")
public class RedissonProperties {

    public static final String ADDRESS_PREFIX = "redis://";

    private String model = "single";

    private String address = "127.0.0.1:6379";

    private String username;

    private String password;

    private Integer database;

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

    @Override
    public String toString() {
        return "RedissonProperties{" +
                ", model='" + model + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", database=" + database +
                '}';
    }
}
