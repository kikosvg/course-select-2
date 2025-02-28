package top.continew.admin.selectclass.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;

import java.io.Serial;
import java.time.*;

/**
 * 班级信息
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "班级信息")
public class ClassResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 班级名称
     */
    @Schema(description = "班级名称")
    private String name;

    /**
     * 班级代码
     */
    @Schema(description = "班级代码")
    private String code;

    /**
     * 所属专业ID
     */
    @Schema(description = "所属专业ID")
    private Long majorId;

    /**
     * 年级
     */
    @Schema(description = "年级")
    private Integer grade;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 状态（1：正常；2：毕业）
     */
    @Schema(description = "状态（1：正常；2：毕业）")
    private Integer status;
}