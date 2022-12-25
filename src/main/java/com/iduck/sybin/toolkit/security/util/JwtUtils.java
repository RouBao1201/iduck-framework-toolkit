package com.iduck.sybin.toolkit.security.util;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.iduck.sybin.toolkit.security.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/9
 **/
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    private final JwtProperties jwtProperties;

    public JwtUtils(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 校验token是否正确
     *
     * @param token token
     * @return 校验结果
     */
    public boolean verifyToken(String token) {
        return verifyToken(token, this.jwtProperties.getSecretKey());
    }

    /**
     * 校验token是否正确
     *
     * @param token     token
     * @param secretKey 密钥
     * @return 校验结果
     */
    public boolean verifyToken(String token, String secretKey) {
        if (StrUtil.isEmpty(token) || StrUtil.isEmpty(secretKey)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 创建token
     *
     * @param claims     claims
     * @param secretKey  密钥
     * @param expireTime 失效时间
     * @return token
     */
    public String createToken(Map<String, Object> claims, String secretKey, Integer expireTime) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS2256")
                .setSubject("iduck-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 创建token
     *
     * @param userId 用户id
     * @return token
     */
    public String createToken(String userId) {
        HashMap<String, Object> claims = new HashMap<>(16);
        claims.put("userId", userId);
        return createToken(claims, this.jwtProperties.getSecretKey(), this.jwtProperties.getExpireTime());
    }

    /**
     * 创建token
     *
     * @param userId     用户唯一标识
     * @param secretKey  密钥
     * @param expireTime 失效时间
     * @return token
     */
    public String createToken(String userId, String secretKey, Integer expireTime) {
        HashMap<String, Object> claims = new HashMap<>(16);
        claims.put("userId", userId);
        return createToken(claims, secretKey, expireTime);
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Claims
     */
    public Claims parseToken(String token) {
        return parseToken(token, this.jwtProperties.getSecretKey());
    }

    /**
     * 解析token
     *
     * @param token     token
     * @param secretKey 密钥
     * @return Claims
     */
    public Claims parseToken(String token, String secretKey) {
        Claims body = null;
        try {
            body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JwtUtils => Parse token error. ErrorMessage:{}", e.getMessage());
        }
        return body;
    }

    /**
     * 根据token获取userId
     *
     * @param token token
     * @return userId
     */
    public String getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        if (ObjUtil.isEmpty(claims)) {
            return "";
        }
        return String.valueOf(claims.get("userId"));
    }

    /**
     * 根据token获取userId
     *
     * @param token     token
     * @param secretKey 密钥
     * @return userId
     */
    public String getUserIdFromToken(String token, String secretKey) {
        Claims claims = parseToken(token, secretKey);
        if (ObjUtil.isEmpty(claims)) {
            return "";
        }
        return String.valueOf(claims.get("userId"));
    }
}
