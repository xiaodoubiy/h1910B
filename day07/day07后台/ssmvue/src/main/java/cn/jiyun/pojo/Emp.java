package cn.jiyun.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Emp implements Serializable{
	private Integer eid;
	private String ename;
	private String sex;
	private String hobby;
	private String img;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	private Integer did;
	private String addr;
	private Integer shengid;
	private Integer shiid;
	private Integer quid;
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Integer getShengid() {
		return shengid;
	}
	public void setShengid(Integer shengid) {
		this.shengid = shengid;
	}
	public Integer getShiid() {
		return shiid;
	}
	public void setShiid(Integer shiid) {
		this.shiid = shiid;
	}
	public Integer getQuid() {
		return quid;
	}
	public void setQuid(Integer quid) {
		this.quid = quid;
	}
	

}
