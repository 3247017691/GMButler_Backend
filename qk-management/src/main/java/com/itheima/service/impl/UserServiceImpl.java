package com.itheima.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        Page<User> page = new Page<>(userDTO.getPage(), userDTO.getPageSize());

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(userDTO.getName()), User::getName, userDTO.getName())
            .eq(StrUtil.isNotBlank(userDTO.getPhone()), User::getPhone, userDTO.getPhone())
            .eq(userDTO.getStatus() != null, User::getStatus, userDTO.getStatus())
            .eq(userDTO.getDeptId() != null, User::getDeptId, userDTO.getDeptId());

        page(page, wrapper);

        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 根据角色标签查询用户列表
     * @param roleLabel
     * @return
     */
    @Override
    public List<User> getUsersByRoleLabel(String roleLabel) {

        return List.of();
    }

    /**
     * 根据部门ID查询用户列表
     * @param deptId
     * @return
     */
    @Override
    public List<User> getUsersByDeptId(Long deptId) {
        return List.of();
    }
}
