package com.project.model;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {

	private String userId;
	private String status;
	private Date fromDate;
	private Date toDate;
	private String date;
	private int type;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	public OrderDto(String userId, String status, Date fromDate, Date toDate, String date, int type) {
		super();
		this.userId = userId;
		this.status = status;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.date = date;
		this.type = type;
	}
	
	public OrderDto() {
		super();
	}
	
	
}
