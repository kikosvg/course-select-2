package top.continew.admin.selectclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.selectclass.model.query.CourseSelectionQuery;
import top.continew.admin.selectclass.model.req.CourseSelectionReq;
import top.continew.admin.selectclass.model.resp.CourseSelectionDetailResp;
import top.continew.admin.selectclass.model.resp.CourseSelectionResp;
import top.continew.admin.selectclass.service.CourseSelectionService;
import top.continew.starter.extension.crud.model.resp.BaseIdResp;

/**
 * 选课记录管理 API
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Tag(name = "选课记录管理 API")
@RestController
@CrudRequestMapping(value = "/selectclass/courseSelection", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class CourseSelectionController extends BaseController<CourseSelectionService, CourseSelectionResp, CourseSelectionDetailResp, CourseSelectionQuery, CourseSelectionReq> {
    @Autowired
    private CourseSelectionService courseSelectionService;
    @Override
    public BaseIdResp<Long> add(CourseSelectionReq req) {
        courseSelectionService.updateCourseSelectedNum(req.getScheduleId());
        return super.add(req);
    }
}