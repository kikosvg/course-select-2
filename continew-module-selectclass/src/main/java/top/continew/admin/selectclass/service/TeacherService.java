package top.continew.admin.selectclass.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.selectclass.model.query.TeacherQuery;
import top.continew.admin.selectclass.model.req.TeacherReq;
import top.continew.admin.selectclass.model.resp.TeacherDetailResp;
import top.continew.admin.selectclass.model.resp.TeacherResp;

/**
 * 教师管理业务接口
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
public interface TeacherService extends BaseService<TeacherResp, TeacherDetailResp, TeacherQuery, TeacherReq> {}