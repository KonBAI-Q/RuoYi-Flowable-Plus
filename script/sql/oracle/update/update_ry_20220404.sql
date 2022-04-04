UPDATE sys_menu SET perms = 'workflow:process:startList' WHERE menu_id = '124';
UPDATE sys_menu SET perms = 'workflow:process:ownList' WHERE menu_id = '125';
UPDATE sys_menu SET perms = 'workflow:process:todoList' WHERE menu_id = '126';
UPDATE sys_menu SET perms = 'workflow:process:finishedList' WHERE menu_id = '127';

insert into sys_menu values('1170', '发起流程', '124', '1', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:process:start',       '#', 'admin', sysdate, '', null, '');

insert into sys_role_menu values ('2', '1170');
