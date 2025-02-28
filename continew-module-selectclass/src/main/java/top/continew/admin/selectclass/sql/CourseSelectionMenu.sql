SET @parentId = 1886282008724795392;
-- 选课记录管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '选课记录管理', 1000, 2, '/selectclass/courseSelection', 'CourseSelection', 'selectclass/courseSelection/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 选课记录管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1886282008724795393, '列表', @parentId, 3, 'selectclass:courseSelection:list', 1, 1, 1, NOW()),
    (1886282008724795394, '详情', @parentId, 3, 'selectclass:courseSelection:detail', 2, 1, 1, NOW()),
    (1886282008724795395, '新增', @parentId, 3, 'selectclass:courseSelection:add', 3, 1, 1, NOW()),
    (1886282008724795396, '修改', @parentId, 3, 'selectclass:courseSelection:update', 4, 1, 1, NOW()),
    (1886282008724795397, '删除', @parentId, 3, 'selectclass:courseSelection:delete', 5, 1, 1, NOW()),
    (1886282008724795398, '导出', @parentId, 3, 'selectclass:courseSelection:export', 6, 1, 1, NOW());

