package com.itheima.enums;

/**
 * 线索状态
 */
public enum ClueStatus {

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
     * 伪线索
     */
    FALSE(4, "伪线索"),

    /**
     * 转为商机
     */
    CONVERT_BUSINESS(5, "转为商机");

    /**
     * 状态值
     */
    private final int code;

    /**
     * 状态描述
     */
    private final String desc;

    ClueStatus(int code, String desc) {
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
    public static ClueStatus of(Integer code) {
        if (code == null) {
            return null;
        }
        for (ClueStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
