package top.continew.admin.selectclass.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 创建或修改课程安排参数
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "创建或修改课程安排参数")
public class CourseScheduleReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    @Schema(description = "课程ID")
    @NotNull(message = "课程ID不能为空")
    private String courseId;

    /**
     * 教师ID
     */
    @Schema(description = "教师ID")
    @NotNull(message = "教师ID不能为空")
    private String teacherId;

    /**
     * 学期
     */
    @Schema(description = "学期")
    @NotBlank(message = "学期不能为空")
    @Length(max = 20, message = "学期长度不能超过 {max} 个字符")
    private String semester;

    /**
     * 教室
     */
    @Schema(description = "教室")
    @NotBlank(message = "教室不能为空")
    @Length(max = 50, message = "教室长度不能超过 {max} 个字符")
    private String classroom;

    /**
     * 星期几（1-7）
     */
    @Schema(description = "星期几（1-7）")
    @NotNull(message = "星期几（1-7）不能为空")
    private Integer dayOfWeek;

    /**
     * 上课时间
     */
    @Schema(description = "上课时间")
    @NotNull(message = "上课时间不能为空")
    private LocalDateTime startTime;

    /**
     * 下课时间
     */
    @Schema(description = "下课时间")
    @NotNull(message = "下课时间不能为空")
    private LocalDateTime endTime;

    /**
     * 最大学生数
     */
    @Schema(description = "最大学生数")
    @NotNull(message = "最大学生数不能为空")
    private Integer maxStudent;

    /**
     * 状态（1：正常；2：已满；3：停课）
     */
    @Schema(description = "状态（1：正常；2：已满；3：停课）")
    @NotNull(message = "状态（1：正常；2：已满；3：停课）不能为空")
    private Integer status;
}