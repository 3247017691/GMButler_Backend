package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.BusinessDTO;
import com.itheima.dto.BusinessFollowDTO;
import com.itheima.entity.Business;
import com.itheima.entity.PageResult;
import com.itheima.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/businesses")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    /**
     * 商机列表查询(分页 + 条件)
     *
     * @param businessDTO
     * @return
     */
    @GetMapping
    public Result getBusinesses(BusinessDTO businessDTO) {
        PageResult<Business> result = businessService.findByPageAndCondition(businessDTO);
        return Result.success(result);
    }

    /**
     * 商机公海池列表查询(分页 + 条件)
     *
     * @param businessDTO
     * @return
     */
    @GetMapping("/pool")
    public Result getBusinessPool(BusinessDTO businessDTO) {
        PageResult<Business> result = businessService.findPoolByPageAndCondition(businessDTO);
        return Result.success(result);
    }

    /**
     * 根据id获取商机详情(含跟进记录)
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getBusiness(@PathVariable Integer id) {
        Business business = businessService.getBusinessDetail(id);
        return Result.success(business);
    }

    /**
     * 新增商机（仅管理员）
     *
     * @param business
     * @return
     */
    @PostMapping
    public Result saveBusiness(@RequestBody Business business) {
        businessService.addBusiness(business);
        return Result.success();
    }

    /**
     * 跟进商机
     *
     * @param businessFollowDTO
     * @return
     */
    @PutMapping
    public Result followBusiness(@RequestBody BusinessFollowDTO businessFollowDTO) {
        businessService.follow(businessFollowDTO);
        return Result.success();
    }

    /**
     * 分配商机
     *
     * @param businessId
     * @param userId
     * @return
     */
    @PutMapping("/assign/{businessId}/{userId}")
    public Result assignBusiness(@PathVariable Integer businessId, @PathVariable Integer userId) {
        businessService.assign(businessId, userId);
        return Result.success();
    }

    /**
     * 踢回公海
     *
     * @param id
     * @return
     */
    @PutMapping("/back/{id}")
    public Result backBusiness(@PathVariable Integer id) {
        businessService.backPool(id);
        return Result.success();
    }

    /**
     * 转客户处理
     *
     * @param id
     * @return
     */
    @PostMapping("/toCustomer/{id}")
    public Result toCustomerBusiness(@PathVariable Integer id) {
        businessService.toCustomer(id);
        return Result.success();
    }
}
