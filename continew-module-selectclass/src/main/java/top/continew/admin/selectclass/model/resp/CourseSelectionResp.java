package top.continew.admin.selectclass.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;
import java.math.BigDecimal;

/**
 * 选课记录信息
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "选课记录信息")
public class CourseSelectionResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    @Schema(description = "学生ID")
    private String studentId;

    /**
     * 课程安排ID
     */
    @Schema(description = "课程安排ID")
    private String scheduleId;

    /**
     * 选课时间
     */
    @Schema(description = "选课时间")
    private LocalDateTime selectTime;

    /**
     * 成绩
     */
    @Schema(description = "成绩")
    private BigDecimal score;

    /**
     * 状态（1：已选；2：退选；3：已修完）
     */
    @Schema(description = "状态（1：已选；2：退选；3：已修完）")
    private Integer status;
}