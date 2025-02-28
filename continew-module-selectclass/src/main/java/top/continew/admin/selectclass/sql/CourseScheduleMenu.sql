SET @parentId = 1886282008540246016;
-- 课程安排管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '课程安排管理', 1000, 2, '/selectclass/courseSchedule', 'CourseSchedule', 'selectclass/courseSchedule/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 课程安排管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1886282008540246017, '列表', @parentId, 3, 'selectclass:courseSchedule:list', 1, 1, 1, NOW()),
    (1886282008544440320, '详情', @parentId, 3, 'selectclass:courseSchedule:detail', 2, 1, 1, NOW()),
    (1886282008544440321, '新增', @parentId, 3, 'selectclass:courseSchedule:add', 3, 1, 1, NOW()),
    (1886282008544440322, '修改', @parentId, 3, 'selectclass:courseSchedule:update', 4, 1, 1, NOW()),
    (1886282008544440323, '删除', @parentId, 3, 'selectclass:courseSchedule:delete', 5, 1, 1, NOW()),
    (1886282008544440324, '导出', @parentId, 3, 'selectclass:courseSchedule:export', 6, 1, 1, NOW());

