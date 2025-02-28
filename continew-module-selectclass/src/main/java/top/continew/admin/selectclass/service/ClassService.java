package top.continew.admin.selectclass.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.selectclass.model.query.ClassQuery;
import top.continew.admin.selectclass.model.req.ClassReq;
import top.continew.admin.selectclass.model.resp.ClassDetailResp;
import top.continew.admin.selectclass.model.resp.ClassResp;

/**
 * 班级业务接口
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
public interface ClassService extends BaseService<ClassResp, ClassDetailResp, ClassQuery, ClassReq> {}