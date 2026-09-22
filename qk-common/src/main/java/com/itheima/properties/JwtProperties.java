package com.itheima.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("qk.jwt") //读取yaml中的配置
@Data
public class JwtProperties {
    private String secret;
}
