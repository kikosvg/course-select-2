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
 * 选课记录查询条件
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "选课记录查询条件")
public class CourseSelectionQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    @Schema(description = "学生ID")
    @Query(type = QueryType.LIKE_LEFT)
    private String studentId;

    /**
     * 课程安排ID
     */
    @Schema(description = "课程安排ID")
    @Query(type = QueryType.LIKE_LEFT)
    private String scheduleId;

    /**
     * 选课时间
     */
    @Schema(description = "选课时间")
    @Query(type = QueryType.LIKE)
    private LocalDateTime selectTime;

    /**
     * 状态（1：已选；2：退选；3：已修完）
     */
    @Schema(description = "状态（1：已选；2：退选；3：已修完）")
    @Query(type = QueryType.EQ)
    private Integer status;
}