package top.continew.admin.selectclass.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.selectclass.model.query.CourseQuery;
import top.continew.admin.selectclass.model.req.CourseReq;
import top.continew.admin.selectclass.model.resp.CourseDetailResp;
import top.continew.admin.selectclass.model.resp.CourseResp;
import top.continew.admin.selectclass.service.CourseService;

/**
 * 课程管理 API
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Tag(name = "课程管理 API")
@RestController
@CrudRequestMapping(value = "/selectclass/course", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class CourseController extends BaseController<CourseService, CourseResp, CourseDetailResp, CourseQuery, CourseReq> {}