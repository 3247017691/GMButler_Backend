package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程实体类
 */
@Data
@TableName("course")
public class Course {
    /**
     * 课程ID
     */
    @TableId
    private Integer id;

    /**
     * 课程科目
     */
    private Integer subject;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程价格
     */
    private Integer price;

    /**
     * 课程目标
     */
    private String target;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
