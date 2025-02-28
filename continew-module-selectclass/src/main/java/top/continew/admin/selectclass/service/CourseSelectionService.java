package top.continew.admin.selectclass.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.selectclass.model.query.CourseSelectionQuery;
import top.continew.admin.selectclass.model.req.CourseSelectionReq;
import top.continew.admin.selectclass.model.resp.CourseSelectionDetailResp;
import top.continew.admin.selectclass.model.resp.CourseSelectionResp;

/**
 * 选课记录业务接口
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
public interface CourseSelectionService extends BaseService<CourseSelectionResp, CourseSelectionDetailResp, CourseSelectionQuery, CourseSelectionReq> {

    void updateCourseSelectedNum(String courseName);
}