drop table if exists wf_copy;
create table wf_copy (
   copy_id          int8 not null,
   title            varchar(255)  default ''::varchar,
   process_id       varchar(64)   default ''::varchar,
   process_name     varchar(255)  default ''::varchar,
   category_id      varchar(255)  default ''::varchar,
   deployment_id    varchar(64)   default ''::varchar,
   instance_id      varchar(64)   default ''::varchar,
   task_id          varchar(64)   default ''::varchar,
   user_id          int8,
   originator_id    int8,
   originator_name  varchar(64)   default ''::varchar,
   create_by        varchar(64)   default ''::varchar,
   create_time      timestamp,
   update_by        varchar(64)   default ''::varchar,
   update_time      timestamp,
   del_flag         char(1),
   constraint "wf_copy_pk" primary key (copy_id)
);

comment on table wf_copy is '流程抄送表';
comment on column wf_copy.copy_id is '抄送主键';
comment on column wf_copy.title is '抄送标题';
comment on column wf_copy.process_id is '流程主键';
comment on column wf_copy.process_name is '流程名称';
comment on column wf_copy.category_id is '流程分类主键';
comment on column wf_copy.deployment_id is '部署主键';
comment on column wf_copy.instance_id is '流程实例主键';
comment on column wf_copy.task_id is '任务主键';
comment on column wf_copy.user_id is '用户主键';
comment on column wf_copy.originator_id is '发起人主键';
comment on column wf_copy.originator_name is '发起人名称';
comment on column wf_copy.create_by is '创建者';
comment on column wf_copy.create_time is '创建时间';
comment on column wf_copy.update_by is '更新者';
comment on column wf_copy.update_time is '更新时间';
comment on column wf_copy.del_flag is '删除标志（0代表存在 2代表删除）';

insert into sys_menu values('128',  '抄送我的', '5',   '5', 'copy',       'workflow/work/copy',        '', 1, 0, 'C', '0', '0', 'workflow:process:copyList',     'checkbox',   'admin', now(), '', null, '');

insert into sys_role_menu values ('2', '128');
