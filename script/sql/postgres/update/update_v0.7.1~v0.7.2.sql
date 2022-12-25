insert into sys_menu values('1181', '新建流程导出', '125', '2', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:process:startExport', '#', 'admin', now(), '', null, '');

insert into sys_menu values('1193', '我的流程导出', '126', '4', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:process:ownExport', '#', 'admin', now(), '', null, '');

insert into sys_menu values('1201', '待办流程导出', '127', '2', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:process:todoExport', '#', 'admin', now(), '', null, '');

insert into sys_menu values('1211', '待签流程导出', '128', '2', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:process:claimExport',  '#', 'admin', now(), '', null, '');

insert into sys_menu values('1221', '已办流程导出', '129', '2', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:process:finishedExport', '#', 'admin', now(), '', null, '');

update sys_menu set menu_name = '抄送流程导出', perms = 'workflow:process:copyExport' where menu_id = 1230;

insert into sys_role_menu values ('2', '1181');

insert into sys_role_menu values ('2', '1193');

insert into sys_role_menu values ('2', '1201');

insert into sys_role_menu values ('2', '1211');

insert into sys_role_menu values ('2', '1221');
