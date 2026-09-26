package com.itheima.dto;

import lombok.Data;

/**
 * 客户分页条件查询参数
 */
@Data
public class CustomerDTO {
    private Integer page = 1;       // 页码
    private Integer pageSize = 10;  // 每页条数
    private String phone;           // 手机号，模糊匹配
    private String name;            // 客户姓名，模糊匹配
    private Integer channel;        // 渠道来源，精确匹配
    private Integer subject;        // 意向学科，精确匹配
}
