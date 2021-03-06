package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PersonVo extends Person{

	private String cname;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date begin;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date end;
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
}
