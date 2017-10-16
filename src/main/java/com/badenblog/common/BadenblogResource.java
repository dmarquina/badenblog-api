package com.badenblog.common;

public class BadenblogResource<T> {
	private Long timestamp;
	private Integer status;
	private String error;
	private String message;
	private boolean managed;
	private T content;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public T getContent() {
		return content;
	}

	public void setContent(final T content) {
		this.content = content;
	}

	public boolean isManaged() {
		return managed;
	}

	public void setManaged(final boolean managed) {
		this.managed = managed;
	}
}
