package com.itheima;

import com.itheima.utils.JwtUtil;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    public static void main(String[] args) {
        //generate();
        generate();
        //验证: 生成一个token,修改载荷部分的内容，如果token被篡改，则报SignatureException
        //     过期：报ExpiredJwtException,设置有效期为1秒，重新生成token再测

        String token = "REDACTED";
        Claims claims = JwtUtil.parseToken(token);
        System.out.println("claims = " + claims);
    }

    private static void generate() {
        //生成 jwt token
        // 载荷
        Map<String,Object> claim = new HashMap<>();
        claim.put("id", 9527);
        claim.put("name", "华安");
        String token = JwtUtil.generateToken(claim);
        System.out.println("token = " + token);
    }
}