package top.continew.admin.selectclass.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.selectclass.model.query.CourseQuery;
import top.continew.admin.selectclass.model.req.CourseReq;
import top.continew.admin.selectclass.model.resp.CourseDetailResp;
import top.continew.admin.selectclass.model.resp.CourseResp;

/**
 * 课程业务接口
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
public interface CourseService extends BaseService<CourseResp, CourseDetailResp, CourseQuery, CourseReq> {}