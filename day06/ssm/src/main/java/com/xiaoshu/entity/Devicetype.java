package com.xiaoshu.entity;

import java.util.Date;

import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name="tb_device")
public class Devicetype {

	private Integer id;
	private String devicename;
	private String typeid;
	private String deviceram;
	private String color;
	private Double price;
	private String status;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createtime;
	
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	
	public String getDeviceram() {
		return deviceram;
	}
	public void setDeviceram(String deviceram) {
		this.deviceram = deviceram;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "Devicetype [id=" + id + ", devicename=" + devicename + ", typeid=" + typeid + ", deviceram=" + deviceram
				+ ", color=" + color + ", price=" + price + ", status=" + status + ", createtime=" + createtime + "]";
	}
	
	
	
}
