package com.jd.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yongkang.zhang
 * @date 2020/11/17
 */
@Data
public class Response<T> implements Serializable {

	private int code;
	private String msg;
	private String errorDesc;
	private T data;
	private String responseId;

	public Response() {
	}

	public Response(int code, String msg, T data) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}



	public Response(int code, String msg, String errorDesc, T data) {
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.errorDesc = errorDesc;
	}

	public Response(int code, String msg, String errorDesc, String responseId, T data) {
		this.code = code;
		this.msg = msg;
		this.errorDesc = errorDesc;
		this.responseId = responseId;
		this.data = data;
	}

	public static <R> Response<R> from(int code, String msg, String errorDesc, R data) {
		return new Response(code, msg, errorDesc, data);
	}

	public static <R> Response<R> from(int code, String msg, R data) {
		return new Response(code, msg, data);
	}

	public static <R> Response<R> from(int code, String msg, String errorDesc, String responseId, R data) {
		return new Response(code, msg, errorDesc, responseId, data);
	}

	public static Response from(int code, String msg) {
		return new Response(code, msg, (Object)null);
	}

	public static Response from(int code, String msg, String errorDesc) {
		return new Response(code, msg, errorDesc, (Object)null);
	}

	public static <R> Response<R> from(int code, R data) {
		return new Response(code,"SUCCESS", data);
	}

    public static <T> Response<T> success(T data) {
        return new Response(0, "success", "", "", data);
    }

}
