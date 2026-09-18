package com.itheima.service;

import com.itheima.entity.Dept;
import com.itheima.entity.PageResult;
import org.springframework.stereotype.Service;


public interface DeptService {
    /**
     * 添加部门
     * @param dept
     */
    void addDept(Dept dept);

    /**
     * 分页查询部门
     * @param name
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<Dept> findDeptsByPage(String name, Integer status, Integer page, Integer pageSize);

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    Dept findById(Integer id);

    /**
     * 修改部门
     * @param dept
     */
    void updateById(Dept dept);

    /**
     * 根据id删除部门
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有部门
     * @return
     */
    Object findAllDept();
}
