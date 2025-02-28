package top.continew.admin.selectclass.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.selectclass.mapper.CourseScheduleMapper;
import top.continew.admin.selectclass.model.entity.CourseScheduleDO;
import top.continew.admin.selectclass.model.query.CourseScheduleQuery;
import top.continew.admin.selectclass.model.req.CourseScheduleReq;
import top.continew.admin.selectclass.model.resp.CourseScheduleDetailResp;
import top.continew.admin.selectclass.model.resp.CourseScheduleResp;
import top.continew.admin.selectclass.service.CourseScheduleService;

/**
 * 课程安排业务实现
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Service
@RequiredArgsConstructor
public class CourseScheduleServiceImpl extends BaseServiceImpl<CourseScheduleMapper, CourseScheduleDO, CourseScheduleResp, CourseScheduleDetailResp, CourseScheduleQuery, CourseScheduleReq> implements CourseScheduleService {}