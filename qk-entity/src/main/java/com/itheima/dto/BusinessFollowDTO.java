package com.itheima.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商机跟进请求参数
 */
@Data
public class BusinessFollowDTO {
    private Integer id;              // 商机ID
    private String name;             // 客户姓名
    private String phone;            // 手机号
    private Integer gender;          // 性别
    private Integer age;             // 年龄
    private String wechat;           // 微信号
    private String qq;               // QQ号
    private Integer subject;         // 意向学科
    private Integer courseId;        // 意向课程
    private Integer degree;          // 学历
    private Integer jobStatus;       // 在职情况
    private Integer channel;         // 渠道来源
    private String remark;           // 备注
    private Integer status;          // 商机状态
    private Integer userId;          // 归属人ID
    private Integer clueId;          // 归属线索ID
    private LocalDateTime nextTime;  // 下次跟进时间
    private List<String> keyItems;   // 沟通重点
    private Integer trackStatus;     // 跟进状态
    private String record;           // 本次跟进内容
}
