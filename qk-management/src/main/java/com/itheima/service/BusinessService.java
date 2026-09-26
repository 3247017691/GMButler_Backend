package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.BusinessDTO;
import com.itheima.dto.BusinessFollowDTO;
import com.itheima.entity.Business;
import com.itheima.entity.PageResult;

public interface BusinessService extends IService<Business> {
    /**
     * 新增商机（仅管理员），进入待分配状态等待分配
     */
    void addBusiness(Business business);

    /**
     * 条件分页查询商机列表, 其余简单的功能都可以使用IService提供的功能即可
     * 管理员可查看全部商机，负责人仅能看到自己负责的商机
     */
    PageResult<Business> findByPageAndCondition(BusinessDTO businessDTO);

    /**
     * 条件分页查询商机公海池列表（仅管理员）
     */
    PageResult<Business> findPoolByPageAndCondition(BusinessDTO businessDTO);

    /**
     * 根据ID查询商机详情（含跟进记录），负责人仅能看到自己负责的商机
     */
    Business getBusinessDetail(Integer id);

    /**
     * 为指定用户分配商机（仅管理员）
     */
    void assign(Integer businessId, Integer userId);

    /**
     * 跟进商机（仅商机负责人）
     */
    void follow(BusinessFollowDTO businessFollowDTO);

    /**
     * 踢回公海（仅商机负责人）
     */
    void backPool(Integer id);

    /**
     * 转客户处理（仅商机负责人）
     */
    void toCustomer(Integer id);
}
