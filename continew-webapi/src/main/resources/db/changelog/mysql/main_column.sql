-- liquibase formatted sql

-- changeset student:1
-- comment 初始化学生选课系统表结构

-- 学生表
CREATE TABLE IF NOT EXISTS `student` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT '学生ID',
    `student_no`  varchar(20)  NOT NULL                    COMMENT '学号',
    `name`        varchar(50)  NOT NULL                    COMMENT '姓名',
    `gender`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '性别（1：男；2：女）',
    `age`         int          DEFAULT NULL                COMMENT '年龄',
    `phone`       varchar(20)  DEFAULT NULL                COMMENT '联系电话',
    `email`       varchar(50)  DEFAULT NULL                COMMENT '邮箱',
    `class_id`    bigint(20)   NOT NULL                    COMMENT '班级ID',
    `major_id`    bigint(20)   NOT NULL                    COMMENT '专业ID',
    `college_id`  bigint(20)   NOT NULL                    COMMENT '学院ID',
    `status`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '状态（1：在读；2：休学；3：退学）',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_student_no`(`student_no`),
    INDEX `idx_class_id`(`class_id`),
    INDEX `idx_major_id`(`major_id`),
    INDEX `idx_college_id`(`college_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 教师表
CREATE TABLE IF NOT EXISTS `teacher` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT '教师ID',
    `teacher_no`  varchar(20)  NOT NULL                    COMMENT '教师编号',
    `name`        varchar(50)  NOT NULL                    COMMENT '姓名',
    `gender`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '性别（1：男；2：女）',
    `title`       varchar(20)  DEFAULT NULL                COMMENT '职称',
    `phone`       varchar(20)  DEFAULT NULL                COMMENT '联系电话',
    `email`       varchar(50)  DEFAULT NULL                COMMENT '邮箱',
    `college_id`  bigint(20)   NOT NULL                    COMMENT '所属学院ID',
    `status`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '状态（1：在职；2：离职）',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_teacher_no`(`teacher_no`),
    INDEX `idx_college_id`(`college_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- 课程表
CREATE TABLE IF NOT EXISTS `course` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT '课程ID',
    `course_no`   varchar(20)  NOT NULL                    COMMENT '课程编号',
    `name`        varchar(100) NOT NULL                    COMMENT '课程名称',
    `credit`      decimal(3,1) NOT NULL                    COMMENT '学分',
    `hours`       int          NOT NULL                    COMMENT '课时',
    `type`        tinyint(1)   NOT NULL DEFAULT 1          COMMENT '类型（1：必修；2：选修）',
    `college_id`  bigint(20)   NOT NULL                    COMMENT '开课学院ID',
    `description` varchar(500) DEFAULT NULL                COMMENT '课程描述',
    `status`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '状态（1：正常；2：停课）',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_course_no`(`course_no`),
    INDEX `idx_college_id`(`college_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 课程安排表
CREATE TABLE IF NOT EXISTS `course_schedule` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT '课程安排ID',
    `course_id`   bigint(20)   NOT NULL                    COMMENT '课程ID',
    `teacher_id`  bigint(20)   NOT NULL                    COMMENT '教师ID',
    `semester`    varchar(20)  NOT NULL                    COMMENT '学期',
    `classroom`   varchar(50)  NOT NULL                    COMMENT '教室',
    `day_of_week` tinyint(1)   NOT NULL                    COMMENT '星期几（1-7）',
    `start_time`  time         NOT NULL                    COMMENT '上课时间',
    `end_time`    time         NOT NULL                    COMMENT '下课时间',
    `max_student` int          NOT NULL DEFAULT 50         COMMENT '最大学生数',
    `selected`    int          NOT NULL DEFAULT 0          COMMENT '已选人数',
    `status`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '状态（1：正常；2：已满；3：停课）',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `idx_course_id`(`course_id`),
    INDEX `idx_teacher_id`(`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程安排表';

-- 选课记录表
CREATE TABLE IF NOT EXISTS `course_selection` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT '选课记录ID',
    `student_id`  bigint(20)   NOT NULL                    COMMENT '学生ID',
    `schedule_id` bigint(20)   NOT NULL                    COMMENT '课程安排ID',
    `select_time` datetime     NOT NULL                    COMMENT '选课时间',
    `score`       decimal(4,1) DEFAULT NULL                COMMENT '成绩',
    `status`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '状态（1：已选；2：退选；3：已修完）',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_student_schedule`(`student_id`, `schedule_id`),
    INDEX `idx_schedule_id`(`schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选课记录表';

-- 学院表
CREATE TABLE IF NOT EXISTS `college` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT '学院ID',
    `name`        varchar(50)  NOT NULL                    COMMENT '学院名称',
    `code`        varchar(20)  NOT NULL                    COMMENT '学院代码',
    `description` varchar(200) DEFAULT NULL                COMMENT '描述',
    `status`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '状态（1：正常；2：停用）',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_code`(`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院表';

-- 专业表
CREATE TABLE IF NOT EXISTS `major` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT '专业ID',
    `name`        varchar(50)  NOT NULL                    COMMENT '专业名称',
    `code`        varchar(20)  NOT NULL                    COMMENT '专业代码',
    `college_id`  bigint(20)   NOT NULL                    COMMENT '所属学院ID',
    `description` varchar(200) DEFAULT NULL                COMMENT '描述',
    `status`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '状态（1：正常；2：停用）',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_code`(`code`),
    INDEX `idx_college_id`(`college_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='专业表';

-- 班级表
CREATE TABLE IF NOT EXISTS `class` (
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT     COMMENT '班级ID',
    `name`        varchar(50)  NOT NULL                    COMMENT '班级名称',
    `code`        varchar(20)  NOT NULL                    COMMENT '班级代码',
    `major_id`    bigint(20)   NOT NULL                    COMMENT '所属专业ID',
    `grade`       int          NOT NULL                    COMMENT '年级',
    `description` varchar(200) DEFAULT NULL                COMMENT '描述',
    `status`      tinyint(1)   NOT NULL DEFAULT 1          COMMENT '状态（1：正常；2：毕业）',
    `create_time` datetime     NOT NULL                    COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL                COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_code`(`code`),
    INDEX `idx_major_id`(`major_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

