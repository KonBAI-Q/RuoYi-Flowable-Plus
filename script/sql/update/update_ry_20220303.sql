RENAME TABLE workflow_category TO wf_category;
RENAME TABLE sys_form TO wf_form;
RENAME TABLE sys_deploy_form TO wf_deploy_form;

ALTER TABLE `wf_deploy_form`
DROP COLUMN `id`,
MODIFY COLUMN `deploy_id` varchar(64) NOT NULL DEFAULT '' COMMENT '流程实例主键' FIRST,
MODIFY COLUMN `form_id` bigint(20) NOT NULL COMMENT '表单主键' AFTER `deploy_id`,
ADD PRIMARY KEY (`deploy_id`, `form_id`) USING BTREE;

ALTER TABLE `wf_form`
CHANGE COLUMN `form_content` `content` longtext NULL COMMENT '表单内容' AFTER `form_name`;
