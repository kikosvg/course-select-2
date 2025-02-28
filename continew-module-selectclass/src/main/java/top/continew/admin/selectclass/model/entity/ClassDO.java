package top.continew.admin.selectclass.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;

/**
 * 班级实体
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@TableName("class")
public class ClassDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 班级代码
     */
    private String code;

    /**
     * 所属专业ID
     */
    private Long majorId;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态（1：正常；2：毕业）
     */
    private Integer status;
}