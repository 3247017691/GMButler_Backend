package com.itheima.dto;

import lombok.Data;

/**
 * 伪线索处理请求参数
 */
@Data
public class ClueFalseDTO {
    private Integer reason;  // 伪线索原因，1：空号/错号，2：停机，3：无法接通，4：无意向，5：其他
    private String remark;   // 备注说明
}
