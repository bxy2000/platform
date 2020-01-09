
insert into menu(id, parent_id, text, jhi_group, jhi_link, external_link, target, icon, hide, description) values
    (10,        null,   '功能导航',           true,        '',                              '',         '',         '',                                false,   ''),
    (10100,     10,     '首页',               false,       '/dashboard',                    '',         '',         'anticon anticon-dashboard',       false,   ''),
    (10101,     10,     '数据目录',           false,       '/data-catalog',                 '',         '',         'anticon anticon-profile',         false,   ''),

    (10909,     10,     '基础信息',           true,        '/basic',                        '',         '',         'anticon anticon-link',            false,   ''),
    (1090901,   10909,  '数据连接',           false,       '/basic/database-connection',    '',         '',         '',                                false,   ''),

    (10910,		10, 	'系统功能',		      true,        '/system',                       '',         '',         'anticon anticon-user', 		   false,	''),
    (1091001,	10910, 	'全局资源', 	      false,       '/system/menu',                  '',         '',         '', 				               false,	''),
    (1091002,	10910, 	'角色权限', 	      false,       '/system/role',                  '',         '',         '', 				               false,	''),
    (1091003,	10910, 	'用户管理', 	      false,	   '/system/user',                  '',         '',         '', 				               false,	'');

insert into role(id, role_name) values
	(3, '系统管理员'),
	(4, '普通用户');

insert into role_menu(roles_id, menus_id) values
    (3, 10100       ),
	(3, 10101       ),

	(3, 1090901     ),

	(3, 1091001	    ),
	(3, 1091002	    ),
	(3, 1091003	    ),

    (3, 10100       ),
	(3, 10101       ),

	(3, 1090901     ),

	(3, 1091001	    ),
	(3, 1091002	    ),
	(3, 1091003	    );

insert into user_extends(id, username, gender, mobile, user_id) values
    (3, 'admin', 'MALE', '138-', 3),
    (4, 'user',  'MALE', '138-', 4);
insert into user_extends_role(user_extends_id, roles_id) values
    (3, 3),
    (4, 4);
