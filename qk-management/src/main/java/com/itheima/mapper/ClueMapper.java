package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.ClueDTO;
import com.itheima.entity.Clue;
import com.itheima.vo.OverviewVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClueMapper extends BaseMapper<Clue> {

    /**
     * 统计线索概览数据（总数与各状态数量）
     * @return
     */
    OverviewVO getClueOverviewData();

    /**
     * 线索分页条件查询（含归属人姓名）
     * @param pageInfo
     * @param clueDTO
     * @return
     */
    Page<Clue> findByPageAndCondition(Page<Clue> pageInfo, ClueDTO clueDTO);

    /**
     * 线索池分页条件查询（含来源活动名称）
     * @param pageInfo
     * @param clueDTO
     * @return
     */
    Page<Clue> findPoolByPageAndCondition(Page<Clue> pageInfo, ClueDTO clueDTO);

    /**
     * 根据ID查询线索详情（含归属人姓名）
     * @param id
     * @return
     */
    Clue findDetailById(Integer id);
}
