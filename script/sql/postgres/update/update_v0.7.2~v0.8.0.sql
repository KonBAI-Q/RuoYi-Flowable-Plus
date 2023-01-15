ALTER TABLE "sys_oss_config" ADD COLUMN "access_policy" char(1) NOT NULL DEFAULT '1'::bpchar;

COMMENT ON COLUMN "sys_oss_config"."access_policy" IS '桶权限类型(0=private 1=public 2=custom)';

ALTER TABLE "wf_deploy_form" ADD COLUMN "form_name" varchar(64) DEFAULT ''::VARCHAR;

COMMENT ON COLUMN "wf_deploy_form"."form_name" IS '表单名称';
