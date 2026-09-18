package com.itheima.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.entity.Dept;
import com.itheima.entity.PageResult;
import com.itheima.mapper.DeptMapper;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeptServiceImpl implements DeptService {
    private DeptMapper deptMapper;

    @Autowired
    public DeptServiceImpl(DeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    /**
     * 添加部门
     */
    @Override
    public void addDept(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    /**
     * 分页查询部门
     */
    @Override
    public PageResult<Dept> findDeptsByPage(String name, Integer status, Integer page, Integer pageSize) {
        Page<Dept> p = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name), Dept::getName, name)
                .eq(status != null, Dept::getStatus, status);

        p = deptMapper.selectPage(p, wrapper);

        return new PageResult<>(p.getTotal(), p.getRecords());
    }

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    @Override
    public Dept findById(Integer id) {
        return deptMapper.selectById(id);
    }

    /**
     * 修改部门
     * @param dept
     */
    @Override
    public void updateById(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateById(dept);
    }

    /**
     * 根据id删除部门
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }
}
