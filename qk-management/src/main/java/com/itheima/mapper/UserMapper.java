package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.UserDTO;
import com.itheima.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据条件查询用户列表
     * @param pageInfo
     * @param userDTO
     * @return
     */
    Page<User> getUsers(Page<User> pageInfo, UserDTO userDTO);

    /**
     * 根据角色标签查询用户列表
     * @param roleLabel
     * @return
     */
    List<User> getUsersByRoleLabel(String roleLabel);

    /**
     * 根据部门ID查询用户列表
     * @param deptId
     * @return
     */
    List<User> getUsersByDeptId(Long deptId);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User selectByUsername(String username);
}
