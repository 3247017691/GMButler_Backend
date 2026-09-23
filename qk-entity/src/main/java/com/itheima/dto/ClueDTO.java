package com.itheima.dto;

import lombok.Data;

/**
 * 线索分页条件查询参数
 */
@Data
public class ClueDTO {
    private Integer clueId;     // 线索ID
    private String name;        // 客户姓名
    private String phone;       // 手机号
    private Integer channel;    // 渠道来源
    private Integer activityId; // 活动ID
    private Integer userId;     // 归属人ID
    private String assignName;  // 归属人姓名
    private Integer status;     // 线索状态
    private Integer subject;    // 意向学科
    private Integer level;      // 意向等级
    private Integer page = 1;       // 页码
    private Integer pageSize = 10;  // 每页条数
}
