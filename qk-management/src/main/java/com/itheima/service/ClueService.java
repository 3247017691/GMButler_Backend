package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.ClueDTO;
import com.itheima.dto.ClueFalseDTO;
import com.itheima.dto.ClueFollowDTO;
import com.itheima.entity.Clue;
import com.itheima.entity.PageResult;

public interface ClueService extends IService<Clue> {
    /**
     * 条件分页查询线索, 其余简单的功能都可以使用IService提供的功能即可
     */
    PageResult<Clue> findByPageAndCondition(ClueDTO clueDTO);

    /**
     * 条件分页查询线索池列表
     */
    PageResult<Clue> findPoolByPageAndCondition(ClueDTO clueDTO);

    /**
     * 根据ID查询线索详情（含跟进记录）
     */
    Clue getClueDetail(Integer id);

    /**
     * 为指定用户分配线索
     */
    void assign(Integer clueId, Integer userId);

    /**
     * 跟进线索
     */
    void follow(ClueFollowDTO clueFollowDTO);

    /**
     * 伪线索处理
     */
    void markFalse(Integer id, ClueFalseDTO clueFalseDTO);

    /**
     * 转商机处理
     */
    void toBusiness(Integer id);
}
