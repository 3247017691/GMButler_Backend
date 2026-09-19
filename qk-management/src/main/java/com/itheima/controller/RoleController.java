package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.RoleDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.Role;
import com.itheima.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public Result findByPageAndCondition(RoleDTO roleDTO) {
        PageResult<Role> pb = roleService.findByPageAndCondition(roleDTO);
        //4.统一结果集然后再返回前端
        return pb != null ? Result.success(pb) : Result.error("没有查询到部门信息");
    }
}
