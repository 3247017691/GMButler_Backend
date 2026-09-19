package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.RoleDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.Role;
import org.springframework.stereotype.Service;

public interface RoleService{
    /**
     * 条件分页查询角色，剩余简单的功能都可以使用IService提供的功能即可
     */
    PageResult<Role> findByPageAndCondition(RoleDTO roleDTO);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 添加角色
     * @param roleDTO
     */
    void save(RoleDTO roleDTO);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Role findById(Integer id);

    /**
     * 修改角色
     * @param role
     */
    void update(Role role);

    /**
     * 查询所有角色
     * @return
     */
    Object list();
}
