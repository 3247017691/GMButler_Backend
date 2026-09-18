package com.itheima.service;

import com.itheima.entity.PageResult;

public interface CourseService {
    PageResult findCoursesByPage(String name, Integer subject, Integer target, Integer page, Integer size);
}
