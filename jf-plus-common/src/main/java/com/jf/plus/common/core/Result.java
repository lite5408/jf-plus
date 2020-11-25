package com.jf.plus.common.core;

import java.io.Serializable;

/**
 * @description：操作结果集
 * @author：tangyh
 * @date：2015/10/1 14:51
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 5576237395711742681L;

	private boolean success = false;

	private String msg = "";

	private Object obj = null;

	private Integer code = null;

	public static Result newInstance(){
		return new Result();
	}

	public static Result newExceptionInstance() {
		return new Result(false, Constants.EXCEPTION_MSG, null, ResultCode.SERVICE_EXCEPTION);
	}

	public Result() {
		super();
	}

	public Result(boolean success, String msg, Object obj, Integer code) {
		super();
		this.success = success;
		this.msg = msg;
		this.obj = obj;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		if(code == ResultCode.SUCCESS){
			setSuccess(Boolean.TRUE);
		}else{
			setSuccess(Boolean.FALSE);
		}
		this.code = code;
	}


}
