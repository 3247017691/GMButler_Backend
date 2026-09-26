package com.itheima.service.impl;

import com.itheima.mapper.BusinessMapper;
import com.itheima.mapper.ClueMapper;
import com.itheima.service.ReportService;
import com.itheima.vo.OverviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询首页线索、商机的概览数据，优先取缓存
     */
    @Override
    public OverviewVO getOverview() {
        // 1. 查询redis缓存中的数据
        OverviewVO overviewVO = (OverviewVO) redisTemplate.opsForValue().get("OverviewVO");
        if (null != overviewVO) {
            return overviewVO;
        }

        // 2. 查询数据库
        // 2.1 获取线索概览数据
        OverviewVO clueOverviewVO = clueMapper.getClueOverviewData();
        // 2.2 获取商机概览数据
        OverviewVO businessOverviewVO = businessMapper.getBusinessOverviewData();
        // 2.3 合并数据返回，保留线索侧的6个字段
        BeanUtils.copyProperties(businessOverviewVO, clueOverviewVO, "clueTotal", "clueWaitAllot", "clueWaitFollow", "clueFollowing", "clueFalse", "clueConvertBusiness");

        // 3. 缓存数据
        log.info("查询数据库中的数据: {}, 缓存到redis中", clueOverviewVO);
        redisTemplate.opsForValue().set("OverviewVO", clueOverviewVO, 5, TimeUnit.MINUTES);

        return clueOverviewVO;
    }
}
