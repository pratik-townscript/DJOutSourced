package com.townscript.demo.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final String API_RESPONSE = "apiResponse";
    Object result;
    long code;
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	
	public static APIResponse toOkResponse(Object data) {
        return toAPIResponse(data, 200);
    }
	
	public static APIResponse toErrorResponse(Object data) {
        return toAPIResponse(data, 2001);
    }
	
	public static APIResponse toAPIResponse(Object data, long code) {
        APIResponse response = new APIResponse();
        response.setResult(data);
        response.setCode(code);
        return response;
    }
}
