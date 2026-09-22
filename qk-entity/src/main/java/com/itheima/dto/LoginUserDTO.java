package com.itheima.dto;

import lombok.Data;

/**
 * 用户登录请求参数
 */
@Data
public class LoginUserDTO {
    private String username;
    private String password;
}