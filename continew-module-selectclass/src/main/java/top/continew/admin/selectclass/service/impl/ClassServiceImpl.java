package top.continew.admin.selectclass.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.selectclass.mapper.ClassMapper;
import top.continew.admin.selectclass.model.entity.ClassDO;
import top.continew.admin.selectclass.model.query.ClassQuery;
import top.continew.admin.selectclass.model.req.ClassReq;
import top.continew.admin.selectclass.model.resp.ClassDetailResp;
import top.continew.admin.selectclass.model.resp.ClassResp;
import top.continew.admin.selectclass.service.ClassService;

/**
 * 班级业务实现
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Service
@RequiredArgsConstructor
public class ClassServiceImpl extends BaseServiceImpl<ClassMapper, ClassDO, ClassResp, ClassDetailResp, ClassQuery, ClassReq> implements ClassService {}