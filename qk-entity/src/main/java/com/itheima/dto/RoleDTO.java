package com.itheima.dto;

import lombok.Data;

@Data
public class RoleDTO {
    private String name; // 角色名称
    private String label; // 角色标签
    private String remark; // 备注
    private Integer page = 1; // 页码
    private Integer pageSize = 10; // 每页条数
}
