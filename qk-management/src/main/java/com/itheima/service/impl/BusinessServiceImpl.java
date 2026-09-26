package com.itheima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.context.UserContext;
import com.itheima.dto.BusinessDTO;
import com.itheima.dto.BusinessFollowDTO;
import com.itheima.entity.Business;
import com.itheima.entity.BusinessTrackRecord;
import com.itheima.entity.Customer;
import com.itheima.entity.PageResult;
import com.itheima.enums.BusinessStatus;
import com.itheima.exception.BizException;
import com.itheima.mapper.BusinessMapper;
import com.itheima.mapper.BusinessTrackRecordMapper;
import com.itheima.mapper.CustomerMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

    private final BusinessTrackRecordMapper businessTrackRecordMapper;
    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;

    @Autowired
    public BusinessServiceImpl(BusinessTrackRecordMapper businessTrackRecordMapper, CustomerMapper customerMapper, UserMapper userMapper) {
        this.businessTrackRecordMapper = businessTrackRecordMapper;
        this.customerMapper = customerMapper;
        this.userMapper = userMapper;
    }

    /**
     * 新增商机（仅管理员），进入待分配状态等待分配
     */
    @Override
    public void addBusiness(Business business) {
        requireAdmin();
        business.setStatus(BusinessStatus.WAIT_ALLOT.getCode());
        save(business);
    }

    /**
     * 条件分页查询商机列表
     * 管理员可查看全部商机；负责人强制按当前登录用户过滤，仅能看到自己负责的商机
     */
    @Override
    public PageResult<Business> findByPageAndCondition(BusinessDTO businessDTO) {
        if (!UserContext.isAdmin()) {
            businessDTO.setUserId(UserContext.getId());
        }

        Page<Business> pageInfo = new Page<>(businessDTO.getPage(), businessDTO.getPageSize());

        pageInfo = baseMapper.findByPageAndCondition(pageInfo, businessDTO);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }

    /**
     * 条件分页查询商机公海池列表（仅管理员）
     */
    @Override
    public PageResult<Business> findPoolByPageAndCondition(BusinessDTO businessDTO) {
        requireAdmin();

        Page<Business> pageInfo = new Page<>(businessDTO.getPage(), businessDTO.getPageSize());

        pageInfo = baseMapper.findPoolByPageAndCondition(pageInfo, businessDTO);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getRecords());
    }

    /**
     * 根据ID查询商机详情（含跟进记录），负责人仅能看到自己负责的商机
     */
    @Override
    public Business getBusinessDetail(Integer id) {
        Business business = baseMapper.findDetailById(id);
        if (business == null) {
            throw new BizException("没有查询到商机信息");
        }
        if (!UserContext.isAdmin()) {
            requireOwner(business);
        }

        business.setTrackRecords(businessTrackRecordMapper.findByBusinessId(id));

        return business;
    }

    /**
     * 为指定用户分配商机：商机状态变为待跟进（仅管理员）
     */
    @Override
    public void assign(Integer businessId, Integer userId) {
        requireAdmin();

        Business businessInDb = getById(businessId);
        if (businessInDb == null) {
            throw new BizException("没有查询到商机信息");
        }
        if (isFinalStatus(businessInDb.getStatus())) {
            throw new BizException("当前商机状态不允许分配");
        }
        if (userMapper.selectById(userId) == null) {
            throw new BizException("没有查询到用户信息");
        }

        Business business = new Business();
        business.setId(businessId);
        business.setUserId(userId);
        business.setStatus(BusinessStatus.WAIT_FOLLOW.getCode());
        updateById(business);
    }

    /**
     * 跟进商机：更新商机并新增一条跟进记录（仅商机负责人）
     */
    @Override
    @Transactional
    public void follow(BusinessFollowDTO businessFollowDTO) {
        Business businessInDb = getById(businessFollowDTO.getId());
        if (businessInDb == null) {
            throw new BizException("没有查询到商机信息");
        }
        requireOwner(businessInDb);
        if (!isFollowableStatus(businessInDb.getStatus())) {
            throw new BizException("当前商机状态不允许跟进");
        }

        Business business = new Business();
        BeanUtil.copyProperties(businessFollowDTO, business);
        // 归属人以后端数据为准，避免请求体中的快照值覆盖
        business.setUserId(businessInDb.getUserId());
        updateById(business);

        // DTO 同名字段直接拷贝；DTO 的 id 是商机ID需映射为 businessId，keyItems 需转为存储格式，userId 以后端数据为准
        BusinessTrackRecord trackRecord = BeanUtil.copyProperties(businessFollowDTO, BusinessTrackRecord.class, "id", "userId", "keyItems");
        trackRecord.setBusinessId(businessFollowDTO.getId());
        trackRecord.setUserId(businessInDb.getUserId());
        trackRecord.setKeyItems(formatKeyItems(businessFollowDTO.getKeyItems()));
        businessTrackRecordMapper.insert(trackRecord);
    }

    /**
     * 踢回公海：商机状态变为回收（仅商机负责人）
     */
    @Override
    public void backPool(Integer id) {
        Business businessInDb = getById(id);
        if (businessInDb == null) {
            throw new BizException("没有查询到商机信息");
        }
        requireOwner(businessInDb);
        if (!isFollowableStatus(businessInDb.getStatus())) {
            throw new BizException("当前商机状态不允许踢回公海");
        }

        Business business = new Business();
        business.setId(id);
        business.setStatus(BusinessStatus.RECYCLE.getCode());
        updateById(business);
    }

    /**
     * 转客户处理：商机状态变为转客户并新增一条客户数据（仅商机负责人）
     */
    @Override
    @Transactional
    public void toCustomer(Integer id) {
        Business businessInDb = getById(id);
        if (businessInDb == null) {
            throw new BizException("没有查询到商机信息");
        }
        requireOwner(businessInDb);

        // 条件更新，保证并发下同一条商机只会成功转为客户一次
        boolean updated = lambdaUpdate()
                .eq(Business::getId, id)
                .ne(Business::getStatus, BusinessStatus.CONVERT_CUSTOMER.getCode())
                .set(Business::getStatus, BusinessStatus.CONVERT_CUSTOMER.getCode())
                .update();
        if (!updated) {
            throw new BizException("该商机已转为客户，请勿重复操作");
        }

        Customer customer = new Customer();
        BeanUtil.copyProperties(businessInDb, customer);
        customer.setId(null);
        customer.setBusinessId(businessInDb.getId());
        customerMapper.insert(customer);
    }

    /**
     * 沟通重点转为存储格式，如 [课程, 价格]
     */
    private String formatKeyItems(List<String> keyItems) {
        return keyItems == null ? "[]" : keyItems.toString();
    }

    /**
     * 是否为终态（已转客户），终态商机不允许再分配
     */
    private boolean isFinalStatus(Integer status) {
        return BusinessStatus.of(status) == BusinessStatus.CONVERT_CUSTOMER;
    }

    /**
     * 校验当前用户为管理员
     */
    private void requireAdmin() {
        if (!UserContext.isAdmin()) {
            throw new BizException("只有管理员可以执行该操作");
        }
    }

    /**
     * 校验当前用户为商机负责人
     */
    private void requireOwner(Business businessInDb) {
        Integer userId = businessInDb.getUserId();
        if (userId == null || !userId.equals(UserContext.getId())) {
            throw new BizException("只能操作自己负责的商机");
        }
    }

    /**
     * 是否为可跟进的进行中状态（待跟进或跟进中）
     */
    private boolean isFollowableStatus(Integer status) {
        BusinessStatus businessStatus = BusinessStatus.of(status);
        return businessStatus == BusinessStatus.WAIT_FOLLOW || businessStatus == BusinessStatus.FOLLOWING;
    }
}
