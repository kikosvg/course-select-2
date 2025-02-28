package top.continew.admin.selectclass.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 创建或修改教师管理参数
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "创建或修改教师管理参数")
public class TeacherReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 教师编号
     */
    @Schema(description = "教师编号")
    @NotBlank(message = "教师编号不能为空")
    @Length(max = 20, message = "教师编号长度不能超过 {max} 个字符")
    private String teacherNo;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")
    @Length(max = 50, message = "姓名长度不能超过 {max} 个字符")
    private String name;

    /**
     * 性别（1：男；2：女）
     */
    @Schema(description = "性别（1：男；2：女）")
    @NotNull(message = "性别（1：男；2：女）不能为空")
    private Integer gender;

    /**
     * 职称
     */
    @Schema(description = "职称")
    @Length(max = 20, message = "职称长度不能超过 {max} 个字符")
    private String title;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过 {max} 个字符")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Length(max = 50, message = "邮箱长度不能超过 {max} 个字符")
    private String email;

    /**
     * 所属学院
     */
    @Schema(description = "所属学院")
    @NotNull(message = "所属学院不能为空")
    private Long collegeId;

    /**
     * 状态（1：在职；2：离职）
     */
    @Schema(description = "状态（1：在职；2：离职）")
    @NotNull(message = "状态（1：在职；2：离职）不能为空")
    private Integer status;
}