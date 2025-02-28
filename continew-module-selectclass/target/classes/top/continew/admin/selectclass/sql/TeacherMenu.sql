SET @parentId = 1886282008921927680;
-- 教师管理管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '教师管理管理', 1000, 2, '/selectclass/teacher', 'Teacher', 'selectclass/teacher/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 教师管理管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1886282008921927681, '列表', @parentId, 3, 'selectclass:teacher:list', 1, 1, 1, NOW()),
    (1886282008921927682, '详情', @parentId, 3, 'selectclass:teacher:detail', 2, 1, 1, NOW()),
    (1886282008921927683, '新增', @parentId, 3, 'selectclass:teacher:add', 3, 1, 1, NOW()),
    (1886282008921927684, '修改', @parentId, 3, 'selectclass:teacher:update', 4, 1, 1, NOW()),
    (1886282008921927685, '删除', @parentId, 3, 'selectclass:teacher:delete', 5, 1, 1, NOW()),
    (1886282008921927686, '导出', @parentId, 3, 'selectclass:teacher:export', 6, 1, 1, NOW());

