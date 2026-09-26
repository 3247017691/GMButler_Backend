package com.itheima.enums;

/**
 * 商机状态
 */
public enum BusinessStatus {

    /**
     * 待分配
     */
    WAIT_ALLOT(1, "待分配"),

    /**
     * 待跟进
     */
    WAIT_FOLLOW(2, "待跟进"),

    /**
     * 跟进中
     */
    FOLLOWING(3, "跟进中"),

    /**
     * 回收
     */
    RECYCLE(4, "回收"),

    /**
     * 转客户
     */
    CONVERT_CUSTOMER(5, "转客户");

    /**
     * 状态值
     */
    private final int code;

    /**
     * 状态描述
     */
    private final String desc;

    BusinessStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据状态值获取枚举
     *
     * @param code
     * @return 未匹配时返回 null
     */
    public static BusinessStatus of(Integer code) {
        if (code == null) {
            return null;
        }
        for (BusinessStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
