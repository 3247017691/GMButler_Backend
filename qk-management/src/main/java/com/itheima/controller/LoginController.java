package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.LoginUserDTO;
import com.itheima.service.UserService;
import com.itheima.vo.LoginResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 用户登录
     * @param dto：用户名与密码
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginUserDTO dto){
        // 调用业务层做登录校验
        LoginResultVO vo = userService.login(dto);
        return Result.success(vo);
    }
}
