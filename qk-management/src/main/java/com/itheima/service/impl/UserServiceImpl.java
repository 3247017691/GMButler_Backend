package com.itheima.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dto.UserDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.User;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 获取用户列表
     */
    @Override
    public PageResult<User> getUsers(UserDTO userDTO) {
        Page<User> pageInfo = new Page<>(userDTO.getPage(), userDTO.getPageSize());

        pageInfo = userMapper.getUsers(pageInfo, userDTO);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }

    /**
     * 根据角色标签查询用户列表
     * @param roleLabel
     * @return
     */
    @Override
    public List<User> getUsersByRoleLabel(String roleLabel) {

        return userMapper.getUsersByRoleLabel(roleLabel);
    }

    /**
     * 根据部门ID查询用户列表
     * @param deptId
     * @return
     */
    @Override
    public List<User> getUsersByDeptId(Long deptId) {
        return userMapper.getUsersByDeptId(deptId);
    }

    /**
     * 添加用户
     * @param user
     */
    @Override
    public void addUser(User user) {
        user.setPassword(DigestUtil.md5Hex(user.getPassword() + "123"));

        userMapper.insert(user);
    }
}
