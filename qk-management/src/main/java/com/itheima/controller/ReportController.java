package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.service.ReportService;
import com.itheima.vo.OverviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * 查询首页线索、商机的概览数据
     *
     * @return
     */
    @GetMapping("/overview")
    public Result getOverview() {
        OverviewVO overview = reportService.getOverview();
        return Result.success(overview);
    }
}
