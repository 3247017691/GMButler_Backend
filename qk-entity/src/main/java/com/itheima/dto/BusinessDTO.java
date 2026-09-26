package com.itheima.dto;

import lombok.Data;

/**
 * 商机分页条件查询参数
 */
@Data
public class BusinessDTO {
    private Integer businessId;   // 商机ID
    private String name;          // 客户姓名
    private String phone;         // 手机号
    private Integer status;       // 商机状态
    private String assignName;    // 负责人姓名
    private Integer subject;     // 意向学科
    private Integer userId;      // 归属人ID（非管理员查询时后端强制设置为当前用户）
    private Integer page = 1;       // 页码
    private Integer pageSize = 10;  // 每页条数
}
