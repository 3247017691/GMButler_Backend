package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dto.ActivityDTO;
import com.itheima.entity.Activity;
import com.itheima.entity.PageResult;
import com.itheima.mapper.ActivityMapper;
import com.itheima.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    private final ActivityMapper activityMapper;

    @Autowired
    public ActivityServiceImpl(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    /**
     * 活动列表查询
     * @param activityDTO
     * @return
     */
    @Override
    public PageResult<Activity> getActivities(ActivityDTO activityDTO) {
        Page<Activity> page = new Page<Activity>(activityDTO.getPage(), activityDTO.getPageSize());

        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<Activity>();
        queryWrapper.eq(activityDTO.getChannel() != null, Activity::getChannel, activityDTO.getChannel());
        queryWrapper.eq(activityDTO.getType() != null, Activity::getType, activityDTO.getType());
        queryWrapper.orderByDesc(Activity::getCreateTime);
        page(page, queryWrapper);

        PageResult<Activity> pb = new PageResult<>(page.getTotal(), page.getRecords());

        return pb;
    }

    /**
     * 根据类型获取活动
     * @param type
     * @return
     */
    @Override
    public List<Activity> getActivityByType(String type) {
        return activityMapper.getActivityByType(type);
    }
}
