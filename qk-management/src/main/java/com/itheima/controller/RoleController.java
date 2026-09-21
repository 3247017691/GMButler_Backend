package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.RoleDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    //注入业务层
    @Autowired
    private RoleService roleService;

    /**
     * 分页条件查询
     * @param roleDTO
     * @return
     */
    @GetMapping
    public Result findByPageAndCondition(RoleDTO roleDTO) {
        //1.接受前端参数
        //2.参数复杂要学会封装参数
        //3.调用service查询
        PageResult<Role> pb = roleService.findByPageAndCondition(roleDTO);
        //4.统一结果集然后再返回前端
        return pb != null ? Result.success(pb) : Result.error("没有查询到部门信息");
    }


    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        boolean flag = roleService.removeById(id);
        return flag ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Role role) {
        //调用service处理数据
        return roleService.saveOrUpdate(role) ? Result.success("添加成功") : Result.error("添加失败");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){

        //调用service层完成工作即可
        Role role = roleService.getById(id);
        return  role == null ? Result.error("没有查询到部门信息") : Result.success(role);

    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Role role){
        //调用service层完成工作即可
        boolean flag = roleService.saveOrUpdate(role);
        return flag ? Result.success("修改成功") : Result.error("修改失败");

    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/list")
    public Result findAll(){
        List<Role> list =  roleService.list();
        return Result.success(list);
    }
}