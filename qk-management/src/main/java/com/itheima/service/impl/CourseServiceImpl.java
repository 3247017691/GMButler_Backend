package com.itheima.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.entity.Course;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CourseMapper;
import com.itheima.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public PageResult findCoursesByPage(String name, Integer subject, Integer target, Integer page, Integer size) {
        Page<Course> p = new Page<>(page, size);

        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name), Course::getName, name)
                .eq(subject != null, Course::getSubject, subject)
                .eq(target != null, Course::getTarget, target);
        Page<Course> coursePage = courseMapper.selectPage(p, wrapper);
        return new PageResult<>(coursePage.getTotal(), coursePage.getRecords());
    }
}
