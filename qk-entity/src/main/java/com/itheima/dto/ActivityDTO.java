package com.itheima.dto;

import lombok.Data;

@Data
public class ActivityDTO {
    private Integer channel;    // 渠道
    private Integer type;    // 类型
    private Integer page;    // 页码
    private Integer pageSize;    // 每页大小
}
