-- 流程管理
update sys_menu set component = 'workflow/form/index' where menu_id = '123';
-- 任务管理
insert into sys_menu values('127', '新建流程', '6', '0', 'start', 'workflow/work/index', '', 1, 0, 'C', '0', '0', null,  'guide', 'admin', sysdate(), '', null, '');

update sys_menu set component = 'workflow/work/own' where menu_id = '124';
update sys_menu set component = 'workflow/work/todo' where menu_id = '125';
update sys_menu set component = 'workflow/work/finished' where menu_id = '126';

insert into sys_role_menu values ('2', '127');
