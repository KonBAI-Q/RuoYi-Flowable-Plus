INSERT [sys_dict_type] ([dict_id], [dict_name], [dict_type], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (11, N'流程状态', N'wf_process_status', N'0', N'admin', getdate(), N'', NULL, N'工作流程状态')
GO

INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (30, 1, N'进行中', N'running', N'wf_process_status', N'', N'primary', N'N', N'0', N'admin', getdate(), N'', NULL, N'进行中状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (31, 2, N'已终止', N'terminated', N'wf_process_status', N'', N'danger', N'N', N'0', N'admin', getdate(), N'', NULL, N'已终止状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (32, 3, N'已完成', N'completed', N'wf_process_status', N'', N'success', N'N', N'0', N'admin', getdate(), N'', NULL, N'已完成状态')
GO
INSERT [sys_dict_data] ([dict_code], [dict_sort], [dict_label], [dict_value], [dict_type], [css_class], [list_class], [is_default], [status], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (33, 4, N'已取消', N'canceled', N'wf_process_status', N'', N'warning', N'N', N'0', N'admin', getdate(), N'', NULL, N'已取消状态')
GO
