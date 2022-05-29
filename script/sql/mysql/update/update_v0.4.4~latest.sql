drop table if exists `wf_copy`;
create table `wf_copy` (
   `copy_id`         bigint        not null  auto_increment   comment '抄送主键',
   `title`           varchar(255)            default ''       comment '抄送标题',
   `process_id`      varchar(64)             default ''       comment '流程主键',
   `process_name`    varchar(255)            default ''       comment '流程名称',
   `category_id`     varchar(255)            default ''       comment '流程分类主键',
   `deployment_id`   varchar(64)             default ''       comment '部署主键',
   `instance_id`     varchar(64)             default ''       comment '流程实例主键',
   `task_id`         varchar(64)             default ''       comment '任务主键',
   `user_id`         bigint                  default null     comment '用户主键',
   `originator_id`   bigint                  default null     comment '发起人主键',
   `originator_name` varchar(64)             default ''       comment '发起人名称',
   `create_by`       varchar(64)             default ''       comment '创建者',
   `create_time`     datetime                default null     comment '创建时间',
   `update_by`       varchar(64)             default ''       comment '更新者',
   `update_time`     datetime                default null     comment '更新时间',
   `del_flag`        char(1)                 default '0'      comment '删除标志（0代表存在 2代表删除）',
   primary key (`copy_id`)
) engine=innodb comment='流程抄送表';

insert into sys_menu values('128',  '抄送我的', '5',   '5', 'copy',       'workflow/work/copy',        '', 1, 0, 'C', '0', '0', 'workflow:process:copyList',     'checkbox',   'admin', sysdate(), '', null, '');

insert into sys_role_menu values ('2', '128');
