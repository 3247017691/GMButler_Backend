package com.itheima.utils;

import com.itheima.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // 令牌有效期（72小时）
    private static final long EXPIRATION_TIME = 72 * 60 * 60 * 1000;

    // 秘钥，从配置qk.jwt.secret中读取，不硬编码在代码中
    private final byte[] secretKey;

    public JwtUtil(JwtProperties jwtProperties) {
        String secret = jwtProperties.getSecret();
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT密钥未配置：请设置环境变量JWT_SECRET，或在配置文件中配置qk.jwt.secret");
        }
        this.secretKey = secret.getBytes();
    }

    /**
     * 生成JWT令牌
     * @param claims 自定义声明信息
     * @return 生成的JWT令牌
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param token JWT令牌
     * @return 解析后的Claims对象
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}