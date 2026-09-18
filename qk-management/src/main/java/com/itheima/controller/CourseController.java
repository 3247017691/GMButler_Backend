package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.entity.PageResult;
import com.itheima.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
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
    @GetMapping("/courses")
    public Result listCourses(
            String name,
            Integer subject,
            Integer target,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        PageResult pageResult = courseService.findCoursesByPage(name, subject, target, page, size);
        return Result.success(pageResult);
    }

    @DeleteMapping("/courses/{id}")
    public Result deleteCourse(Integer id){
        courseService.deleteCourse(id);
        return Result.success();
    }
}
