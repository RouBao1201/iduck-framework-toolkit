package com.iduck.sybin.toolkit.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全加密配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/22
 **/
@ConfigurationProperties(prefix = "idk.security")
public class SecurityKeyProperties {
    private String aesKey;

    private String md5Salt;

    private String rsaPublicKey;

    private String rsaPrivateKey;

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getMd5Salt() {
        return md5Salt;
    }

    public void setMd5Salt(String md5Salt) {
        this.md5Salt = md5Salt;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    @Override
    public String toString() {
        return "SecurityKeyProperties{" +
                "aesKey='" + aesKey + '\'' +
                ", md5Salt='" + md5Salt + '\'' +
                ", rsaPublicKey='" + rsaPublicKey + '\'' +
                ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                '}';
    }
}
