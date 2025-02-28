package top.continew.admin.selectclass.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.continew.admin.selectclass.mapper.CourseScheduleMapper;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.selectclass.mapper.CourseSelectionMapper;
import top.continew.admin.selectclass.model.entity.CourseSelectionDO;
import top.continew.admin.selectclass.model.query.CourseSelectionQuery;
import top.continew.admin.selectclass.model.req.CourseSelectionReq;
import top.continew.admin.selectclass.model.resp.CourseSelectionDetailResp;
import top.continew.admin.selectclass.model.resp.CourseSelectionResp;
import top.continew.admin.selectclass.service.CourseSelectionService;

/**
 * 选课记录业务实现
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Service
@RequiredArgsConstructor
public class CourseSelectionServiceImpl extends BaseServiceImpl<CourseSelectionMapper, CourseSelectionDO, CourseSelectionResp, CourseSelectionDetailResp, CourseSelectionQuery, CourseSelectionReq> implements CourseSelectionService {
    @Autowired
    private CourseScheduleMapper courseScheduleMapper;
    @Override
    public void updateCourseSelectedNum(String courseName) {
        courseScheduleMapper.updateCourseSelectedNum(courseName);
    }
}