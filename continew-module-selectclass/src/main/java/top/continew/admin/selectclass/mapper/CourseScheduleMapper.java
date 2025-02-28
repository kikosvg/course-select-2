package top.continew.admin.selectclass.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import top.continew.starter.data.mp.base.BaseMapper;
import top.continew.admin.selectclass.model.entity.CourseScheduleDO;

/**
* 课程安排 Mapper
*
* @author weiwei
* @since 2025/02/03 13:14
*/
@Mapper
public interface CourseScheduleMapper extends BaseMapper<CourseScheduleDO> {
    @Update("update course_schedule set selected = selected + 1 where course_id = #{courseScheduleId}")
    void updateCourseSelectedNum(String courseScheduleId);
}