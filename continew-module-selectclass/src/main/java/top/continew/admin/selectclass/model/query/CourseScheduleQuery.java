package top.continew.admin.selectclass.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 课程安排查询条件
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "课程安排查询条件")
public class CourseScheduleQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    @Schema(description = "课程ID")
    @Query(type = QueryType.EQ)
    private String courseId;

    /**
     * 教师ID
     */
    @Schema(description = "教师ID")
    @Query(type = QueryType.EQ)
    private String teacherId;

    /**
     * 学期
     */
    @Schema(description = "学期")
    @Query(type = QueryType.EQ)
    private String semester;

    /**
     * 教室
     */
    @Schema(description = "教室")
    @Query(type = QueryType.EQ)
    private String classroom;

    /**
     * 星期几（1-7）
     */
    @Schema(description = "星期几（1-7）")
    @Query(type = QueryType.EQ)
    private Integer dayOfWeek;

    /**
     * 上课时间
     */
    @Schema(description = "上课时间")
    @Query(type = QueryType.EQ)
    private LocalDateTime startTime;

    /**
     * 下课时间
     */
    @Schema(description = "下课时间")
    @Query(type = QueryType.EQ)
    private LocalDateTime endTime;

    /**
     * 最大学生数
     */
    @Schema(description = "最大学生数")
    @Query(type = QueryType.EQ)
    private Integer maxStudent;

    /**
     * 已选人数
     */
    @Schema(description = "已选人数")
    @Query(type = QueryType.EQ)
    private Integer selected;

    /**
     * 状态（1：正常；2：已满；3：停课）
     */
    @Schema(description = "状态（1：正常；2：已满；3：停课）")
    @Query(type = QueryType.EQ)
    private Integer status;
}