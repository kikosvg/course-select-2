-- liquibase formatted sql

-- changeset student:1
-- comment 创建课程安排状态更新触发器

DELIMITER //

-- 创建更新课程状态的触发器（插入时）
CREATE TRIGGER trg_course_schedule_status_insert 
AFTER INSERT ON course_schedule
FOR EACH ROW
BEGIN
    IF NEW.selected >= NEW.max_student THEN
        UPDATE course_schedule 
        SET status = 2 
        WHERE id = NEW.id AND status = 1;
    END IF;
END //

-- 创建更新课程状态的触发器（更新时）
CREATE TRIGGER trg_course_schedule_status_update
AFTER UPDATE ON course_schedule
FOR EACH ROW
BEGIN
    IF NEW.selected >= NEW.max_student AND NEW.status = 1 THEN
        UPDATE course_schedule 
        SET status = 2 
        WHERE id = NEW.id;
    ELSEIF NEW.selected < NEW.max_student AND NEW.status = 2 THEN
        UPDATE course_schedule 
        SET status = 1 
        WHERE id = NEW.id;
    END IF;
END //

DELIMITER ; 