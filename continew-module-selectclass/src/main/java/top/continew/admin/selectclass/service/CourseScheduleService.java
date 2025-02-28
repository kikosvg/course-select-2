package top.continew.admin.selectclass.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.selectclass.model.query.CourseScheduleQuery;
import top.continew.admin.selectclass.model.req.CourseScheduleReq;
import top.continew.admin.selectclass.model.resp.CourseScheduleDetailResp;
import top.continew.admin.selectclass.model.resp.CourseScheduleResp;

/**
 * 课程安排业务接口
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
public interface CourseScheduleService extends BaseService<CourseScheduleResp, CourseScheduleDetailResp, CourseScheduleQuery, CourseScheduleReq> {}