package com.itheima.dto;

import lombok.Data;

/**
 * /users?name=张&status=1&phone=133****1111&deptId=5&page=1&pageSize=5
 * 封装查询参数
 */
@Data
public class UserDTO {
    private String name; // 姓名
    private Integer status; // 状态
    private String phone; // 手机
    private Integer deptId; // 部门
    private Integer page = 1; // 页码
    private Integer pageSize = 10; // 每页条数
}
