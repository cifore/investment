package com.csi.sbs.investment.business.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Response Model")
public class ResultUtil<T> {

	@ApiModelProperty(notes="return code",example="1")
	private String code;

	@ApiModelProperty(notes="return msg",example="")
	private String msg;

	@ApiModelProperty(notes="return data")
	private T data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
