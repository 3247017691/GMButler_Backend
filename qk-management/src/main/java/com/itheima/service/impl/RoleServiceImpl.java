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

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }
    @Override
    public PageResult<Role> findByPageAndCondition(RoleDTO roleDTO) {
        Page<Role> page = new Page<>(roleDTO.getPage(), roleDTO.getPageSize());

        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(roleDTO.getName()), Role::getName, roleDTO.getName())
                .eq(StrUtil.isNotBlank(roleDTO.getLabel()), Role::getLabel, roleDTO.getLabel());

        page = roleMapper.selectPage(page, wrapper);

        return new PageResult<>(page.getTotal(), page.getRecords());
    }
}
