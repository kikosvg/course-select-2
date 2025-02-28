package top.continew.admin.selectclass.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.selectclass.mapper.TeacherMapper;
import top.continew.admin.selectclass.model.entity.TeacherDO;
import top.continew.admin.selectclass.model.query.TeacherQuery;
import top.continew.admin.selectclass.model.req.TeacherReq;
import top.continew.admin.selectclass.model.resp.TeacherDetailResp;
import top.continew.admin.selectclass.model.resp.TeacherResp;
import top.continew.admin.selectclass.service.TeacherService;

/**
 * 教师管理业务实现
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl extends BaseServiceImpl<TeacherMapper, TeacherDO, TeacherResp, TeacherDetailResp, TeacherQuery, TeacherReq> implements TeacherService {}