package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.CustomerDTO;
import com.itheima.entity.Customer;
import com.itheima.entity.PageResult;

public interface CustomerService extends IService<Customer> {

    /**
     * 新增客户
     */
    void addCustomer(Customer customer);

    /**
     * 根据ID查询客户详情
     *
     * @param id
     * @return
     */
    Customer getCustomerDetail(Integer id);

    /**
     * 更新客户
     *
     * @param customer
     */
    void updateCustomer(Customer customer);

    /**
     * 条件分页查询客户列表
     *
     * @param customerDTO
     * @return
     */
    PageResult<Customer> findByPageAndCondition(CustomerDTO customerDTO);
}
