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
 * 创建或修改选课记录参数
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "创建或修改选课记录参数")
public class CourseSelectionReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 学生ID
     */
    @Schema(description = "学生ID")
    @NotNull(message = "学生ID不能为空")
    private String studentId;

    /**
     * 课程安排ID
     */
    @Schema(description = "课程安排ID")
    @NotNull(message = "课程安排ID不能为空")
    private String scheduleId;

    /**
     * 选课时间
     */
    @Schema(description = "选课时间")
    @NotNull(message = "选课时间不能为空")
    private LocalDateTime selectTime;

    /**
     * 状态（1：已选；2：退选；3：已修完）
     */
    @Schema(description = "状态（1：已选；2：退选；3：已修完）")
    @NotNull(message = "状态（1：已选；2：退选；3：已修完）不能为空")
    private Integer status;
}