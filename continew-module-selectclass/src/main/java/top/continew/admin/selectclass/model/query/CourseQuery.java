package top.continew.admin.selectclass.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;
import java.math.BigDecimal;

/**
 * 课程查询条件
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "课程查询条件")
public class CourseQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    @Schema(description = "课程编号")
    @Query(type = QueryType.EQ)
    private String courseNo;

    /**
     * 课程名称
     */
    @Schema(description = "课程名称")
    @Query(type = QueryType.EQ)
    private String name;

    /**
     * 学分(10分制)
     */
    @Schema(description = "学分(10分制)")
    @Query(type = QueryType.EQ)
    private BigDecimal credit;

    /**
     * 课时
     */
    @Schema(description = "课时")
    @Query(type = QueryType.EQ)
    private Integer hours;

    /**
     * 类型（1：必修；2：选修）
     */
    @Schema(description = "类型（1：必修；2：选修）")
    @Query(type = QueryType.EQ)
    private Integer type;

    /**
     * 开课学院ID
     */
    @Schema(description = "开课学院ID")
    @Query(type = QueryType.EQ)
    private Long collegeId;

    /**
     * 状态（1：正常；2：停课）
     */
    @Schema(description = "状态（1：正常；2：停课）")
    @Query(type = QueryType.EQ)
    private Integer status;
}