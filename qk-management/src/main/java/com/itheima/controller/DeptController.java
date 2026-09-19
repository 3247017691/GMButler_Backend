package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.entity.Dept;
import com.itheima.entity.PageResult;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class DeptController {
    private final DeptService deptService;

    @Autowired
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 添加部门
     * @param dept
     * @return
     */
    @PostMapping("/depts")
    public Result addDept(@RequestBody Dept dept){
        log.info("新增部门,参数:{}", dept);
        deptService.addDept(dept);
        return Result.success();
    }

    /**
     * 分页查询部门
     */
    @GetMapping("/depts")
    public Result listDept(
            String name,
            Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("分页查询部门, 参数: name={}, status={}, page={}, pageSize={}", name, status, page, pageSize);
        PageResult<Dept> pageResult = deptService.findDeptsByPage(name, status, page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询部门
     */
    @GetMapping("/depts/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("根据id查询部门,参数:{}", id);
        Dept dept = deptService.findById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     */
    @PutMapping("/depts")
    public Result updateDept(@RequestBody Dept dept){
        log.info("修改部门,参数:{}", dept);
        deptService.updateById(dept);
        return Result.success();
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/depts/{id}")
    public Result deleteDept(@PathVariable Integer id){
        log.info("删除部门,参数:{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/depts/list")
    public Result findAllDept(){
        log.info("查询所有部门");
        return Result.success(deptService.findAllDept());
    }
}
