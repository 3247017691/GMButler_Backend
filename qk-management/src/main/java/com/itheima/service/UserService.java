package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.UserDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.User;

import java.util.List;


public interface UserService extends IService<User> {
    /**
     * 根据条件查询用户列表
     */
    PageResult<User> getUsers(UserDTO userDTO);

    /**
     * 根据角色标签查询用户列表
     * @param roleLabel
     * @return
     */
    List<User> getUsersByRoleLabel(String roleLabel);

    /**
     * 根据部门标签查询用户列表
     * @param deptId
     * @return
     */
    List<User> getUsersByDeptId(Long deptId);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);
}
