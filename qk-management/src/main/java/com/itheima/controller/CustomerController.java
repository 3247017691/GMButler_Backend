package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.CustomerDTO;
import com.itheima.entity.Customer;
import com.itheima.entity.PageResult;
import com.itheima.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 客户列表查询(分页 + 条件)
     *
     * @param customerDTO
     * @return
     */
    @GetMapping
    public Result getCustomers(CustomerDTO customerDTO) {
        PageResult<Customer> result = customerService.findByPageAndCondition(customerDTO);
        return Result.success(result);
    }

    /**
     * 根据id获取客户详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getCustomer(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerDetail(id);
        return Result.success(customer);
    }

    /**
     * 新增客户
     *
     * @param customer
     * @return
     */
    @PostMapping
    public Result saveCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return Result.success();
    }

    /**
     * 修改客户
     *
     * @param customer
     * @return
     */
    @PutMapping
    public Result updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        return Result.success();
    }
}
