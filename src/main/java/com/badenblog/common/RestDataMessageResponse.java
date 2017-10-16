package com.badenblog.common;

public class RestDataMessageResponse {
	private String message;
	private Boolean result;

	public RestDataMessageResponse() {
	}

	public RestDataMessageResponse(String message, boolean result) {
		this.message = message;
		this.result = result;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}
}