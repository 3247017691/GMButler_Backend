package com.itheima.enums;

/**
 * 线索跟进记录类型
 */
public enum ClueTrackType {

    /**
     * 正常跟进
     */
    FOLLOW(1, "正常跟进"),

    /**
     * 伪线索
     */
    FALSE(2, "伪线索");

    /**
     * 类型值
     */
    private final int code;

    /**
     * 类型描述
     */
    private final String desc;

    ClueTrackType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
