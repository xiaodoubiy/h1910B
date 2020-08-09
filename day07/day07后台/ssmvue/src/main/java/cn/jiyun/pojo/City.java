package cn.jiyun.pojo;

import java.io.Serializable;

public class City implements Serializable{
	private Integer cid;
	private String cname;
	private Integer fid;
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}

}
