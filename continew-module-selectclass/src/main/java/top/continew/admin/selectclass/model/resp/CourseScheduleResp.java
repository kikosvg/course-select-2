package top.continew.admin.selectclass.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;

/**
 * 课程安排信息
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "课程安排信息")
public class CourseScheduleResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    @Schema(description = "课程ID")
    private String courseId;

    /**
     * 教师ID
     */
    @Schema(description = "教师ID")
    private String teacherId;

    /**
     * 学期
     */
    @Schema(description = "学期")
    private String semester;

    /**
     * 教室
     */
    @Schema(description = "教室")
    private String classroom;

    /**
     * 星期几（1-7）
     */
    @Schema(description = "星期几（1-7）")
    private Integer dayOfWeek;

    /**
     * 上课时间
     */
    @Schema(description = "上课时间")
    private LocalDateTime startTime;

    /**
     * 下课时间
     */
    @Schema(description = "下课时间")
    private LocalDateTime endTime;

    /**
     * 最大学生数
     */
    @Schema(description = "最大学生数")
    private Integer maxStudent;

    /**
     * 已选人数
     */
    @Schema(description = "已选人数")
    private Integer selected;

    /**
     * 状态（1：正常；2：已满；3：停课）
     */
    @Schema(description = "状态（1：正常；2：已满；3：停课）")
    private Integer status;
}