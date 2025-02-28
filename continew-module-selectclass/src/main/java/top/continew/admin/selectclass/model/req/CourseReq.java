package top.continew.admin.selectclass.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;
import java.math.BigDecimal;

/**
 * 创建或修改课程参数
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "创建或修改课程参数")
public class CourseReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    @Schema(description = "课程编号")
    @NotBlank(message = "课程编号不能为空")
    @Length(max = 20, message = "课程编号长度不能超过 {max} 个字符")
    private String courseNo;

    /**
     * 课程名称
     */
    @Schema(description = "课程名称")
    @NotBlank(message = "课程名称不能为空")
    @Length(max = 100, message = "课程名称长度不能超过 {max} 个字符")
    private String name;

    /**
     * 学分(10分制)
     */
    @Schema(description = "学分(10分制)")
    @NotNull(message = "学分(10分制)不能为空")
    private BigDecimal credit;

    /**
     * 课时
     */
    @Schema(description = "课时")
    @NotNull(message = "课时不能为空")
    private Integer hours;

    /**
     * 类型（1：必修；2：选修）
     */
    @Schema(description = "类型（1：必修；2：选修）")
    @NotNull(message = "类型（1：必修；2：选修）不能为空")
    private Integer type;

    /**
     * 开课学院ID
     */
    @Schema(description = "开课学院ID")
    @NotNull(message = "开课学院ID不能为空")
    private Long collegeId;

    /**
     * 课程描述
     */
    @Schema(description = "课程描述")
    @Length(max = 500, message = "课程描述长度不能超过 {max} 个字符")
    private String description;

    /**
     * 状态（1：正常；2：停课）
     */
    @Schema(description = "状态（1：正常；2：停课）")
    @NotNull(message = "状态（1：正常；2：停课）不能为空")
    private Integer status;
}