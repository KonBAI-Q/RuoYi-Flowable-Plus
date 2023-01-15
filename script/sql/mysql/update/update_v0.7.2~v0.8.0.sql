ALTER TABLE sys_oss_config ADD COLUMN access_policy char(1) NOT NULL DEFAULT 1 COMMENT '桶权限类型(0=private 1=public 2=custom)' AFTER region;

ALTER TABLE wf_deploy_form ADD COLUMN form_name varchar(64) NULL COMMENT '表单名称' AFTER node_key;
