package com.itheima.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色标签
     */
    private String label;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
