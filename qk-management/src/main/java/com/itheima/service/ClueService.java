package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.ClueDTO;
import com.itheima.dto.ClueFalseDTO;
import com.itheima.dto.ClueFollowDTO;
import com.itheima.entity.Clue;
import com.itheima.entity.PageResult;

public interface ClueService extends IService<Clue> {
    /**
     * 新增线索（仅管理员）
     */
    void addClue(Clue clue);

    /**
     * 删除线索（仅管理员）
     */
    void deleteClue(Integer id);

    /**
     * 条件分页查询线索, 其余简单的功能都可以使用IService提供的功能即可
     * 管理员可查看全部线索，负责人仅能看到自己负责的线索
     */
    PageResult<Clue> findByPageAndCondition(ClueDTO clueDTO);

    /**
     * 条件分页查询线索池列表（仅管理员）
     */
    PageResult<Clue> findPoolByPageAndCondition(ClueDTO clueDTO);

    /**
     * 根据ID查询线索详情（含跟进记录），负责人仅能看到自己负责的线索
     */
    Clue getClueDetail(Integer id);

    /**
     * 为指定用户分配线索（仅管理员）
     */
    void assign(Integer clueId, Integer userId);

    /**
     * 跟进线索（仅线索负责人）
     */
    void follow(ClueFollowDTO clueFollowDTO);

    /**
     * 伪线索处理（仅线索负责人）
     */
    void markFalse(Integer id, ClueFalseDTO clueFalseDTO);

    /**
     * 转商机处理（仅线索负责人）
     */
    void toBusiness(Integer id);
}
