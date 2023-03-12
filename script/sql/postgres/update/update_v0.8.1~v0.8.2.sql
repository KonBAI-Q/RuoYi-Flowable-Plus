insert into sys_dict_type values(11, '流程状态', 'wf_process_status',   '0', 'admin', now(), '', null, '工作流程状态');

insert into sys_dict_data values(30, 1,  '进行中',   'running',    'wf_process_status',   '',   'primary',  'N', '0', 'admin', now(), '', null, '进行中状态');
insert into sys_dict_data values(31, 2,  '已终止',   'terminated', 'wf_process_status',   '',   'danger',   'N', '0', 'admin', now(), '', null, '已终止状态');
insert into sys_dict_data values(32, 3,  '已完成',   'completed',  'wf_process_status',   '',   'success',  'N', '0', 'admin', now(), '', null, '已完成状态');
insert into sys_dict_data values(33, 4,  '已取消',   'canceled',   'wf_process_status',   '',   'warning',  'N', '0', 'admin', now(), '', null, '已取消状态');
