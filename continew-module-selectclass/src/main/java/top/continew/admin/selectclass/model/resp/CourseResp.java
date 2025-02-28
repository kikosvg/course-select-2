package top.continew.admin.selectclass.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;
import java.math.BigDecimal;

/**
 * 课程信息
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "课程信息")
public class CourseResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    @Schema(description = "课程编号")
    private String courseNo;

    /**
     * 课程名称
     */
    @Schema(description = "课程名称")
    private String name;

    /**
     * 学分(10分制)
     */
    @Schema(description = "学分(10分制)")
    private BigDecimal credit;

    /**
     * 课时
     */
    @Schema(description = "课时")
    private Integer hours;

    /**
     * 类型（1：必修；2：选修）
     */
    @Schema(description = "类型（1：必修；2：选修）")
    private Integer type;

    /**
     * 开课学院ID
     */
    @Schema(description = "开课学院ID")
    private Long collegeId;

    /**
     * 课程描述
     */
    @Schema(description = "课程描述")
    private String description;

    /**
     * 状态（1：正常；2：停课）
     */
    @Schema(description = "状态（1：正常；2：停课）")
    private Integer status;
}