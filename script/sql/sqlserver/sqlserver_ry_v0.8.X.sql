
CREATE TABLE [gen_table]
(
    [table_id]          bigint                         NOT NULL,
    [table_name]        nvarchar(200) DEFAULT ''       NULL,
    [table_comment]     nvarchar(500) DEFAULT ''       NULL,
    [sub_table_name]    nvarchar(64)                   NULL,
    [sub_table_fk_name] nvarchar(64)                   NULL,
    [class_name]        nvarchar(100) DEFAULT ''       NULL,
    [tpl_category]      nvarchar(200) DEFAULT ('crud') NULL,
    [package_name]      nvarchar(100)                  NULL,
    [module_name]       nvarchar(30)                   NULL,
    [business_name]     nvarchar(30)                   NULL,
    [function_name]     nvarchar(50)                   NULL,
    [function_author]   nvarchar(50)                   NULL,
    [gen_type]          nchar(1)      DEFAULT ('0')    NULL,
    [gen_path]          nvarchar(200) DEFAULT ('/')    NULL,
    [options]           nvarchar(1000)                 NULL,
    [create_by]         nvarchar(64)  DEFAULT ''       NULL,
    [create_time]       datetime2(7)                   NULL,
    [update_by]         nvarchar(64)  DEFAULT ''       NULL,
    [update_time]       datetime2(7)                   NULL,
    [remark]            nvarchar(500)                  NULL,
    CONSTRAINT [PK__gen_table] PRIMARY KEY CLUSTERED ([table_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'编号' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'table_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'表名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'table_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'表描述' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'table_comment'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'关联子表的表名' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'sub_table_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'子表关联的外键名' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'sub_table_fk_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'实体类名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'class_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'使用的模板（crud单表操作 tree树表操作）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'tpl_category'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'生成包路径' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'package_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'生成模块名' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'module_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'生成业务名' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'business_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'生成功能名' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'function_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'生成功能作者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'function_author'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'生成代码方式（0zip压缩包 1自定义路径）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'gen_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'生成路径（不填默认项目路径）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'gen_path'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'其它生成选项' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'options'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'代码生成业务表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table'
GO

CREATE TABLE [gen_table_column]
(
    [column_id]      bigint                       NOT NULL,
    [table_id]       bigint                       NULL,
    [column_name]    nvarchar(200)                NULL,
    [column_comment] nvarchar(500)                NULL,
    [column_type]    nvarchar(100)                NULL,
    [java_type]      nvarchar(500)                NULL,
    [java_field]     nvarchar(200)                NULL,
    [is_pk]          nchar(1)                     NULL,
    [is_increment]   nchar(1)                     NULL,
    [is_required]    nchar(1)                     NULL,
    [is_insert]      nchar(1)                     NULL,
    [is_edit]        nchar(1)                     NULL,
    [is_list]        nchar(1)                     NULL,
    [is_query]       nchar(1)                     NULL,
    [query_type]     nvarchar(200) DEFAULT ('EQ') NULL,
    [html_type]      nvarchar(200)                NULL,
    [dict_type]      nvarchar(200) DEFAULT ''     NULL,
    [sort]           int                          NULL,
    [create_by]      nvarchar(64)  DEFAULT ''     NULL,
    [create_time]    datetime2(7)                 NULL,
    [update_by]      nvarchar(64)  DEFAULT ''     NULL,
    [update_time]    datetime2(7)                 NULL,
    CONSTRAINT [PK__gen_table_column] PRIMARY KEY CLUSTERED ([column_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'编号' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'column_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'归属表编号' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'table_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'列名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'column_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'列描述' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'column_comment'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'列类型' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'column_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'JAVA类型' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'java_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'JAVA字段名' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'java_field'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否主键（1是）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'is_pk'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否自增（1是）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'is_increment'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否必填（1是）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'is_required'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否为插入字段（1是）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'is_insert'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否编辑字段（1是）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'is_edit'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否列表字段（1是）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'is_list'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否查询字段（1是）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'is_query'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'查询方式（等于、不等于、大于、小于、范围）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'query_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'html_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典类型' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'dict_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'排序' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'sort'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'代码生成业务表字段' ,
    'SCHEMA', N'dbo',
    'TABLE', N'gen_table_column'
GO

CREATE TABLE [sys_config]
(
    [config_id]    bigint                      NOT NULL,
    [config_name]  nvarchar(100) DEFAULT ''    NULL,
    [config_key]   nvarchar(100) DEFAULT ''    NULL,
    [config_value] nvarchar(500) DEFAULT ''    NULL,
    [config_type]  nchar(1)      DEFAULT ('N') NULL,
    [create_by]    nvarchar(64)  DEFAULT ''    NULL,
    [create_time]  datetime2(7)                NULL,
    [update_by]    nvarchar(64)  DEFAULT ''    NULL,
    [update_time]  datetime2(7)                NULL,
    [remark]       nvarchar(500)               NULL,
    CONSTRAINT [PK__sys_config] PRIMARY KEY CLUSTERED ([config_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'参数主键' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'config_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'参数名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'config_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'参数键名' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'config_key'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'参数键值' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'config_value'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'系统内置（Y是 N否）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'config_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'参数配置表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_config'
GO

INSERT [sys_config] ([config_id], [config_name], [config_key], [config_value], [config_type], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1, N'主框架页-默认皮肤样式名称', N'sys.index.skinName', N'skin-blue', N'Y', N'admin', getdate(), N'', NULL, N'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow')
GO
INSERT [sys_config] ([config_id], [config_name], [config_key], [config_value], [config_type], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (2, N'用户管理-账号初始密码', N'sys.user.initPassword', N'123456', N'Y', N'admin', getdate(), N'', NULL, N'初始化密码 123456')
GO
INSERT [sys_config] ([config_id], [config_name], [config_key], [config_value], [config_type], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (3, N'主框架页-侧边栏主题', N'sys.index.sideTheme', N'theme-dark', N'Y', N'admin', getdate(), N'', NULL, N'深色主题theme-dark，浅色主题theme-light')
GO
INSERT [sys_config] ([config_id], [config_name], [config_key], [config_value], [config_type], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (4, N'账号自助-验证码开关', N'sys.account.captchaEnabled', N'true', N'Y', N'admin', getdate(), N'', NULL, N'是否开启验证码功能（true开启，false关闭）')
GO
INSERT [sys_config] ([config_id], [config_name], [config_key], [config_value], [config_type], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (5, N'账号自助-是否开启用户注册功能', N'sys.account.registerUser', N'false', N'Y', N'admin', getdate(), N'', NULL, N'是否开启注册用户功能（true开启，false关闭）')
GO
INSERT [sys_config] ([config_id], [config_name], [config_key], [config_value], [config_type], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (11, N'OSS预览列表资源开关', N'sys.oss.previewListResource', N'true', N'Y', N'admin', getdate(), N'', null, N'true:开启, false:关闭');
GO

CREATE TABLE [sys_dept]
(
    [dept_id]     bigint                     NOT NULL,
    [parent_id]   bigint       DEFAULT ((0)) NULL,
    [ancestors]   nvarchar(500)DEFAULT ''    NULL,
    [dept_name]   nvarchar(30) DEFAULT ''    NULL,
    [order_num]   int          DEFAULT ((0)) NULL,
    [leader]      nvarchar(20)               NULL,
    [phone]       nvarchar(11)               NULL,
    [email]       nvarchar(50)               NULL,
    [status]      nchar(1)     DEFAULT ('0') NULL,
    [del_flag]    nchar(1)     DEFAULT ('0') NULL,
    [create_by]   nvarchar(64) DEFAULT ''    NULL,
    [create_time] datetime2(7)               NULL,
    [update_by]   nvarchar(64) DEFAULT ''    NULL,
    [update_time] datetime2(7)               NULL,
    CONSTRAINT [PK__sys_dept] PRIMARY KEY CLUSTERED ([dept_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'部门id' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'dept_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'父部门id' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'parent_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'祖级列表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'ancestors'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'部门名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'dept_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'显示顺序' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'order_num'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'负责人' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'leader'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'联系电话' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'phone'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'邮箱' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'email'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'部门状态（0正常 1停用）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'删除标志（0代表存在 2代表删除）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'del_flag'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'部门表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dept'
GO

INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (100, 0, N'0', N'若依科技', 0, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (101, 100, N'0,100', N'深圳总公司', 1, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (102, 100, N'0,100', N'长沙分公司', 2, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (103, 101, N'0,100,101', N'研发部门', 1, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (104, 101, N'0,100,101', N'市场部门', 2, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (105, 101, N'0,100,101', N'测试部门', 3, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (106, 101, N'0,100,101', N'财务部门', 4, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (107, 101, N'0,100,101', N'运维部门', 5, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (108, 102, N'0,100,102', N'市场部门', 1, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO
INSERT [sys_dept] ([dept_id], [parent_id], [ancestors], [dept_name], [order_num], [leader], [phone], [email], [status], [del_flag], [create_by], [create_time], [update_by], [update_time]) VALUES (109, 102, N'0,100,102', N'财务部门', 2, N'若依', N'15888888888', N'ry@qq.com', N'0', N'0', N'admin', getdate(), N'', NULL)
GO

CREATE TABLE [sys_dict_data]
(
    [dict_code]   bigint                      NOT NULL,
    [dict_sort]   int           DEFAULT ((0)) NULL,
    [dict_label]  nvarchar(100) DEFAULT ''    NULL,
    [dict_value]  nvarchar(100) DEFAULT ''    NULL,
    [dict_type]   nvarchar(100) DEFAULT ''    NULL,
    [css_class]   nvarchar(100)               NULL,
    [list_class]  nvarchar(100)               NULL,
    [is_default]  nchar(1)      DEFAULT ('N') NULL,
    [status]      nchar(1)      DEFAULT ('0') NULL,
    [create_by]   nvarchar(64)  DEFAULT ''    NULL,
    [create_time] datetime2(7)                NULL,
    [update_by]   nvarchar(64)  DEFAULT ''    NULL,
    [update_time] datetime2(7)                NULL,
    [remark]      nvarchar(500)               NULL,
    CONSTRAINT [PK__sys_dict_data] PRIMARY KEY CLUSTERED ([dict_code])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典编码' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'dict_code'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典排序' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'dict_sort'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典标签' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'dict_label'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典键值' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'dict_value'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典类型' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'dict_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'样式属性（其他样式扩展）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'css_class'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'表格回显样式' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'list_class'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否默认（Y是 N否）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'is_default'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'状态（0正常 1停用）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典数据表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_data'
GO

INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1, 1, N'男', N'0', N'sys_user_sex', N'', N'', N'Y', N'0', N'admin', getdate(), N'', NULL, N'性别男')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (2, 2, N'女', N'1', N'sys_user_sex', N'', N'', N'N', N'0', N'admin', getdate(), N'', NULL, N'性别女')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (3, 3, N'未知', N'2', N'sys_user_sex', N'', N'', N'N', N'0', N'admin', getdate(), N'', NULL, N'性别未知')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (4, 1, N'显示', N'0', N'sys_show_hide', N'', N'primary', N'Y', N'0', N'admin', getdate(), N'', NULL, N'显示菜单')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (5, 2, N'隐藏', N'1', N'sys_show_hide', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'隐藏菜单')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (6, 1, N'正常', N'0', N'sys_normal_disable', N'', N'primary', N'Y', N'0', N'admin', getdate(), N'', NULL, N'正常状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (7, 2, N'停用', N'1', N'sys_normal_disable', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'停用状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (8, 1, N'正常', N'0', N'sys_job_status', N'', N'primary', N'Y', N'0', N'admin', getdate(), N'', NULL, N'正常状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (9, 2, N'暂停', N'1', N'sys_job_status', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'停用状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (10, 1, N'默认', N'DEFAULT', N'sys_job_group', N'', N'', N'Y', N'0', N'admin', getdate(), N'', NULL, N'默认分组')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (11, 2, N'系统', N'SYSTEM', N'sys_job_group', N'', N'', N'N', N'0', N'admin', getdate(), N'', NULL, N'系统分组')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (12, 1, N'是', N'Y', N'sys_yes_no', N'', N'primary', N'Y', N'0', N'admin', getdate(), N'', NULL, N'系统默认是')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (13, 2, N'否', N'N', N'sys_yes_no', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'系统默认否')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (14, 1, N'通知', N'1', N'sys_notice_type', N'', N'warning', N'Y', N'0', N'admin', getdate(), N'', NULL, N'通知')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (15, 2, N'公告', N'2', N'sys_notice_type', N'', N'success', N'N', N'0', N'admin', getdate(), N'', NULL, N'公告')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (16, 1, N'正常', N'0', N'sys_notice_status', N'', N'primary', N'Y', N'0', N'admin', getdate(), N'', NULL, N'正常状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (17, 2, N'关闭', N'1', N'sys_notice_status', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'关闭状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (29, 99, N'其他', N'0', N'sys_oper_type', N'', N'info', N'N', N'0', N'admin', getdate(), N'', NULL, N'其他操作');
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (18, 1, N'新增', N'1', N'sys_oper_type', N'', N'info', N'N', N'0', N'admin', getdate(), N'', NULL, N'新增操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (19, 2, N'修改', N'2', N'sys_oper_type', N'', N'info', N'N', N'0', N'admin', getdate(), N'', NULL, N'修改操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (20, 3, N'删除', N'3', N'sys_oper_type', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'删除操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (21, 4, N'授权', N'4', N'sys_oper_type', N'', N'primary', N'N', N'0', N'admin', getdate(), N'', NULL, N'授权操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (22, 5, N'导出', N'5', N'sys_oper_type', N'', N'warning', N'N', N'0', N'admin', getdate(), N'', NULL, N'导出操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (23, 6, N'导入', N'6', N'sys_oper_type', N'', N'warning', N'N', N'0', N'admin', getdate(), N'', NULL, N'导入操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (24, 7, N'强退', N'7', N'sys_oper_type', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'强退操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (25, 8, N'生成代码', N'8', N'sys_oper_type', N'', N'warning', N'N', N'0', N'admin', getdate(), N'', NULL, N'生成操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (26, 9, N'清空数据', N'9', N'sys_oper_type', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'清空操作')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (27, 1, N'成功', N'0', N'sys_common_status', N'', N'primary', N'N', N'0', N'admin', getdate(), N'', NULL, N'正常状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (28, 2, N'失败', N'1', N'sys_common_status', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'停用状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (30, 1, N'进行中', N'running', N'wf_process_status', N'', N'primary', N'N', N'0', N'admin', getdate(), N'', NULL, N'进行中状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (31, 2, N'已终止', N'terminated', N'wf_process_status', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'已终止状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (32, 3, N'已完成', N'completed', N'wf_process_status', N'', N'success', N'N', N'0', N'admin', getdate(), N'', NULL, N'已完成状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (33, 4, N'已取消', N'canceled', N'wf_process_status', N'', N'warning', N'N', N'0', N'admin', getdate(), N'', NULL, N'已取消状态')
GO

CREATE TABLE [sys_dict_type]
(
    [dict_id]     bigint                      NOT NULL,
    [dict_name]   nvarchar(100) DEFAULT ''    NULL,
    [dict_type]   nvarchar(100) DEFAULT ''    NULL,
    [status]      nchar(1)      DEFAULT ('0') NULL,
    [create_by]   nvarchar(64)  DEFAULT ''    NULL,
    [create_time] datetime2(7)                NULL,
    [update_by]   nvarchar(64)  DEFAULT ''    NULL,
    [update_time] datetime2(7)                NULL,
    [remark]      nvarchar(500)               NULL,
    CONSTRAINT [PK__sys_dict_type] PRIMARY KEY CLUSTERED ([dict_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典主键' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'dict_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'dict_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典类型' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'dict_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'状态（0正常 1停用）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'字典类型表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_dict_type'
GO

INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1, N'用户性别', N'sys_user_sex', N'0', N'admin', getdate(), N'', NULL, N'用户性别列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (2, N'菜单状态', N'sys_show_hide', N'0', N'admin', getdate(), N'', NULL, N'菜单状态列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (3, N'系统开关', N'sys_normal_disable', N'0', N'admin', getdate(), N'', NULL, N'系统开关列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (4, N'任务状态', N'sys_job_status', N'0', N'admin', getdate(), N'', NULL, N'任务状态列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (5, N'任务分组', N'sys_job_group', N'0', N'admin', getdate(), N'', NULL, N'任务分组列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (6, N'系统是否', N'sys_yes_no', N'0', N'admin', getdate(), N'', NULL, N'系统是否列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (7, N'通知类型', N'sys_notice_type', N'0', N'admin', getdate(), N'', NULL, N'通知类型列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (8, N'通知状态', N'sys_notice_status', N'0', N'admin', getdate(), N'', NULL, N'通知状态列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (9, N'操作类型', N'sys_oper_type', N'0', N'admin', getdate(), N'', NULL, N'操作类型列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (10, N'系统状态', N'sys_common_status', N'0', N'admin', getdate(), N'', NULL, N'登录状态列表')
GO
INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (11, N'流程状态', N'wf_process_status', N'0', N'admin', getdate(), N'', NULL, N'工作流程状态')
GO

CREATE TABLE [sys_logininfor]
(
    [info_id]        bigint                      NOT NULL,
    [user_name]      nvarchar(50)  DEFAULT ''    NULL,
    [ipaddr]         nvarchar(128) DEFAULT ''    NULL,
    [login_location] nvarchar(255) DEFAULT ''    NULL,
    [browser]        nvarchar(50)  DEFAULT ''    NULL,
    [os]             nvarchar(50)  DEFAULT ''    NULL,
    [status]         nchar(1)      DEFAULT ('0') NULL,
    [msg]            nvarchar(255) DEFAULT ''    NULL,
    [login_time]     datetime2(7)                NULL,
    CONSTRAINT [PK__sys_logininfor] PRIMARY KEY CLUSTERED ([info_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'访问ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'info_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户账号' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'user_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'登录IP地址' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'ipaddr'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'登录地点' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'login_location'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'浏览器类型' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'browser'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'操作系统' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'os'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'登录状态（0成功 1失败）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'提示消息' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'msg'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'访问时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor',
    'COLUMN', N'login_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'系统访问记录' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_logininfor'
GO

CREATE TABLE [sys_menu]
(
    [menu_id]     bigint                      NOT NULL,
    [menu_name]   nvarchar(50)                NOT NULL,
    [parent_id]   bigint        DEFAULT ((0)) NULL,
    [order_num]   int           DEFAULT ((0)) NULL,
    [path]        nvarchar(200) DEFAULT ''    NULL,
    [component]   nvarchar(255)               NULL,
    [query_param] nvarchar(255)               NULL,
    [is_frame]    int           DEFAULT ((1)) NULL,
    [is_cache]    int           DEFAULT ((0)) NULL,
    [menu_type]   nchar(1)      DEFAULT ''    NULL,
    [visible]     nchar(1)      DEFAULT ((0)) NULL,
    [status]      nchar(1)      DEFAULT ((0)) NULL,
    [perms]       nvarchar(100)               NULL,
    [icon]        nvarchar(100) DEFAULT ('#') NULL,
    [create_by]   nvarchar(64)  DEFAULT ''    NULL,
    [create_time] datetime2(7)                NULL,
    [update_by]   nvarchar(64)  DEFAULT ''    NULL,
    [update_time] datetime2(7)                NULL,
    [remark]      nvarchar(500) DEFAULT ''    NULL,
    CONSTRAINT [PK__sys_menu] PRIMARY KEY CLUSTERED ([menu_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'菜单ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'menu_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'菜单名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'menu_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'父菜单ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'parent_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'显示顺序' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'order_num'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'路由地址' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'path'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'组件路径' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'component'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'路由参数' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'query_param'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否为外链（0是 1否）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'is_frame'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'是否缓存（0缓存 1不缓存）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'is_cache'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'菜单类型（M目录 C菜单 F按钮）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'menu_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'显示状态（0显示 1隐藏）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'visible'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'菜单状态（0正常 1停用）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'权限标识' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'perms'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'菜单图标' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'icon'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'菜单权限表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_menu'
GO

-- 一级菜单
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1, N'系统管理', 0, 1, N'system',   NULL, N'', 1, 0, N'M', N'0', N'0', N'', N'system',  N'admin', getdate(), N'', NULL, N'系统管理目录')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (2, N'系统监控', 0, 2, N'monitor',  NULL, N'', 1, 0, N'M', N'0', N'0', N'', N'monitor', N'admin', getdate(), N'', NULL, N'系统监控目录')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (3, N'系统工具', 0, 3, N'tool',     NULL, N'', 1, 0, N'M', N'0', N'0', N'', N'tool',    N'admin', getdate(), N'', NULL, N'系统工具目录')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (4, N'流程管理', 0, 4, N'process',  NULL, N'', 1, 0, N'M', N'0', N'0', N'', N'skill',   N'admin', getdate(), N'', NULL, '流程管理目录')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (5, N'办公管理', 0, 5, N'work',     NULL, N'', 1, 0, N'M', N'0', N'0', N'', N'job',     N'admin', getdate(), N'', NULL, '办公管理目录')
GO

-- 二级菜单
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (100, N'用户管理',    1, 1, N'user', N'system/user/index', N'', 1, 0, N'C', N'0', N'0', N'system:user:list', N'user', N'admin', getdate(), N'', NULL, N'用户管理菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (101, N'角色管理',    1, 2, N'role', N'system/role/index', N'', 1, 0, N'C', N'0', N'0', N'system:role:list', N'peoples', N'admin', getdate(), N'', NULL, N'角色管理菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (102, N'菜单管理',    1, 3, N'menu', N'system/menu/index', N'', 1, 0, N'C', N'0', N'0', N'system:menu:list', N'tree-table', N'admin', getdate(), N'', NULL, N'菜单管理菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (103, N'部门管理',    1, 4, N'dept', N'system/dept/index', N'', 1, 0, N'C', N'0', N'0', N'system:dept:list', N'tree', N'admin', getdate(), N'', NULL, N'部门管理菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (104, N'岗位管理',    1, 5, N'post', N'system/post/index', N'', 1, 0, N'C', N'0', N'0', N'system:post:list', N'post', N'admin', getdate(), N'', NULL, N'岗位管理菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (105, N'字典管理',    1, 6, N'dict', N'system/dict/index', N'', 1, 0, N'C', N'0', N'0', N'system:dict:list', N'dict', N'admin', getdate(), N'', NULL, N'字典管理菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (106, N'参数设置',    1, 7, N'config', N'system/config/index', N'', 1, 0, N'C', N'0', N'0', N'system:config:list', N'edit', N'admin', getdate(), N'', NULL, N'参数设置菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (107, N'通知公告',    1, 8, N'notice', N'system/notice/index', N'', 1, 0, N'C', N'0', N'0', N'system:notice:list', N'message', N'admin', getdate(), N'', NULL, N'通知公告菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (108, N'日志管理',    1, 9, N'log', N'', N'', 1, 0, N'M', N'0', N'0', N'', N'log', N'admin', getdate(), N'', NULL, N'日志管理菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (109, N'在线用户',    2, 1, N'online', N'monitor/online/index', N'', 1, 0, N'C', N'0', N'0', N'monitor:online:list', N'online', N'admin', getdate(), N'', NULL, N'在线用户菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (111, N'数据监控',    2, 3, N'druid', N'monitor/druid/index', N'', 1, 0, N'C', N'0', N'0', N'monitor:druid:list', N'druid', N'admin', getdate(), N'', NULL, N'数据监控菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (112, N'缓存列表',    2, 6, N'cacheList', N'monitor/cache/list', N'', 1, 0, N'C', N'0', N'0', N'monitor:cache:list', N'redis-list', N'admin', getdate(), N'', NULL, N'缓存列表菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (113, N'缓存监控',    2, 5, N'cache', N'monitor/cache/index', N'', 1, 0, N'C', N'0', N'0', N'monitor:cache:list', N'redis', N'admin', getdate(), N'', NULL, N'缓存监控菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (114, N'表单构建',    3, 1, N'build', N'tool/build/index', N'', 1, 0, N'C', N'0', N'0', N'tool:build:list', N'build', N'admin', getdate(), N'', NULL, N'表单构建菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (115, N'代码生成',    3, 2, N'gen', N'tool/gen/index', N'', 1, 0, N'C', N'0', N'0', N'tool:gen:list', N'code', N'admin', getdate(), N'', NULL, N'代码生成菜单')
GO
-- springboot-admin监控
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (117, N'Admin监控',  2, 5, N'Admin', N'monitor/admin/index', N'', 1, 0, N'C', N'0', N'0', N'monitor:admin:list', N'dashboard', N'admin', getdate(), N'', NULL, N'Admin监控菜单')
GO
-- oss菜单
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (118, N'文件管理',    1, 10, N'oss', N'system/oss/index', N'', 1, 0, N'C', '0', N'0', N'system:oss:list', N'upload', N'admin', getdate(), N'', NULL, N'文件管理菜单');
GO
-- xxl-job-admin控制台
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (120, N'任务调度中心', 2, 5, N'XxlJob', N'monitor/xxljob/index', N'', 1, 0, N'C', N'0', N'0', N'monitor:xxljob:list', N'job', N'admin', getdate(), N'', NULL, N'Xxl-Job控制台菜单')
GO
-- 流程管理
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (121,  N'流程分类',   4, 1, N'category',   N'workflow/category/index',      N'', 1, 0, N'C', N'0', N'0', N'workflow:category:list',   N'nested',    N'admin', getdate(), N'', NULL, N'流程分类菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (122,  N'表单配置',   4, 2, N'form',       N'workflow/form/index',          N'', 1, 0, N'C', N'0', N'0', N'workflow:form:list',       N'form',      N'admin', getdate(), N'', NULL, N'表单配置菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (123,  N'流程模型',   4, 3, N'model',      N'workflow/model/index',         N'', 1, 0, N'C', N'0', N'0', N'workflow:model:list',      N'component', N'admin', getdate(), N'', NULL, N'流程模型菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (124,  N'部署管理',   4, 4, N'deploy',     N'workflow/deploy/index',        N'', 1, 0, N'C', N'0', N'0', N'workflow:deploy:list',     N'example',   N'admin', getdate(), N'', NULL, N'部署管理菜单')
GO
-- 办公管理
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (125,  N'新建流程',   5, 1, N'create',     N'workflow/work/index',      N'', 1, 0, N'C', N'0', N'0', N'workflow:process:startList',    N'guide',      N'admin', getdate(), N'', NULL, N'新建流程菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (126,  N'我的流程',   5, 2, N'own',        N'workflow/work/own',        N'', 1, 0, N'C', N'0', N'0', N'workflow:process:ownList',      N'cascader',   N'admin', getdate(), N'', NULL, N'我的流程菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (127,  N'待办任务',   5, 3, N'todo',       N'workflow/work/todo',       N'', 1, 0, N'C', N'0', N'0', N'workflow:process:todoList',     N'time-range', N'admin', getdate(), N'', NULL, N'待办任务菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (128,  N'待签任务',   5, 4, N'claim',      N'workflow/work/claim',      N'', 1, 0, N'C', N'0', N'0', N'workflow:process:claimList',    N'checkbox',   N'admin', getdate(), N'', NULL, N'待签任务菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (129,  N'已办任务',   5, 5, N'finished',   N'workflow/work/finished',   N'', 1, 0, N'C', N'0', N'0', N'workflow:process:finishedList', N'checkbox',   N'admin', getdate(), N'', NULL, N'已办任务菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (130,  N'抄送我的',   5, 6, N'copy',       N'workflow/work/copy',       N'', 1, 0, N'C', N'0', N'0', N'workflow:process:copyList',     N'checkbox',   N'admin', getdate(), N'', NULL, N'抄送我的菜单')
GO

-- 三级菜单
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (500, N'操作日志', 108, 1, N'operlog', N'monitor/operlog/index', N'', 1, 0, N'C', N'0', N'0', N'monitor:operlog:list', N'form', N'admin', getdate(), N'', NULL, N'操作日志菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (501, N'登录日志', 108, 2, N'logininfor', N'monitor/logininfor/index', N'', 1, 0, N'C', N'0', N'0', N'monitor:logininfor:list', N'logininfor', N'admin', getdate(), N'', NULL, N'登录日志菜单')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1001, N'用户查询', 100, 1, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:user:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1002, N'用户新增', 100, 2, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:user:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1003, N'用户修改', 100, 3, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:user:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1004, N'用户删除', 100, 4, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:user:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1005, N'用户导出', 100, 5, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:user:export', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1006, N'用户导入', 100, 6, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:user:import', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1007, N'重置密码', 100, 7, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:user:resetPwd', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1010, N'角色查询', 101, 1, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:role:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1011, N'角色新增', 101, 2, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:role:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1012, N'角色修改', 101, 3, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:role:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1013, N'角色删除', 101, 4, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:role:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1014, N'角色导出', 101, 5, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:role:export', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1020, N'菜单查询', 102, 1, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:menu:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1021, N'菜单新增', 102, 2, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:menu:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1022, N'菜单修改', 102, 3, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:menu:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1023, N'菜单删除', 102, 4, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:menu:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1030, N'部门查询', 103, 1, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dept:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1031, N'部门新增', 103, 2, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dept:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1032, N'部门修改', 103, 3, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dept:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1033, N'部门删除', 103, 4, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dept:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1040, N'岗位查询', 104, 1, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:post:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1041, N'岗位新增', 104, 2, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:post:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1042, N'岗位修改', 104, 3, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:post:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1043, N'岗位删除', 104, 4, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:post:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1044, N'岗位导出', 104, 5, N'', N'', N'', 1, 0, N'F', N'0', N'0', N'system:post:export', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1050, N'字典查询', 105, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dict:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1051, N'字典新增', 105, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dict:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1052, N'字典修改', 105, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dict:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1053, N'字典删除', 105, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dict:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1054, N'字典导出', 105, 5, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:dict:export', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1060, N'参数查询', 106, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:config:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1061, N'参数新增', 106, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:config:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1062, N'参数修改', 106, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:config:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1063, N'参数删除', 106, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:config:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1064, N'参数导出', 106, 5, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:config:export', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1070, N'公告查询', 107, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:notice:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1071, N'公告新增', 107, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:notice:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1072, N'公告修改', 107, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:notice:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1073, N'公告删除', 107, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:notice:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1080, N'操作查询', 500, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:operlog:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1081, N'操作删除', 500, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:operlog:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1082, N'日志导出', 500, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:operlog:export', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1090, N'登录查询', 501, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:logininfor:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1091, N'登录删除', 501, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:logininfor:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1092, N'日志导出', 501, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:logininfor:export', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1093, N'账户解锁', 501, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:logininfor:unlock',  N'#', N'admin', getdate(), N'', null, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1100, N'在线查询', 109, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:online:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1101, N'批量强退', 109, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:online:batchLogout', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1102, N'单条强退', 109, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'monitor:online:forceLogout', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1120, N'生成查询', 115, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'tool:gen:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1121, N'生成修改', 115, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'tool:gen:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1122, N'生成删除', 115, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'tool:gen:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1123, N'导入代码', 115, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'tool:gen:import', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1124, N'预览代码', 115, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'tool:gen:preview', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1125, N'生成代码', 115, 5, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'tool:gen:code', N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- oss相关按钮
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1130, N'文件查询', 118, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:oss:query', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1131, N'文件上传', 118, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:oss:upload', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1132, N'文件下载', 118, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:oss:download', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1133, N'文件删除', 118, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:oss:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1134, N'配置添加', 118, 5, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:oss:add', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1135, N'配置编辑', 118, 6, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:oss:edit', N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 流程分类管理
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1140, N'分类查询', 121, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:category:query',  N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1141, N'分类新增', 121, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:category:add',    N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1142, N'分类编辑', 121, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:category:edit',   N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1143, N'分类删除', 121, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:category:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 表单配置
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1150, N'表单查询', 122, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:form:query',  N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1151, N'表单新增', 122, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:form:add',    N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1152, N'表单修改', 122, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:form:edit',   N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1153, N'表单删除', 122, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:form:remove', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1154, N'表单导出', 122, 5, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:form:export', N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 流程定义
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1160, N'模型查询', 123, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:query',    N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1161, N'模型新增', 123, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:add',      N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1162, N'模型修改', 123, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:edit',     N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1163, N'模型删除', 123, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:remove',   N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1164, N'模型导出', 123, 5, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:export',   N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1165, N'模型导入', 123, 6, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:import',   N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1166, N'模型设计', 123, 7, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:designer', N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1167, N'模型保存', 123, 8, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:save',     N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1168, N'流程部署', 123, 9, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:model:deploy',   N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 部署管理
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1170, N'部署查询', 124, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:deploy:query',   N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1171, N'部署删除', 124, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:deploy:remove',  N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1172, N'更新状态', 124, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:deploy:status',  N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 新建流程
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1180, N'发起流程',    125, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:start',        N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1181, N'新建流程导出', 125, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:startExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 我的流程
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1190, N'流程详情',    126, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:query',      N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1191, N'流程删除',    126, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:remove',     N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1192, N'流程取消',    126, 3, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:cancel',     N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1193, N'我的流程导出', 126, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:ownExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 待办任务
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1200, N'流程办理',    127, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:approval',    N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1201, N'待办流程导出', 127, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:todoExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 待签任务
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1210, N'流程签收',    128, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:claim',         N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1211, N'待签流程导出', 128, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:claimExport',   N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 已办任务
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1220, N'流程撤回', 129, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:revoke',          N'#', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1221, N'流程撤回', 129, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:finishedExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO
-- 抄送我的
INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1230, N'抄送流程导出', 130, 1, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:copyExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO

CREATE TABLE [sys_notice]
(
    [notice_id]      bigint                     NOT NULL,
    [notice_title]   nvarchar(50)               NOT NULL,
    [notice_type]    nchar(1)                   NOT NULL,
    [notice_content] nvarchar(max)              NULL,
    [status]         nchar(1)     DEFAULT ('0') NULL,
    [create_by]      nvarchar(64) DEFAULT ''    NULL,
    [create_time]    datetime2(7)               NULL,
    [update_by]      nvarchar(64) DEFAULT ''    NULL,
    [update_time]    datetime2(7)               NULL,
    [remark]         nvarchar(255)              NULL,
    CONSTRAINT [PK__sys_notice] PRIMARY KEY CLUSTERED ([notice_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'公告ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'notice_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'公告标题' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'notice_title'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'公告类型（1通知 2公告）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'notice_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'公告内容' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'notice_content'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'公告状态（0正常 1关闭）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'通知公告表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_notice'
GO

INSERT [sys_notice] ([notice_id], [notice_title], [notice_type], [notice_content], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1, N'温馨提醒：2018-07-01 若依新版本发布啦', N'2', N'<p>1111111111</p>', N'0', N'admin', getdate(), N'admin', getdate(), N'管理员')
GO
INSERT [sys_notice] ([notice_id], [notice_title], [notice_type], [notice_content], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (2, N'维护通知：2018-07-01 若依系统凌晨维护', N'1', N'<p><img src="/dev-api/profile/upload/2021/12/04/f1e00aba-0182-46b0-9c65-7804dfd0ea4e.png"></p>', N'0', N'admin', getdate(), N'admin', getdate(), N'管理员')
GO

CREATE TABLE [sys_oper_log]
(
    [oper_id]        bigint                       NOT NULL,
    [title]          nvarchar(50)   DEFAULT ''    NULL,
    [business_type]  int            DEFAULT ((0)) NULL,
    [method]         nvarchar(100)  DEFAULT ''    NULL,
    [request_method] nvarchar(10)   DEFAULT ''    NULL,
    [operator_type]  int            DEFAULT ((0)) NULL,
    [oper_name]      nvarchar(50)   DEFAULT ''    NULL,
    [dept_name]      nvarchar(50)   DEFAULT ''    NULL,
    [oper_url]       nvarchar(255)  DEFAULT ''    NULL,
    [oper_ip]        nvarchar(128)  DEFAULT ''    NULL,
    [oper_location]  nvarchar(255)  DEFAULT ''    NULL,
    [oper_param]     nvarchar(2000) DEFAULT ''    NULL,
    [json_result]    nvarchar(2000) DEFAULT ''    NULL,
    [status]         int            DEFAULT ((0)) NULL,
    [error_msg]      nvarchar(2000) DEFAULT ''    NULL,
    [oper_time]      datetime2(7)                 NULL,
    CONSTRAINT [PK__sys_oper_log] PRIMARY KEY CLUSTERED ([oper_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'日志主键' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'oper_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'模块标题' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'title'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'业务类型（0其它 1新增 2修改 3删除）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'business_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'方法名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'method'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'请求方式' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'request_method'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'操作类别（0其它 1后台用户 2手机端用户）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'operator_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'操作人员' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'oper_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'部门名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'dept_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'请求URL' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'oper_url'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'主机地址' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'oper_ip'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'操作地点' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'oper_location'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'请求参数' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'oper_param'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'返回参数' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'json_result'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'操作状态（0正常 1异常）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'错误消息' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'error_msg'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'操作时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log',
    'COLUMN', N'oper_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'操作日志记录' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oper_log'
GO

CREATE TABLE [sys_post]
(
    [post_id]     bigint                  NOT NULL,
    [post_code]   nvarchar(64)            NOT NULL,
    [post_name]   nvarchar(50)            NOT NULL,
    [post_sort]   int                     NOT NULL,
    [status]      nchar(1)                NOT NULL,
    [create_by]   nvarchar(64) DEFAULT '' NULL,
    [create_time] datetime2(7)            NULL,
    [update_by]   nvarchar(64) DEFAULT '' NULL,
    [update_time] datetime2(7)            NULL,
    [remark]      nvarchar(500)           NULL,
    CONSTRAINT [PK__sys_post] PRIMARY KEY CLUSTERED ([post_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'岗位ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'post_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'岗位编码' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'post_code'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'岗位名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'post_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'显示顺序' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'post_sort'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'状态（0正常 1停用）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'岗位信息表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_post'
GO

INSERT [sys_post] ([post_id], [post_code], [post_name], [post_sort], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1, N'ceo', N'董事长', 1, N'0', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_post] ([post_id], [post_code], [post_name], [post_sort], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (2, N'se', N'项目经理', 2, N'0', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_post] ([post_id], [post_code], [post_name], [post_sort], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (3, N'hr', N'人力资源', 3, N'0', N'admin', getdate(), N'', NULL, N'')
GO
INSERT [sys_post] ([post_id], [post_code], [post_name], [post_sort], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (4, N'user', N'普通员工', 4, N'0', N'admin', getdate(), N'', NULL, N'')
GO

CREATE TABLE [sys_role]
(
    [role_id]             bigint                     NOT NULL,
    [role_name]           nvarchar(30)               NOT NULL,
    [role_key]            nvarchar(100)              NOT NULL,
    [role_sort]           int                        NOT NULL,
    [data_scope]          nchar(1)     DEFAULT ('1') NULL,
    [menu_check_strictly] tinyint      DEFAULT ((1)) NULL,
    [dept_check_strictly] tinyint      DEFAULT ((1)) NULL,
    [status]              nchar(1)                   NOT NULL,
    [del_flag]            nchar(1)     DEFAULT ('0') NULL,
    [create_by]           nvarchar(64) DEFAULT ''    NULL,
    [create_time]         datetime2(7)               NULL,
    [update_by]           nvarchar(64) DEFAULT ''    NULL,
    [update_time]         datetime2(7)               NULL,
    [remark]              nvarchar(500)              NULL,
    CONSTRAINT [PK__sys_role] PRIMARY KEY CLUSTERED ([role_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'role_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色名称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'role_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色权限字符串' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'role_key'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'显示顺序' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'role_sort'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'data_scope'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'菜单树选择项是否关联显示' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'menu_check_strictly'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'部门树选择项是否关联显示' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'dept_check_strictly'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色状态（0正常 1停用）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'删除标志（0代表存在 2代表删除）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'del_flag'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色信息表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role'
GO

INSERT [sys_role] ([role_id], [role_name], [role_key], [role_sort], [data_scope], [menu_check_strictly], [dept_check_strictly], [status], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1, N'超级管理员', N'admin', 1, N'1', 1, 1, N'0', N'0', N'admin', getdate(), N'', NULL, N'超级管理员')
GO
INSERT [sys_role] ([role_id], [role_name], [role_key], [role_sort], [data_scope], [menu_check_strictly], [dept_check_strictly], [status], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (2, N'普通角色', N'common', 2, N'2', 1, 1, N'0', N'0', N'admin', getdate(), N'admin', CAST(N'2021-12-04T15:44:20.0000000' AS DateTime2), N'普通角色')
GO

CREATE TABLE [sys_role_dept]
(
    [role_id] bigint NOT NULL,
    [dept_id] bigint NOT NULL,
    CONSTRAINT [PK__sys_role_dept] PRIMARY KEY CLUSTERED ([role_id], [dept_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role_dept',
    'COLUMN', N'role_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'部门ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role_dept',
    'COLUMN', N'dept_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色和部门关联表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role_dept'
GO

INSERT [sys_role_dept] ([role_id], [dept_id]) VALUES (2, 100)
GO
INSERT [sys_role_dept] ([role_id], [dept_id]) VALUES (2, 101)
GO
INSERT [sys_role_dept] ([role_id], [dept_id]) VALUES (2, 105)
GO

CREATE TABLE [sys_role_menu]
(
    [role_id] bigint NOT NULL,
    [menu_id] bigint NOT NULL,
    CONSTRAINT [PK__sys_role_menu] PRIMARY KEY CLUSTERED ([role_id], [menu_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role_menu',
    'COLUMN', N'role_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'菜单ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role_menu',
    'COLUMN', N'menu_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色和菜单关联表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_role_menu'
GO

INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 2)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 3)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 4)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 5)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 100)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 101)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 102)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 103)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 104)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 105)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 106)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 107)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 108)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 109)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 110)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 111)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 112)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 113)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 114)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 115)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 117)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 118)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 120)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 122)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 123)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 124)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 125)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 126)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 127)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 128)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 129)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 130)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 500)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 501)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1001)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1002)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1003)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1004)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1005)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1006)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1007)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1010)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1011)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1012)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1013)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1014)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1020)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1021)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1022)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1023)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1030)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1031)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1032)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1033)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1040)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1041)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1042)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1043)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1044)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1050)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1051)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1052)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1053)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1054)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1060)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1061)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1062)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1063)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1064)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1070)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1071)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1072)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1073)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1080)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1081)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1082)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1090)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1091)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1092)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1093)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1100)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1101)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1102)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1120)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1121)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1122)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1123)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1124)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1125)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1130)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1131)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1132)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1133)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1134)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1135)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1140)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1141)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1142)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1143)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1150)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1151)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1152)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1153)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1154)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1160)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1161)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1162)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1163)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1164)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1165)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1166)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1167)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1168)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1170)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1171)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1172)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1180)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1181)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1190)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1191)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1192)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1193)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1200)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1201)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1210)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1211)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1220)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1221)
GO
INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1230)
GO

CREATE TABLE [sys_user]
(
    [user_id]     bigint                             NOT NULL,
    [dept_id]     bigint                             NULL,
    [user_name]   nvarchar(30)                       NOT NULL,
    [nick_name]   nvarchar(30)                       NOT NULL,
    [user_type]   nvarchar(10)  DEFAULT ('sys_user') NULL,
    [email]       nvarchar(50)  DEFAULT ''           NULL,
    [phonenumber] nvarchar(11)  DEFAULT ''           NULL,
    [sex]         nchar(1)      DEFAULT ('0')        NULL,
    [avatar]      nvarchar(100) DEFAULT ''           NULL,
    [password]    nvarchar(100) DEFAULT ''           NULL,
    [status]      nchar(1)      DEFAULT ('0')        NULL,
    [del_flag]    nchar(1)      DEFAULT ('0')        NULL,
    [login_ip]    nvarchar(128) DEFAULT ''           NULL,
    [login_date]  datetime2(7)                       NULL,
    [create_by]   nvarchar(64)  DEFAULT ''           NULL,
    [create_time] datetime2(7)                       NULL,
    [update_by]   nvarchar(64)  DEFAULT ''           NULL,
    [update_time] datetime2(7)                       NULL,
    [remark]      nvarchar(500)                      NULL,
    CONSTRAINT [PK__sys_user] PRIMARY KEY CLUSTERED ([user_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'user_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'部门ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'dept_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户账号' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'user_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户昵称' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'nick_name'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户类型（sys_user系统用户）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'user_type'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户邮箱' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'email'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'手机号码' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'phonenumber'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户性别（0男 1女 2未知）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'sex'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'头像地址' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'avatar'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'密码' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'password'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'帐号状态（0正常 1停用）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'status'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'删除标志（0代表存在 2代表删除）' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'del_flag'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'最后登录IP' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'login_ip'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'最后登录时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'login_date'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'create_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'创建时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新者' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'update_by'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'更新时间' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'update_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'备注' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户信息表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user'
GO

INSERT [sys_user] ([user_id], [dept_id], [user_name], [nick_name], [user_type], [email], [phonenumber], [sex], [avatar], [password], [status], [del_flag], [login_ip], [login_date], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1, 103, N'admin', N'若依管理员', N'sys_user', N'crazyLionLi@163.com', N'15888888888', N'1', N'', N'$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', N'0', N'0', N'127.0.0.1', getdate(), N'admin', getdate(), N'', getdate(), N'管理员')
GO
INSERT [sys_user] ([user_id], [dept_id], [user_name], [nick_name], [user_type], [email], [phonenumber], [sex], [avatar], [password], [status], [del_flag], [login_ip], [login_date], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (2, 105, N'ry', N'若依', N'sys_user', N'crazyLionLi@qq.com', N'15666666666', N'1', N'', N'$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', N'0', N'0', N'127.0.0.1', getdate(), N'admin', getdate(), N'admin', getdate(), N'测试员')
GO

CREATE TABLE [sys_user_post]
(
    [user_id] bigint NOT NULL,
    [post_id] bigint NOT NULL,
    CONSTRAINT [PK__sys_user_post] PRIMARY KEY CLUSTERED ([user_id], [post_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user_post',
    'COLUMN', N'user_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'岗位ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user_post',
    'COLUMN', N'post_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户与岗位关联表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user_post'
GO

INSERT [sys_user_post] ([user_id], [post_id]) VALUES (1, 1)
GO
INSERT [sys_user_post] ([user_id], [post_id]) VALUES (2, 2)
GO

CREATE TABLE [sys_user_role]
(
    [user_id] bigint NOT NULL,
    [role_id] bigint NOT NULL,
    CONSTRAINT [PK__sys_user_role] PRIMARY KEY CLUSTERED ([user_id], [role_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user_role',
    'COLUMN', N'user_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'角色ID' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user_role',
    'COLUMN', N'role_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'用户和角色关联表' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_user_role'
GO

INSERT [sys_user_role] ([user_id], [role_id]) VALUES (1, 1)
GO
INSERT [sys_user_role] ([user_id], [role_id]) VALUES (2, 2)
GO

CREATE TABLE [sys_oss]
(
    [oss_id]        bigint                          NOT NULL,
    [file_name]     nvarchar(255) DEFAULT ''        NOT NULL,
    [original_name] nvarchar(255) DEFAULT ''        NOT NULL,
    [file_suffix]   nvarchar(10)  DEFAULT ''        NOT NULL,
    [url]           nvarchar(500)                   NOT NULL,
    [create_time]   datetime2(7)                    NULL,
    [create_by]     nvarchar(64)  DEFAULT ''        NULL,
    [update_time]   datetime2(7)                    NULL,
    [update_by]     nvarchar(64)  DEFAULT ''        NULL,
    [service]       nvarchar(20)  DEFAULT ('minio') NOT NULL,
    CONSTRAINT [PK__sys_oss] PRIMARY KEY CLUSTERED ([oss_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sp_addextendedproperty
    'MS_Description', N'对象存储主键',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'oss_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'文件名',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'file_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'原名',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'original_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'文件后缀名',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'file_suffix'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'URL地址',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'url'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建时间',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'create_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'上传人',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'create_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新时间',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'update_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新人',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'update_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'服务商',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss',
    'COLUMN', N'service'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'OSS对象存储表',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss'
GO

CREATE TABLE [sys_oss_config]
(
    [oss_config_id] bigint                      NOT NULL,
    [config_key]    nvarchar(20)  DEFAULT ''    NOT NULL,
    [access_key]    nvarchar(255) DEFAULT ''    NULL,
    [secret_key]    nvarchar(255) DEFAULT ''    NULL,
    [bucket_name]   nvarchar(255) DEFAULT ''    NULL,
    [prefix]        nvarchar(255) DEFAULT ''    NULL,
    [endpoint]      nvarchar(255) DEFAULT ''    NULL,
    [domain]        nvarchar(255) DEFAULT ''    NULL,
    [is_https]      nchar(1)      DEFAULT ('N') NULL,
    [region]        nvarchar(255) DEFAULT ''    NULL,
    [access_policy] nchar(1)      DEFAULT ('1') NOT NULL,
    [status]        nchar(1)      DEFAULT ('1') NULL,
    [ext1]          nvarchar(255) DEFAULT ''    NULL,
    [create_by]     nvarchar(64)  DEFAULT ''    NULL,
    [create_time]   datetime2(7)                NULL,
    [update_by]     nvarchar(64)  DEFAULT ''    NULL,
    [update_time]   datetime2(7)                NULL,
    [remark]        nvarchar(500)               NULL,
    CONSTRAINT [PK__sys_oss_config] PRIMARY KEY CLUSTERED ([oss_config_id])
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
GO

EXEC sp_addextendedproperty
    'MS_Description', N'主建',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'oss_config_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'配置key',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'config_key'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'accessKey',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'access_key'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'秘钥',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'secret_key'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'桶名称',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'bucket_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'前缀',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'prefix'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'访问站点',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'endpoint'
GO
EXEC sp_addextendedproperty
     'MS_Description', N'自定义域名',
     'SCHEMA', N'dbo',
     'TABLE', N'sys_oss_config',
     'COLUMN', N'domain'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'是否https（Y=是,N=否）',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'is_https'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'域',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'region'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'桶权限类型(0=private 1=public 2=custom)',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'access_policy'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'状态（0=正常,1=停用）',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'status'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'扩展字段',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'ext1'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建者',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'create_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建时间',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'create_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新者',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'update_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新时间',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'update_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'备注',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config',
    'COLUMN', N'remark'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'对象存储配置表',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_oss_config'
GO

INSERT INTO [sys_oss_config] ([oss_config_id], [config_key], [access_key], [secret_key], [bucket_name], [prefix], [endpoint], [domain], [is_https], [region], [access_policy], [status], [ext1], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'1', N'minio', N'ruoyi',            N'ruoyi123',        N'ruoyi',            N'', N'127.0.0.1:9000',                    N'',N'N', N'',           N'1', N'0', N'', N'admin', getdate(), N'admin', getdate(), NULL)
GO
INSERT INTO [sys_oss_config] ([oss_config_id], [config_key], [access_key], [secret_key], [bucket_name], [prefix], [endpoint], [domain], [is_https], [region], [access_policy], [status], [ext1], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'2', N'qiniu', N'XXXXXXXXXXXXXXXX', N'XXXXXXXXXXXXXXX', N'ruoyi',            N'', N's3-cn-north-1.qiniucs.com',         N'',N'N', N'',           N'1', N'1', N'', N'admin', getdate(), N'admin', getdate(), NULL)
GO
INSERT INTO [sys_oss_config] ([oss_config_id], [config_key], [access_key], [secret_key], [bucket_name], [prefix], [endpoint], [domain], [is_https], [region], [access_policy], [status], [ext1], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'3', N'aliyun', N'XXXXXXXXXXXXXXX', N'XXXXXXXXXXXXXXX', N'ruoyi',            N'', N'oss-cn-beijing.aliyuncs.com',       N'',N'N', N'',           N'1', N'1', N'', N'admin', getdate(), N'admin', getdate(), NULL)
GO
INSERT INTO [sys_oss_config] ([oss_config_id], [config_key], [access_key], [secret_key], [bucket_name], [prefix], [endpoint], [domain], [is_https], [region], [access_policy], [status], [ext1], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'4', N'qcloud', N'XXXXXXXXXXXXXXX', N'XXXXXXXXXXXXXXX', N'ruoyi-1250000000', N'', N'cos.ap-beijing.myqcloud.com',       N'',N'N', N'ap-beijing', N'1', N'1', N'', N'admin', getdate(), N'admin', getdate(), NULL)
GO
INSERT INTO [sys_oss_config] ([oss_config_id], [config_key], [access_key], [secret_key], [bucket_name], [prefix], [endpoint], [domain], [is_https], [region], [access_policy], [status], [ext1], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'5', N'image',  N'ruoyi',           N'ruoyi123',        N'ruoyi',            N'image', N'127.0.0.1:9000',               N'',N'N', N'',           N'1', N'1', N'', N'admin', getdate(), N'admin', getdate(), NULL)
GO

CREATE TABLE [wf_category]
(
    [category_id]      bigint                        NOT NULL,
    [category_name]    nvarchar(64)   DEFAULT ''     NULL,
    [code]             nvarchar(64)   DEFAULT ''     NULL,
    [remark]           nvarchar(500)                 NULL,
    [create_by]        nvarchar(64)   DEFAULT ''     NULL,
    [create_time]      datetime2(7)                  NULL,
    [update_by]        nvarchar(64)   DEFAULT ''     NULL,
    [update_time]      datetime2(7)                  NULL,
    [del_flag]         nchar(1)       DEFAULT ('0')  NULL,
    CONSTRAINT [PK__wf_category] PRIMARY KEY CLUSTERED ([category_id])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
)
GO

EXEC sp_addextendedproperty
    'MS_Description', N'流程分类id',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'category_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程分类名称',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'category_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'分类编码',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'code'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'备注',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'remark'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建者',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'create_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建时间',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'create_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新者',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'update_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新时间',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'update_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'删除标志（0代表存在 2代表删除）',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category',
    'COLUMN', N'del_flag'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程分类表',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_category'
GO

CREATE TABLE [wf_form]
(
    [form_id]        bigint                          NOT NULL,
    [form_name]      nvarchar(64)    DEFAULT ''      NULL,
    [content]        nvarchar(max)                   NULL,
    [create_by]      nvarchar(64)    DEFAULT ''      NULL,
    [create_time]    datetime2(7)                    NULL,
    [update_by]      nvarchar(64)    DEFAULT ''      NULL,
    [update_time]    datetime2(7)                    NULL,
    [remark]         nvarchar(255)                   NULL,
    [del_flag]       nchar(1)        DEFAULT ('0')   NULL,
    CONSTRAINT [PK__wf_form] PRIMARY KEY CLUSTERED ([form_id])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
)
GO

EXEC sp_addextendedproperty
    'MS_Description', N'表单主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'form_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'表单名称',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'form_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'表单内容',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'content'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建者',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'create_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建时间',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'create_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新者',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'update_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新时间',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'update_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'备注',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'remark'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'删除标志（0代表存在 2代表删除）',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form',
    'COLUMN', N'del_flag'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程表单信息表',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_form'
GO

CREATE TABLE [wf_deploy_form]
(
    [deploy_id]  nvarchar(64)              NOT NULL,
    [form_key]   nvarchar(64)              NOT NULL,
    [node_key]   nvarchar(64)              NOT NULL,
    [form_name]  nvarchar(64)   DEFAULT '' NULL,
    [node_name]  nvarchar(64)   DEFAULT '' NULL,
    [content]    nvarchar(max)             NULL,
    CONSTRAINT [PK__wf_deploy_form] PRIMARY KEY CLUSTERED ([deploy_id], [form_key], [node_key])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
)
GO

EXEC sp_addextendedproperty
    'MS_Description', N'流程实例主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_deploy_form',
    'COLUMN', N'deploy_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'表单Key',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_deploy_form',
    'COLUMN', N'form_key'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'节点Key',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_deploy_form',
    'COLUMN', N'node_key'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'表单名称',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_deploy_form',
    'COLUMN', N'form_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'节点名称',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_deploy_form',
    'COLUMN', N'node_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'表单内容',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_deploy_form',
    'COLUMN', N'content'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程实例关联表单',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_deploy_form'
GO

CREATE TABLE [wf_copy]
(
    [copy_id]          bigint                        NOT NULL,
    [title]            nvarchar(255)  DEFAULT ''     NULL,
    [process_id]       nvarchar(64)   DEFAULT ''     NULL,
    [process_name]     nvarchar(255)  DEFAULT ''     NULL,
    [category_id]      nvarchar(255)  DEFAULT ''     NULL,
    [deployment_id]    nvarchar(64)   DEFAULT ''     NULL,
    [instance_id]      nvarchar(64)   DEFAULT ''     NULL,
    [task_id]          nvarchar(64)   DEFAULT ''     NULL,
    [user_id]          bigint                        NULL,
    [originator_id]    bigint                        NULL,
    [originator_name]  nvarchar(64)   DEFAULT ''     NULL,
    [create_by]        nvarchar(64)   DEFAULT ''     NULL,
    [create_time]      datetime2(7)                  NULL,
    [update_by]        nvarchar(64)   DEFAULT ''     NULL,
    [update_time]      datetime2(7)                  NULL,
    [del_flag]         nchar(1)       DEFAULT ('0')  NULL,
    CONSTRAINT [PK__wf_copy] PRIMARY KEY CLUSTERED ([copy_id])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
)
GO

EXEC sp_addextendedproperty
    'MS_Description', N'抄送主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'copy_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'抄送标题',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'title'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'process_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程名称',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'process_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程分类主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'category_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'部署主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'deployment_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程实例主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'instance_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'任务主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'task_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'用户主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'user_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'发起人主键',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'originator_id'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'发起人名称',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'originator_name'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建者',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'create_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'创建时间',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'create_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新者',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'update_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'更新时间',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'update_time'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'删除标志（0代表存在 2代表删除）',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy',
    'COLUMN', N'del_flag'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'流程抄送表',
    'SCHEMA', N'dbo',
    'TABLE', N'wf_copy'
GO
