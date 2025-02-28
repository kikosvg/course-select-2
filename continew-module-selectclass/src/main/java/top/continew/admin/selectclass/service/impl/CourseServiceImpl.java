package top.continew.admin.selectclass.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.selectclass.mapper.CourseMapper;
import top.continew.admin.selectclass.model.entity.CourseDO;
import top.continew.admin.selectclass.model.query.CourseQuery;
import top.continew.admin.selectclass.model.req.CourseReq;
import top.continew.admin.selectclass.model.resp.CourseDetailResp;
import top.continew.admin.selectclass.model.resp.CourseResp;
import top.continew.admin.selectclass.service.CourseService;

/**
 * 课程业务实现
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends BaseServiceImpl<CourseMapper, CourseDO, CourseResp, CourseDetailResp, CourseQuery, CourseReq> implements CourseService {}