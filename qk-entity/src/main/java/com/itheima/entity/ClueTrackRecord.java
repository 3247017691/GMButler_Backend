package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 线索跟进记录实体类
 * 对应数据库表 clue_track_record
 */
@Data
@TableName("clue_track_record")
public class ClueTrackRecord {

    /**
     * 跟进记录ID，主键
     */
    @TableId
    private Integer id;

    /**
     * 线索ID，关联线索表主键
     */
    private Integer clueId;

    /**
     * 跟进人ID，关联用户ID
     */
    private Integer userId;

    /**
     * 意向学科，1：AI智能应用开发(Java)，2：AI大模型开发(Python)，3：AI鸿蒙开发，4：AI大数据，5：AI嵌入式，6：AI测试，7：AI运维
     */
    private Integer subject;

    /**
     * 意向等级，1：近期学习，2：打算学习(考虑中)，3：进行了解，4：打酱油
     */
    private Integer level;

    /**
     * 跟进记录内容
     */
    private String record;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextTime;

    /**
     * 记录类型，1：正常跟进，2：伪线索
     */
    private Integer type;

    /**
     * 伪线索原因，1：空号/错号，2：停机，3：无法接通，4：无意向，5：其他
     */
    private Integer falseReason;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 跟进人姓名（关联用户表查询，非表字段）
     */
    @TableField(exist = false)
    private String assignName;
}
