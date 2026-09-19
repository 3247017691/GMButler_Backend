package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dto.RoleDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.Role;
import com.itheima.mapper.RoleMapper;
import com.itheima.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    /**
     * 根据条件查询分页数据
     *
     * @param roleDTO
     * @return
     */
    @Override
    public PageResult<Role> findByPageAndCondition(RoleDTO roleDTO) { //"张"
        // 1.单表的我们要习惯使用mp完成
        Page<Role> page = new Page<>(roleDTO.getPage(), roleDTO.getPageSize());
        //2.创建mp的条件构建器
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        // "" , null ,"    "
        queryWrapper.like(StringUtils.isNotBlank(roleDTO.getName()), Role::getName, roleDTO.getName())
                .eq(StringUtils.isNotBlank(roleDTO.getLabel()), Role::getLabel, roleDTO.getLabel());
        // getBaseMapper().selectPage(page, queryWrapper);
        page(page,queryWrapper);
        //3.将查询的结果封装成接口文档要的格式
        PageResult<Role> pb = new PageResult<>(page.getTotal(), page.getRecords());

        return pb;
    }
}
