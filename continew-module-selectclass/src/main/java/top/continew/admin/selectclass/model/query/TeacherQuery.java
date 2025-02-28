package top.continew.admin.selectclass.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 教师管理查询条件
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@Schema(description = "教师管理查询条件")
public class TeacherQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 教师编号
     */
    @Schema(description = "教师编号")
    @Query(type = QueryType.EQ)
    private String teacherNo;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @Query(type = QueryType.EQ)
    private String name;

    /**
     * 性别（1：男；2：女）
     */
    @Schema(description = "性别（1：男；2：女）")
    @Query(type = QueryType.EQ)
    private Integer gender;

    /**
     * 所属学院
     */
    @Schema(description = "所属学院")
    @Query(type = QueryType.EQ)
    private Long collegeId;

    /**
     * 状态（1：在职；2：离职）
     */
    @Schema(description = "状态（1：在职；2：离职）")
    @Query(type = QueryType.EQ)
    private Integer status;
}