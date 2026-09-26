package com.itheima.service;

import com.itheima.vo.OverviewVO;

public interface ReportService {

    /**
     * 查询首页线索、商机的概览数据
     */
    OverviewVO getOverview();
}
