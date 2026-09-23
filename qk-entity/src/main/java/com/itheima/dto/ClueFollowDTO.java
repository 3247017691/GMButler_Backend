package com.itheima.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 线索跟进请求参数
 */
@Data
public class ClueFollowDTO {
    private Integer id;             // 线索ID
    private String phone;           // 手机号
    private Integer channel;        // 渠道来源
    private Integer activityId;     // 活动ID
    private String name;            // 客户姓名
    private Integer gender;         // 性别
    private Integer age;            // 年龄
    private String wechat;          // 微信号
    private String qq;              // QQ号
    private Integer userId;         // 归属人ID
    private Integer status;         // 线索状态
    private Integer subject;        // 意向学科
    private Integer level;          // 意向等级
    private LocalDateTime nextTime; // 下次跟进时间
    private String record;          // 本次跟进记录内容
}
