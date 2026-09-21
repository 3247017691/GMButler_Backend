package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.UserDTO;
import com.itheima.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据条件查询用户列表
     * @param pageInfo
     * @param userDTO
     * @return
     */
    Page<User> getUsers(Page<User> pageInfo, UserDTO userDTO);
}
