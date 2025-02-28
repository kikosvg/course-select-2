package top.continew.admin.selectclass.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.*;

/**
 * 教师管理详情信息
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "教师管理详情信息")
public class TeacherDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 教师编号
     */
    @Schema(description = "教师编号")
    @ExcelProperty(value = "教师编号")
    private String teacherNo;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 性别（1：男；2：女）
     */
    @Schema(description = "性别（1：男；2：女）")
    @ExcelProperty(value = "性别（1：男；2：女）")
    private Integer gender;

    /**
     * 职称
     */
    @Schema(description = "职称")
    @ExcelProperty(value = "职称")
    private String title;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    @ExcelProperty(value = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 所属学院
     */
    @Schema(description = "所属学院")
    @ExcelProperty(value = "所属学院")
    private Long collegeId;

    /**
     * 状态（1：在职；2：离职）
     */
    @Schema(description = "状态（1：在职；2：离职）")
    @ExcelProperty(value = "状态（1：在职；2：离职）")
    private Integer status;
}