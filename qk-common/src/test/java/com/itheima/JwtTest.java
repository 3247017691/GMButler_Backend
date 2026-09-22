package com.itheima;

import com.itheima.properties.JwtProperties;
import com.itheima.utils.JwtUtil;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    public static void main(String[] args) {
        //测试密钥从环境变量读取，避免硬编码到代码库
        String secret = System.getenv("JWT_SECRET");
        if (secret == null || secret.isBlank()) {
            System.out.println("未读取到环境变量JWT_SECRET，请先配置后再运行测试");
            return;
        }

        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(secret);
        JwtUtil jwtUtil = new JwtUtil(jwtProperties);

        //验证: 生成一个token,修改载荷部分的内容，如果token被篡改，则报SignatureException
        //     过期：报ExpiredJwtException,设置有效期为1秒，重新生成token再测
        Map<String, Object> claim = new HashMap<>();
        claim.put("id", 9527);
        claim.put("name", "华安");
        String token = jwtUtil.generateToken(claim);
        System.out.println("token = " + token);

        Claims claims = jwtUtil.parseToken(token);
        System.out.println("claims = " + claims);
    }
}