package com.ruoyi.common.core.domain;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 操作消息提醒
 *
 * @author Lion Li
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("请求响应对象")
public class R<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	@ApiModelProperty("消息状态码")
	private int code;

	/**
	 * 返回内容
	 */
	@ApiModelProperty("消息内容")
	private String msg;

	/**
	 * 数据对象
	 */
	@ApiModelProperty("数据对象")
	private T data;

	/**
	 * 初始化一个新创建的 AjaxResult 对象
	 *
	 * @param code 状态码
	 * @param msg  返回内容
	 */
	public R(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 返回成功消息
	 *
	 * @return 成功消息
	 */
	public static R<Void> success() {
		return R.success("操作成功");
	}

	/**
	 * 返回成功数据
	 *
	 * @return 成功消息
	 */
	public static <T> R<T> success(T data) {
		return R.success("操作成功", data);
	}

	/**
	 * 返回成功消息
	 *
	 * @param msg 返回内容
	 * @return 成功消息
	 */
	public static R<Void> success(String msg) {
		return R.success(msg, null);
	}

	/**
	 * 返回成功消息
	 *
	 * @param msg  返回内容
	 * @param data 数据对象
	 * @return 成功消息
	 */
	public static <T> R<T> success(String msg, T data) {
		return new R<>(HttpStatus.HTTP_OK, msg, data);
	}

	/**
	 * 返回错误消息
	 *
	 * @return
	 */
	public static R<Void> error() {
		return R.error("操作失败");
	}

	/**
	 * 返回错误消息
	 *
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static R<Void> error(String msg) {
		return R.error(msg, null);
	}

	/**
	 * 返回错误消息
	 *
	 * @param msg  返回内容
	 * @param data 数据对象
	 * @return 警告消息
	 */
	public static <T> R<T> error(String msg, T data) {
		return new R<>(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
	}

	/**
	 * 返回错误消息
	 *
	 * @param code 状态码
	 * @param msg  返回内容
	 * @return 警告消息
	 */
	public static R<Void> error(int code, String msg) {
		return new R<>(code, msg, null);
	}

}
