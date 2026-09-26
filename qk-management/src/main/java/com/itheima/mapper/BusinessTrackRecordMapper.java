package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.BusinessTrackRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BusinessTrackRecordMapper extends BaseMapper<BusinessTrackRecord> {

    /**
     * 根据商机ID查询跟进记录列表（含跟进人姓名）
     * @param businessId
     * @return
     */
    List<BusinessTrackRecord> findByBusinessId(Integer businessId);
}
