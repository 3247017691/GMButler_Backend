package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 客户实体类
 * 对应数据库表 customer
 */
@Data
@TableName("customer")
public class Customer {

    /**
     * 客户ID，主键
     */
    @TableId
    private Integer id;

    /**
     * 手机号，唯一
     */
    private String phone;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 渠道来源，1：线上活动，2：推广介绍
     */
    private Integer channel;

    /**
     * 性别，1：男，2：女
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 学历，1：高中，2：中专，3：大专，4：本科，5：硕士，6：博士，7：其他
     */
    private Integer degree;

    /**
     * 在职情况，1：在职，0：离职
     */
    private Integer jobStatus;

    /**
     * 意向学科，1：AI智能应用开发(Java)，2：AI大模型开发(Python)，3：AI鸿蒙开发，4：AI大数据，5：AI嵌入式，6：AI测试，7：AI运维
     */
    private Integer subject;

    /**
     * 意向课程，课程id
     */
    private Integer courseId;

    /**
     * 来源商机id
     */
    private Integer businessId;

    /**
     * 意向课程名称（关联课程表查询，非表字段）
     */
    @TableField(exist = false)
    private String courseName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
