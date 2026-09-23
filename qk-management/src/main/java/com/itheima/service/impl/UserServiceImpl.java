package com.itheima.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dto.LoginUserDTO;
import com.itheima.dto.UserDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.User;
import com.itheima.exception.BizException;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.vo.LoginResultVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
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

    @Override
    public LoginResultVO login(LoginUserDTO dto) {
        User userInDb = getBaseMapper().selectByUsername(dto.getUsername());
        if (userInDb == null) {
            throw new BizException("用户名或密码错误");
        }

        if(userInDb.getStatus() == 0){
            throw new BizException("用户已禁用");
        }
        String encrypted = DigestUtil.md5Hex(dto.getPassword());
        if(!encrypted.equals(userInDb.getPassword())){
            throw new BizException("用户名或密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userInDb.getId());
        claims.put("username", userInDb.getName());
        claims.put("roleLabel", userInDb.getRoleLabel());
        String token = jwtUtil.generateToken(claims);

        LoginResultVO vo = new LoginResultVO();
        vo.setId(userInDb.getId());
        vo.setName(userInDb.getName());
        vo.setToken(token);
        vo.setUsername(userInDb.getUsername());
        vo.setRoleLabel(userInDb.getRoleLabel());
        vo.setImage(userInDb.getImage());
        return vo;
    }
}
