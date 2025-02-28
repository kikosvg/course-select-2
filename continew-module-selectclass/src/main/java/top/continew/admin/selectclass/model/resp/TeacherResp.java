package top.continew.admin.selectclass.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;

/**
 * 教师管理信息
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "教师管理信息")
public class TeacherResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 教师编号
     */
    @Schema(description = "教师编号")
    private String teacherNo;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;

    /**
     * 性别（1：男；2：女）
     */
    @Schema(description = "性别（1：男；2：女）")
    private Integer gender;

    /**
     * 职称
     */
    @Schema(description = "职称")
    private String title;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 所属学院
     */
    @Schema(description = "所属学院")
    private Long collegeId;

    /**
     * 状态（1：在职；2：离职）
     */
    @Schema(description = "状态（1：在职；2：离职）")
    private Integer status;
}