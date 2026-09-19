package com.itheima.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.Result;
import com.itheima.entity.Course;
import com.itheima.entity.PageResult;
import com.itheima.mapper.CourseMapper;
import com.itheima.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    /**
     * 分页查询课程列表
     * @param name
     * @param subject
     * @param target
     * @param page
     * @param size
     * @return
     */
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

    /**
     * 删除课程
     * @param id
     */
    @Override
    public void deleteCourse(Integer id) {
        courseMapper.deleteById(id);
    }

    /**
     * 添加课程
     * @param course
     */
    @Override
    public void addCourse(Course course) {
        course.setCreateTime(LocalDateTime.now());
        course.setUpdateTime(LocalDateTime.now());
        courseMapper.insert(course);
    }

    /**
     * 根据id查询课程
     * @param id
     * @return
     */
    @Override
    public Course findById(Integer id) {
        return courseMapper.selectById(id);
    }

    /**
     * 修改课程
     * @param course
     */
    @Override
    public void updateCourse(Course course) {
        course.setUpdateTime(LocalDateTime.now());
        courseMapper.updateById(course);
    }

    /**
     * 查询所有课程
     * @return
     */
    @Override
    public List<Course> findAll() {
        return courseMapper.selectList(null);
    }

    /**
     * 根据科目查询课程
     * @param subject
     * @return
     */
    @Override
    public Object getCoursesBySubject(Integer subject) {
        return courseMapper.getCoursesBySubject(subject);
    }


}
