package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.UserDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        PageResult<User> pageResult = userService.getUsers(userDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result deleteUsers(@PathVariable List<Long> ids) {
        userService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping
    public Result saveUser(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findUserById(@PathVariable Long id) {
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
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/list")
    public Result getUserList() {
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
        List<User> userList = userService.getUsersByRoleLabel(roleLabel);
        return Result.success(userList);
    }

    @GetMapping("/dept/{deptId}")
    public Result getUsersByDeptId(@PathVariable Long deptId) {
        List<User> userList = userService.getUsersByDeptId(deptId);
        return Result.success(userList);
    }
}
