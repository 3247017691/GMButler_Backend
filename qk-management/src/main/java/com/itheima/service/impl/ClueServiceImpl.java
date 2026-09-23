package com.itheima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dto.ClueDTO;
import com.itheima.dto.ClueFalseDTO;
import com.itheima.dto.ClueFollowDTO;
import com.itheima.entity.Business;
import com.itheima.entity.Clue;
import com.itheima.entity.ClueTrackRecord;
import com.itheima.entity.PageResult;
import com.itheima.exception.BizException;
import com.itheima.mapper.BusinessMapper;
import com.itheima.mapper.ClueMapper;
import com.itheima.mapper.ClueTrackRecordMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements ClueService {

    /**
     * 跟进记录类型：正常跟进
     */
    private static final int TRACK_TYPE_FOLLOW = 1;

    /**
     * 跟进记录类型：伪线索
     */
    private static final int TRACK_TYPE_FALSE = 2;

    /**
     * 线索状态：待分配
     */
    private static final int STATUS_WAIT_ALLOT = 1;

    /**
     * 线索状态：待跟进
     */
    private static final int STATUS_WAIT_FOLLOW = 2;

    /**
      * 线索状态：跟进中
     */
    private static final int STATUS_FOLLOWING = 3;

    /**
     * 线索状态：伪线索
     */
    private static final int STATUS_FALSE = 4;

    /**
     * 线索状态：转为商机
     */
    private static final int STATUS_CONVERT_BUSINESS = 5;

    private final ClueTrackRecordMapper clueTrackRecordMapper;
    private final BusinessMapper businessMapper;
    private final UserMapper userMapper;

    @Autowired
    public ClueServiceImpl(ClueTrackRecordMapper clueTrackRecordMapper, BusinessMapper businessMapper, UserMapper userMapper) {
        this.clueTrackRecordMapper = clueTrackRecordMapper;
        this.businessMapper = businessMapper;
        this.userMapper = userMapper;
    }

    /**
     * 条件分页查询线索列表
     *
     * @param clueDTO
     * @return
     */
    @Override
    public PageResult<Clue> findByPageAndCondition(ClueDTO clueDTO) {
        Page<Clue> pageInfo = new Page<>(clueDTO.getPage(), clueDTO.getPageSize());

        pageInfo = baseMapper.findByPageAndCondition(pageInfo, clueDTO);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }

    /**
     * 条件分页查询线索池列表
     *
     * @param clueDTO
     * @return
     */
    @Override
    public PageResult<Clue> findPoolByPageAndCondition(ClueDTO clueDTO) {
        Page<Clue> pageInfo = new Page<>(clueDTO.getPage(), clueDTO.getPageSize());

        pageInfo = baseMapper.findPoolByPageAndCondition(pageInfo, clueDTO);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }

    /**
     * 根据ID查询线索详情（含跟进记录）
     *
     * @param id
     * @return
     */
    @Override
    public Clue getClueDetail(Integer id) {
        Clue clue = baseMapper.findDetailById(id);
        if (clue == null) {
            throw new BizException("没有查询到线索信息");
        }

        clue.setTrackRecords(clueTrackRecordMapper.findByClueId(id));

        return clue;
    }

    /**
     * 为指定用户分配线索：线索状态变为待跟进
     *
     * @param clueId
     * @param userId
     */
    @Override
    public void assign(Integer clueId, Integer userId) {
        Clue clueInDb = getById(clueId);
        if (clueInDb == null) {
            throw new BizException("没有查询到线索信息");
        }
        if (isFinalStatus(clueInDb.getStatus())) {
            throw new BizException("当前线索状态不允许分配");
        }
        if (userMapper.selectById(userId) == null) {
            throw new BizException("没有查询到用户信息");
        }

        Clue clue = new Clue();
        clue.setId(clueId);
        clue.setUserId(userId);
        clue.setStatus(STATUS_WAIT_FOLLOW);
        updateById(clue);
    }

    /**
     * 跟进线索：更新线索并新增一条跟进记录
     *
     * @param clueFollowDTO
     */
    @Override
    @Transactional
    public void follow(ClueFollowDTO clueFollowDTO) {
        Clue clueInDb = getById(clueFollowDTO.getId());
        if (clueInDb == null) {
            throw new BizException("没有查询到线索信息");
        }
        if (clueInDb.getUserId() == null) {
            throw new BizException("线索暂未分配，无法跟进");
        }
        if (!isFollowableStatus(clueInDb.getStatus())) {
            throw new BizException("当前线索状态不允许跟进");
        }

        Clue clue = new Clue();
        BeanUtil.copyProperties(clueFollowDTO, clue);
        // 归属人以后端数据为准，避免请求体中的快照值覆盖
        clue.setUserId(clueInDb.getUserId());
        updateById(clue);

        ClueTrackRecord trackRecord = new ClueTrackRecord();
        trackRecord.setClueId(clueFollowDTO.getId());
        trackRecord.setUserId(clueInDb.getUserId());
        trackRecord.setSubject(clueFollowDTO.getSubject());
        trackRecord.setLevel(clueFollowDTO.getLevel());
        trackRecord.setRecord(clueFollowDTO.getRecord());
        trackRecord.setNextTime(clueFollowDTO.getNextTime());
        trackRecord.setType(TRACK_TYPE_FOLLOW);
        clueTrackRecordMapper.insert(trackRecord);
    }

    /**
     * 伪线索处理：线索状态变为伪线索并新增一条伪线索记录
     *
     * @param id
     * @param clueFalseDTO
     */
    @Override
    @Transactional
    public void markFalse(Integer id, ClueFalseDTO clueFalseDTO) {
        Clue clueInDb = getById(id);
        if (clueInDb == null) {
            throw new BizException("没有查询到线索信息");
        }
        if (clueInDb.getUserId() == null) {
            throw new BizException("线索暂未分配，无法标记为伪线索");
        }
        if (!isFollowableStatus(clueInDb.getStatus())) {
            throw new BizException("当前线索状态不允许标记为伪线索");
        }

        Clue clue = new Clue();
        clue.setId(id);
        clue.setStatus(STATUS_FALSE);
        updateById(clue);

        ClueTrackRecord trackRecord = new ClueTrackRecord();
        trackRecord.setClueId(id);
        trackRecord.setUserId(clueInDb.getUserId());
        trackRecord.setSubject(clueInDb.getSubject());
        trackRecord.setLevel(clueInDb.getLevel());
        trackRecord.setRecord(clueFalseDTO.getRemark());
        trackRecord.setType(TRACK_TYPE_FALSE);
        trackRecord.setFalseReason(clueFalseDTO.getReason());
        clueTrackRecordMapper.insert(trackRecord);
    }

    /**
     * 转商机处理：线索状态变为转为商机并新增一条商机数据
     *
     * @param id
     */
    @Override
    @Transactional
    public void toBusiness(Integer id) {
        Clue clueInDb = getById(id);
        if (clueInDb == null) {
            throw new BizException("没有查询到线索信息");
        }

        // 条件更新，保证并发下同一条线索只会成功转为商机一次
        boolean updated = lambdaUpdate()
                .eq(Clue::getId, id)
                .ne(Clue::getStatus, STATUS_CONVERT_BUSINESS)
                .set(Clue::getStatus, STATUS_CONVERT_BUSINESS)
                .update();
        if (!updated) {
            throw new BizException("该线索已转为商机，请勿重复操作");
        }

        Business business = new Business();
        BeanUtil.copyProperties(clueInDb, business);
        business.setId(null);
        business.setClueId(clueInDb.getId());
        // 线索已有归属人时商机直接进入待跟进，否则进入商机池待分配
        business.setStatus(clueInDb.getUserId() == null ? STATUS_WAIT_ALLOT : STATUS_WAIT_FOLLOW);
        businessMapper.insert(business);
    }

    /**
     * 是否为终态（伪线索或已转商机），终态线索不允许再分配/跟进
     *
     * @param status
     * @return
     */
    private boolean isFinalStatus(Integer status) {
        return Integer.valueOf(STATUS_FALSE).equals(status) || Integer.valueOf(STATUS_CONVERT_BUSINESS).equals(status);
    }

    /**
     * 是否为可跟进的进行中状态（待跟进或跟进中）
     *
     * @param status
     * @return
     */
    private boolean isFollowableStatus(Integer status) {
        return Integer.valueOf(STATUS_WAIT_FOLLOW).equals(status) || Integer.valueOf(STATUS_FOLLOWING).equals(status);
    }
}
