package com.itheima.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.RoleDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.Role;
import com.itheima.mapper.RoleMapper;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /**
     * 根据条件查询分页数据
     * @param roleDTO
     * @return
     */
    @Override
    public PageResult<Role> findByPageAndCondition(RoleDTO roleDTO) {
        Page<Role> page = new Page<>(roleDTO.getPage(), roleDTO.getPageSize());

        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(roleDTO.getName()), Role::getName, roleDTO.getName())
                .eq(StrUtil.isNotBlank(roleDTO.getLabel()), Role::getLabel, roleDTO.getLabel());

        page = roleMapper.selectPage(page, wrapper);

        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        roleMapper.deleteById(id);
    }

    /**
     * 添加角色
     * @param roleDTO
     */
    @Override
    public void save(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setLabel(roleDTO.getLabel());
        role.setRemark(roleDTO.getRemark());
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insert(role);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Role findById(Integer id) {
        return roleMapper.selectById(id);
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    public void update(Role role) {
        roleMapper.updateById(role);
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public Object list() {
        return roleMapper.selectList(null);
    }
}
