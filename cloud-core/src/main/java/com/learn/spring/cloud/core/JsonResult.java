package com.learn.spring.cloud.core;



import java.io.Serializable;


/**
 * @author bill
 * @version 1.0
 * @date 2020/4/20 21:47
 */
public class JsonResult<T> implements Serializable {
	private static final long serialVersionUID = 1091032671849867704L;

	public static final String CODE = "code";
	public static final String DATA = "data";
	public static final String MSG = "msg";

	private String code;
	private String msg;
	private T data;

	private static <T> JsonResult<T> create() {
		return new JsonResult<>();
	}

	public static <T> JsonResult<T> success() {
		JsonResult<T> result = JsonResult.create();
		return result.setCode(HttpConstants.CODE_SUCCESS);
	}

	public static <T> JsonResult<T> success(T data) {
		JsonResult<T> result = JsonResult.success();
		result.setData(data);
		return result;
	}

	public static <T> JsonResult<T> success(T data, String msg) {
		JsonResult<T> result = JsonResult.success(data);
		result.setCode(msg);
		return result;
	}



	public static <T> JsonResult<T> failed() {
		JsonResult<T> result = JsonResult.create();
		result.setCode(HttpConstants.CODE_FAILED);
		return result;
	}

	public static <T> JsonResult<T> failed(String msg){
		JsonResult<T> result = JsonResult.failed();
		result.setMsg(msg);
		return result;
	}

	public static <T> JsonResult<T> failed(String msg, String code, T data) {
		JsonResult<T> result = JsonResult.create();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}


	public T getDataWithEx() {
		if (this.code == null) {
			throw new GlobalException("JsonResult is null");
		} else if (!"0000".equals(this.code)) {
			throw new GlobalException(this.code, this.msg);
		} else {
			return this.data;
		}
	}

	public String getCode() {
		return code;
	}

	public T getData() {
		return data;
	}


	public String getMsg() {
		return msg;
	}

	public JsonResult<T> setCode(String code) {
		this.code = code;
		return this;
	}

	public JsonResult<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public JsonResult<T> setData(T data) {
		this.data = data;
		return this;
	}

    @Override
    public String toString() {
        return "JsonResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
