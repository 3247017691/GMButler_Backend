package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.RoleDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.Role;

public interface RoleService extends IService<Role> {
    /**
     * 条件分页查询角色,剩余简单的功能都可以使用IService提供的功能即可
     */
    PageResult<Role> findByPageAndCondition(RoleDTO roleDTO);
}
