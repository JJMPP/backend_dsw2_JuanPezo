package com.proyecto.entity;

import java.util.Date;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public class HttpResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "America/Lima")
	private Date timeStamp;
	
	private int httpStatusCode; //200, 201, 400, 500
	private HttpStatus httpStatus;
	private String reason;
	private String message;
	
	
	public HttpResponse() {
		super();
	}

	public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
		super();
		this.timeStamp = new Date();
		this.httpStatusCode = httpStatusCode;
		this.httpStatus = httpStatus;
		this.reason = reason;
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
