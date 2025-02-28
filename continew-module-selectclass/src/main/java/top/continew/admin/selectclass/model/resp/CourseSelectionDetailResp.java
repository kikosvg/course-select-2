package top.continew.admin.selectclass.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.*;
import java.math.BigDecimal;

/**
 * 选课记录详情信息
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "选课记录详情信息")
public class CourseSelectionDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    @Schema(description = "学生ID")
    @ExcelProperty(value = "学生ID")
    private String studentId;

    /**
     * 课程安排ID
     */
    @Schema(description = "课程安排ID")
    @ExcelProperty(value = "课程安排ID")
    private String scheduleId;

    /**
     * 选课时间
     */
    @Schema(description = "选课时间")
    @ExcelProperty(value = "选课时间")
    private LocalDateTime selectTime;

    /**
     * 成绩
     */
    @Schema(description = "成绩")
    @ExcelProperty(value = "成绩")
    private BigDecimal score;

    /**
     * 状态（1：已选；2：退选；3：已修完）
     */
    @Schema(description = "状态（1：已选；2：退选；3：已修完）")
    @ExcelProperty(value = "状态（1：已选；2：退选；3：已修完）")
    private Integer status;
}