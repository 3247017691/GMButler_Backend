package com.itheima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.context.UserContext;
import com.itheima.dto.ClueDTO;
import com.itheima.dto.ClueFalseDTO;
import com.itheima.dto.ClueFollowDTO;
import com.itheima.entity.Business;
import com.itheima.entity.Clue;
import com.itheima.entity.ClueTrackRecord;
import com.itheima.entity.PageResult;
import com.itheima.enums.BusinessStatus;
import com.itheima.enums.ClueStatus;
import com.itheima.enums.ClueTrackType;
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
     * 新增线索（仅管理员）
     *
     * @param clue
     */
    @Override
    public void addClue(Clue clue) {
        requireAdmin();
        save(clue);
    }

    /**
     * 删除线索（仅管理员）
     *
     * @param id
     */
    @Override
    public void deleteClue(Integer id) {
        requireAdmin();
        boolean flag = removeById(id);
        if (!flag) {
            throw new BizException("删除失败");
        }
    }

    /**
     * 条件分页查询线索列表
     * 管理员可查看全部线索；负责人强制按当前登录用户过滤，仅能看到自己负责的线索
     *
     * @param clueDTO
     * @return
     */
    @Override
    public PageResult<Clue> findByPageAndCondition(ClueDTO clueDTO) {
        if (!UserContext.isAdmin()) {
            clueDTO.setUserId(UserContext.getId());
        }

        Page<Clue> pageInfo = new Page<>(clueDTO.getPage(), clueDTO.getPageSize());

        pageInfo = baseMapper.findByPageAndCondition(pageInfo, clueDTO);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }

    /**
     * 条件分页查询线索池列表（仅管理员）
     *
     * @param clueDTO
     * @return
     */
    @Override
    public PageResult<Clue> findPoolByPageAndCondition(ClueDTO clueDTO) {
        requireAdmin();

        Page<Clue> pageInfo = new Page<>(clueDTO.getPage(), clueDTO.getPageSize());

        pageInfo = baseMapper.findPoolByPageAndCondition(pageInfo, clueDTO);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }

    /**
     * 根据ID查询线索详情（含跟进记录），负责人仅能看到自己负责的线索
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
        if (!UserContext.isAdmin()) {
            requireOwner(clue);
        }

        clue.setTrackRecords(clueTrackRecordMapper.findByClueId(id));

        return clue;
    }

    /**
     * 为指定用户分配线索：线索状态变为待跟进（仅管理员）
     *
     * @param clueId
     * @param userId
     */
    @Override
    public void assign(Integer clueId, Integer userId) {
        requireAdmin();

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
        clue.setStatus(ClueStatus.WAIT_FOLLOW.getCode());
        updateById(clue);
    }

    /**
     * 跟进线索：更新线索并新增一条跟进记录（仅线索负责人）
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
        requireOwner(clueInDb);
        if (!isFollowableStatus(clueInDb.getStatus())) {
            throw new BizException("当前线索状态不允许跟进");
        }

        Clue clue = new Clue();
        BeanUtil.copyProperties(clueFollowDTO, clue);
        // 归属人以后端数据为准，避免请求体中的快照值覆盖
        clue.setUserId(clueInDb.getUserId());
        updateById(clue);

        // DTO 同名字段直接拷贝；DTO 的 id 是线索ID需映射为 clueId，userId 以后端数据为准
        ClueTrackRecord trackRecord = BeanUtil.copyProperties(clueFollowDTO, ClueTrackRecord.class, "id", "userId");
        trackRecord.setClueId(clueFollowDTO.getId());
        trackRecord.setUserId(clueInDb.getUserId());
        trackRecord.setType(ClueTrackType.FOLLOW.getCode());
        clueTrackRecordMapper.insert(trackRecord);
    }

    /**
     * 伪线索处理：线索状态变为伪线索并新增一条伪线索记录（仅线索负责人）
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
        requireOwner(clueInDb);
        if (!isFollowableStatus(clueInDb.getStatus())) {
            throw new BizException("当前线索状态不允许标记为伪线索");
        }

        Clue clue = new Clue();
        clue.setId(id);
        clue.setStatus(ClueStatus.FALSE.getCode());
        updateById(clue);

        ClueTrackRecord trackRecord = new ClueTrackRecord();
        trackRecord.setClueId(id);
        trackRecord.setUserId(clueInDb.getUserId());
        trackRecord.setSubject(clueInDb.getSubject());
        trackRecord.setLevel(clueInDb.getLevel());
        trackRecord.setRecord(clueFalseDTO.getRemark());
        trackRecord.setType(ClueTrackType.FALSE.getCode());
        trackRecord.setFalseReason(clueFalseDTO.getReason());
        clueTrackRecordMapper.insert(trackRecord);
    }

    /**
     * 转商机处理：线索状态变为转为商机并新增一条商机数据（仅线索负责人）
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
        requireOwner(clueInDb);

        // 条件更新，保证并发下同一条线索只会成功转为商机一次
        boolean updated = lambdaUpdate()
                .eq(Clue::getId, id)
                .ne(Clue::getStatus, ClueStatus.CONVERT_BUSINESS.getCode())
                .set(Clue::getStatus, ClueStatus.CONVERT_BUSINESS.getCode())
                .update();
        if (!updated) {
            throw new BizException("该线索已转为商机，请勿重复操作");
        }

        Business business = new Business();
        BeanUtil.copyProperties(clueInDb, business);
        business.setId(null);
        business.setClueId(clueInDb.getId());
        // 线索已有归属人时商机直接进入待跟进，否则进入商机池待分配
        BusinessStatus businessStatus = clueInDb.getUserId() == null ? BusinessStatus.WAIT_ALLOT : BusinessStatus.WAIT_FOLLOW;
        business.setStatus(businessStatus.getCode());
        businessMapper.insert(business);
    }

    /**
     * 是否为终态（伪线索或已转商机），终态线索不允许再分配/跟进
     *
     * @param status
     * @return
     */
    private boolean isFinalStatus(Integer status) {
        ClueStatus clueStatus = ClueStatus.of(status);
        return clueStatus == ClueStatus.FALSE || clueStatus == ClueStatus.CONVERT_BUSINESS;
    }

    /**
     * 校验当前用户为管理员
     */
    private void requireAdmin() {
        if (!UserContext.isAdmin()) {
            throw new BizException("只有管理员可以执行该操作");
        }
    }

    /**
     * 校验当前用户为线索负责人
     */
    private void requireOwner(Clue clueInDb) {
        Integer userId = clueInDb.getUserId();
        if (userId == null || !userId.equals(UserContext.getId())) {
            throw new BizException("只能操作自己负责的线索");
        }
    }

    /**
     * 是否为可跟进的进行中状态（待跟进或跟进中）
     *
     * @param status
     * @return
     */
    private boolean isFollowableStatus(Integer status) {
        ClueStatus clueStatus = ClueStatus.of(status);
        return clueStatus == ClueStatus.WAIT_FOLLOW || clueStatus == ClueStatus.FOLLOWING;
    }
}
