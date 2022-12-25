INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1181, N'新建流程导出', 125, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:startExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO

INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1193, N'我的流程导出', 126, 4, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:ownExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO

INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1201, N'待办流程导出', 127, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:todoExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO

INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1211, N'待签流程导出', 128, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:claimExport',   N'#', N'admin', getdate(), N'', NULL, N'')
GO

INSERT [sys_menu] ([menu_id], [menu_name], [parent_id], [order_num], [path], [component], [query_param], [is_frame], [is_cache], [menu_type], [visible], [status], [perms], [icon], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (1221, N'流程撤回', 129, 2, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'workflow:process:finishedExport',  N'#', N'admin', getdate(), N'', NULL, N'')
GO

UPDATE sys_menu SET menu_name = '抄送流程导出', perms = 'workflow:process:copyExport' WHERE menu_id = 1230
GO

INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1181)
GO

INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1193)
GO

INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1201)
GO

INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1211)
GO

INSERT [sys_role_menu] ([role_id], [menu_id]) VALUES (2, 1221)
GO
