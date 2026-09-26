package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.CustomerDTO;
import com.itheima.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

    /**
     * 客户分页条件查询（含意向课程名称）
     * @param pageInfo
     * @param customerDTO
     * @return
     */
    Page<Customer> findByPageAndCondition(Page<Customer> pageInfo, CustomerDTO customerDTO);
}
