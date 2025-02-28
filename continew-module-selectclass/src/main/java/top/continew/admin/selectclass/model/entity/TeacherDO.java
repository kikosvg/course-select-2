package top.continew.admin.selectclass.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;

/**
 * 教师管理实体
 *
 * @author weiwei
 * @since 2025/02/03 13:14
 */
@Data
@TableName("teacher")
public class TeacherDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 教师编号
     */
    private String teacherNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（1：男；2：女）
     */
    private Integer gender;

    /**
     * 职称
     */
    private String title;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所属学院
     */
    private Long collegeId;

    /**
     * 状态（1：在职；2：离职）
     */
    private Integer status;
}