package top.continew.admin.selectclass.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;
import java.time.*;

/**
 * 课程安排实体
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@TableName("course_schedule")
public class CourseScheduleDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 教师ID
     */
    private String teacherId;

    /**
     * 学期
     */
    private String semester;

    /**
     * 教室
     */
    private String classroom;

    /**
     * 星期几（1-7）
     */
    private Integer dayOfWeek;

    /**
     * 上课时间
     */
    private LocalDateTime startTime;

    /**
     * 下课时间
     */
    private LocalDateTime endTime;

    /**
     * 最大学生数
     */
    private Integer maxStudent;

    /**
     * 已选人数
     */
    private Integer selected;

    /**
     * 状态（1：正常；2：已满；3：停课）
     */
    private Integer status;
}