package top.continew.admin.selectclass.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.selectclass.model.query.TeacherQuery;
import top.continew.admin.selectclass.model.req.TeacherReq;
import top.continew.admin.selectclass.model.resp.TeacherDetailResp;
import top.continew.admin.selectclass.model.resp.TeacherResp;
import top.continew.admin.selectclass.service.TeacherService;

/**
 * 教师管理管理 API
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Tag(name = "教师管理管理 API")
@RestController
@CrudRequestMapping(value = "/selectclass/teacher", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class TeacherController extends BaseController<TeacherService, TeacherResp, TeacherDetailResp, TeacherQuery, TeacherReq> {}