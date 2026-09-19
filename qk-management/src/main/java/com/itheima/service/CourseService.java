package com.itheima.service;

import com.itheima.common.Result;
import com.itheima.entity.Course;
import com.itheima.entity.PageResult;

import java.util.List;

public interface CourseService {

    /**
     * 分页查询课程列表
     * @param name
     * @param subject
     * @param target
     * @param page
     * @param size
     * @return
     */
    PageResult findCoursesByPage(String name, Integer subject, Integer target, Integer page, Integer size);

    /**
     * 删除课程
     * @param id
     */
    void deleteCourse(Integer id);

    /**
     * 添加课程
     * @param course
     */
    void addCourse(Course course);

    /**
     * 根据id查询课程
     * @param id
     * @return
     */
    Course findById(Integer id);

    /**
     * 修改课程
     * @param course
     */
    void updateCourse(Course course);

    /**
     * 查询所有课程
     * @return
     */
    List<Course> findAll();

    /**
     *
     * @param subject
     * @return
     */
    Object getCoursesBySubject(Integer subject);
}
