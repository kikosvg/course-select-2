package top.continew.admin.selectclass.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 班级查询条件
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "班级查询条件")
public class ClassQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 班级名称
     */
    @Schema(description = "班级名称")
    @Query(type = QueryType.EQ)
    private String name;

    /**
     * 班级代码
     */
    @Schema(description = "班级代码")
    @Query(type = QueryType.EQ)
    private String code;

    /**
     * 所属专业ID
     */
    @Schema(description = "所属专业ID")
    @Query(type = QueryType.EQ)
    private Long majorId;

    /**
     * 年级
     */
    @Schema(description = "年级")
    @Query(type = QueryType.EQ)
    private Integer grade;

    /**
     * 状态（1：正常；2：毕业）
     */
    @Schema(description = "状态（1：正常；2：毕业）")
    @Query(type = QueryType.EQ)
    private Integer status;
}