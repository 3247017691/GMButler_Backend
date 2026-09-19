package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.RoleDTO;
import com.itheima.entity.PageResult;
import com.itheima.entity.Role;
import com.itheima.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 根据条件查询分页数据
     * @param roleDTO
     * @return
     */
    @GetMapping("/roles")
    public Result findByPageAndCondition(RoleDTO roleDTO) {
        PageResult<Role> pb = roleService.findByPageAndCondition(roleDTO);
        //4.统一结果集然后再返回前端
        return pb != null ? Result.success(pb) : Result.error("没有查询到部门信息");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/roles/{id}")
    public Result deleteById(Long id) {
        roleService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增角色
     * @param roleDTO
     * @return
     */
    @PostMapping("/roles")
    public Result save(RoleDTO roleDTO) {
        roleService.save(roleDTO);
        return Result.success();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/roles/{id}")
    public Result findById(@PathVariable Integer id){
        return Result.success(roleService.findById(id));
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PutMapping("/roles")
    public Result update(@RequestBody Role role) {
        roleService.update(role);
        return Result.success();
    }

    /**
     * 查询所有角色
     * @return
     */
    @GetMapping("/roles/list")
    public Result listRoles() {
        return Result.success(roleService.list());
    }
}
