package com.itheima.entity;

import java.util.List;

public class PageResult<T> {
    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页结果
     */
    private List<T> rows;
}
