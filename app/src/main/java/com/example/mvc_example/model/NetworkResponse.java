package com.example.mvc_example.model;

public class NetworkResponse {

	private boolean isSuccess;
	private String message;
	private Exception exception;

	public NetworkResponse() {
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
}
