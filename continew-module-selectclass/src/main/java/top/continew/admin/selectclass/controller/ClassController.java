package top.continew.admin.selectclass.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.selectclass.model.query.ClassQuery;
import top.continew.admin.selectclass.model.req.ClassReq;
import top.continew.admin.selectclass.model.resp.ClassDetailResp;
import top.continew.admin.selectclass.model.resp.ClassResp;
import top.continew.admin.selectclass.service.ClassService;

/**
 * 班级管理 API
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Tag(name = "班级管理 API")
@RestController
@CrudRequestMapping(value = "/selectclass/class", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class ClassController extends BaseController<ClassService, ClassResp, ClassDetailResp, ClassQuery, ClassReq> {}