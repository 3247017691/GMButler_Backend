package com.itheima.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import com.itheima.common.Result;
import com.itheima.dto.UserDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取用户列表
     */
    @GetMapping
    public Result getUsers(UserDTO userDTO) {
        log.info("获取用户列表: {}", userDTO);
        PageResult<User> pageResult = userService.getUsers(userDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除用户
     * @param ids 用户ID数组
     * @return 统一响应结果
     */
    @DeleteMapping("/{ids}")
    public Result deleteUsers(@PathVariable("ids") List<Integer> ids) {
        log.info("批量删除用户: {}", ids);
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping
    public Result saveUser(@RequestBody User user) {
        log.info("新增用户: {}", user);
        userService.addUser(user);
        return Result.success();
    }

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findUserById(@PathVariable Long id) {
        log.info("根据id获取用户: {}", id);
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping
    public Result updateUser(@RequestBody User user) {
        log.info("修改用户: {}", user);
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/list")
    public Result getUserList() {
        log.info("获取用户列表");
        List<User> userList = userService.list();
        return Result.success(userList);
    }

    /**
     * 根据角色标签获取用户列表
     * @param roleLabel
     * @return
     */
    @GetMapping("/role/{roleLabel}")
    public Result getUsersByRoleLabel(@PathVariable String roleLabel) {
        log.info("根据角色标签获取用户列表: {}", roleLabel);
        List<User> userList = userService.getUsersByRoleLabel(roleLabel);
        return Result.success(userList);
    }

    /**
     * 根据部门id获取用户列表
     * @param deptId
     * @return
     */
    @GetMapping("/dept/{deptId}")
    public Result getUsersByDeptId(@PathVariable Long deptId) {
        log.info("根据部门id获取用户列表: {}", deptId);
        List<User> userList = userService.getUsersByDeptId(deptId);
        return Result.success(userList);
    }
}
