package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dto.CustomerDTO;
import com.itheima.entity.Customer;
import com.itheima.entity.PageResult;
import com.itheima.exception.BizException;
import com.itheima.mapper.CustomerMapper;
import com.itheima.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    /**
     * 新增客户：来源商机由转客户流程维护，手工录入一律置空
     */
    @Override
    public void addCustomer(Customer customer) {
        customer.setId(null);
        customer.setBusinessId(null);
        save(customer);
    }

    /**
     * 根据ID查询客户详情
     */
    @Override
    public Customer getCustomerDetail(Integer id) {
        Customer customer = getById(id);
        if (customer == null) {
            throw new BizException("没有查询到客户信息");
        }
        return customer;
    }

    /**
     * 更新客户：来源商机与创建时间不允许被请求覆盖
     */
    @Override
    public void updateCustomer(Customer customer) {
        if (customer.getId() == null || getById(customer.getId()) == null) {
            throw new BizException("没有查询到客户信息");
        }
        customer.setBusinessId(null);
        customer.setCreateTime(null);
        updateById(customer);
    }

    /**
     * 条件分页查询客户列表
     */
    @Override
    public PageResult<Customer> findByPageAndCondition(CustomerDTO customerDTO) {
        Page<Customer> pageInfo = new Page<>(customerDTO.getPage(), customerDTO.getPageSize());

        pageInfo = baseMapper.findByPageAndCondition(pageInfo, customerDTO);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }
}
