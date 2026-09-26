package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.BusinessDTO;
import com.itheima.entity.Business;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessMapper extends BaseMapper<Business> {

    /**
     * 商机分页条件查询（含负责人姓名）
     * @param pageInfo
     * @param businessDTO
     * @return
     */
    Page<Business> findByPageAndCondition(Page<Business> pageInfo, BusinessDTO businessDTO);

    /**
     * 商机公海池分页条件查询（回收状态）
     * @param pageInfo
     * @param businessDTO
     * @return
     */
    Page<Business> findPoolByPageAndCondition(Page<Business> pageInfo, BusinessDTO businessDTO);

    /**
     * 根据ID查询商机详情（含负责人姓名）
     * @param id
     * @return
     */
    Business findDetailById(Integer id);
}
