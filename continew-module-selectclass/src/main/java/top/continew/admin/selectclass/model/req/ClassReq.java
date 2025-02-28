package top.continew.admin.selectclass.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 创建或修改班级参数
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "创建或修改班级参数")
public class ClassReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 班级名称
     */
    @Schema(description = "班级名称")
    @NotBlank(message = "班级名称不能为空")
    @Length(max = 50, message = "班级名称长度不能超过 {max} 个字符")
    private String name;

    /**
     * 班级代码
     */
    @Schema(description = "班级代码")
    @NotBlank(message = "班级代码不能为空")
    @Length(max = 20, message = "班级代码长度不能超过 {max} 个字符")
    private String code;

    /**
     * 所属专业ID
     */
    @Schema(description = "所属专业ID")
    @NotNull(message = "所属专业ID不能为空")
    private Long majorId;

    /**
     * 年级
     */
    @Schema(description = "年级")
    @NotNull(message = "年级不能为空")
    private Integer grade;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Length(max = 200, message = "描述长度不能超过 {max} 个字符")
    private String description;

    /**
     * 状态（1：正常；2：毕业）
     */
    @Schema(description = "状态（1：正常；2：毕业）")
    @NotNull(message = "状态（1：正常；2：毕业）不能为空")
    private Integer status;
}