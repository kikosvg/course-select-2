package top.continew.admin.selectclass.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.*;

/**
 * 班级详情信息
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "班级详情信息")
public class ClassDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 班级名称
     */
    @Schema(description = "班级名称")
    @ExcelProperty(value = "班级名称")
    private String name;

    /**
     * 班级代码
     */
    @Schema(description = "班级代码")
    @ExcelProperty(value = "班级代码")
    private String code;

    /**
     * 所属专业ID
     */
    @Schema(description = "所属专业ID")
    @ExcelProperty(value = "所属专业ID")
    private Long majorId;

    /**
     * 年级
     */
    @Schema(description = "年级")
    @ExcelProperty(value = "年级")
    private Integer grade;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 状态（1：正常；2：毕业）
     */
    @Schema(description = "状态（1：正常；2：毕业）")
    @ExcelProperty(value = "状态（1：正常；2：毕业）")
    private Integer status;
}