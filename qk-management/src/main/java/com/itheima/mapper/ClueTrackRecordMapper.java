package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.ClueTrackRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClueTrackRecordMapper extends BaseMapper<ClueTrackRecord> {

    /**
     * 根据线索ID查询跟进记录列表（含跟进人姓名）
     * @param clueId
     * @return
     */
    List<ClueTrackRecord> findByClueId(Integer clueId);
}
