package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.ActivityDTO;
import com.itheima.entity.Activity;
import com.itheima.entity.PageResult;

import java.util.List;

public interface ActivityService extends IService<Activity> {
    /**
     * 活动列表查询
     *
     * @param activityDTO
     * @return
     */
    PageResult<Activity> getActivities(ActivityDTO activityDTO);

    /**
     * 根据类型获取活动
     * @param type
     * @return
     */
    List<Activity> getActivityByType(String type);
}
