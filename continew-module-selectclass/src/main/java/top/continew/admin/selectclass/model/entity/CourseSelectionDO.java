package top.continew.admin.selectclass.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;
import java.time.*;
import java.math.BigDecimal;

/**
 * 选课记录实体
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@TableName("course_selection")
public class CourseSelectionDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 课程安排ID
     */
    private String scheduleId;

    /**
     * 选课时间
     */
    private LocalDateTime selectTime;

    /**
     * 成绩
     */
    private BigDecimal score;

    /**
     * 状态（1：已选；2：退选；3：已修完）
     */
    private Integer status;
}