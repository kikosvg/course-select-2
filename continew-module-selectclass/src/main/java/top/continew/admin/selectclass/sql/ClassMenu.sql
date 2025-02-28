SET @parentId = 1886282007982403584;
-- 班级管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '班级管理', 1000, 2, '/selectclass/class', 'Class', 'selectclass/class/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 班级管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1886282007982403585, '列表', @parentId, 3, 'selectclass:class:list', 1, 1, 1, NOW()),
    (1886282007982403586, '详情', @parentId, 3, 'selectclass:class:detail', 2, 1, 1, NOW()),
    (1886282007982403587, '新增', @parentId, 3, 'selectclass:class:add', 3, 1, 1, NOW()),
    (1886282007982403588, '修改', @parentId, 3, 'selectclass:class:update', 4, 1, 1, NOW()),
    (1886282007982403589, '删除', @parentId, 3, 'selectclass:class:delete', 5, 1, 1, NOW()),
    (1886282007982403590, '导出', @parentId, 3, 'selectclass:class:export', 6, 1, 1, NOW());

