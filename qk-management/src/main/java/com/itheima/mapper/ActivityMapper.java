package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
    /**
     * 根据类型获取活动
     * @param type
     * @return
     */
    List<Activity> getActivityByType(String type);
}
