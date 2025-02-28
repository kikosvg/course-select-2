package top.continew.admin.selectclass.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.selectclass.model.query.CourseScheduleQuery;
import top.continew.admin.selectclass.model.req.CourseScheduleReq;
import top.continew.admin.selectclass.model.resp.CourseScheduleDetailResp;
import top.continew.admin.selectclass.model.resp.CourseScheduleResp;
import top.continew.admin.selectclass.service.CourseScheduleService;

/**
 * 课程安排管理 API
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Tag(name = "课程安排管理 API")
@RestController
@CrudRequestMapping(value = "/selectclass/courseSchedule", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class CourseScheduleController extends BaseController<CourseScheduleService, CourseScheduleResp, CourseScheduleDetailResp, CourseScheduleQuery, CourseScheduleReq> {

}