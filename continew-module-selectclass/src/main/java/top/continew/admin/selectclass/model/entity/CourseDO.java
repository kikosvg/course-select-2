package top.continew.admin.selectclass.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 课程实体
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@TableName("course")
public class CourseDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    private String courseNo;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 学分(10分制)
     */
    private BigDecimal credit;

    /**
     * 课时
     */
    private Integer hours;

    /**
     * 类型（1：必修；2：选修）
     */
    private Integer type;

    /**
     * 开课学院ID
     */
    private Long collegeId;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 状态（1：正常；2：停课）
     */
    private Integer status;
}