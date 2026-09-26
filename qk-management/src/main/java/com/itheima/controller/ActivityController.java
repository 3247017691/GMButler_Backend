package com.itheima.controller;


import com.itheima.common.Result;
import com.itheima.dto.ActivityDTO;
import com.itheima.entity.Activity;
import com.itheima.entity.PageResult;
import com.itheima.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/activities")
@RestController
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * 活动列表查询
     * @param activityDTO
     * @return
     */
    @GetMapping
    public Result getActivities(ActivityDTO activityDTO) {
        PageResult<Activity> result = activityService.getActivities(activityDTO);
        return Result.success(result);
    }

    /**
     * 删除活动
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteActivity(@PathVariable Long id) {
        activityService.removeById(id);
        return Result.success();
    }

    /**
     * 保存活动
     * @param activity
     * @return
     */
    @PostMapping
    public Result saveActivity(@RequestBody Activity activity) {
        activityService.save(activity);
        return Result.success();
    }

    /**
     * 根据id获取活动
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getActivity(@PathVariable Long id) {
        Activity activity = activityService.getById(id);
        return Result.success(activity);
    }

    /**
     * 修改活动
     * @param activity
     * @return
     */
    @PutMapping
    public Result updateActivity(@RequestBody Activity activity) {
        activityService.updateById(activity);
        return Result.success();
    }

    /**
     * 根据类型获取活动
     * @param type
     * @return
     */
    @GetMapping("/type/{type}")
    public Result getActivitiesByType(@PathVariable String type) {
        List<Activity> activities = activityService.getActivityByType(type);
        return Result.success(activities);
    }
}
