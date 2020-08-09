package cn.jiyun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.jiyun.pojo.City;
import cn.jiyun.pojo.Dept;
import cn.jiyun.pojo.Emp;
import cn.jiyun.pojo.EmpVo;

public interface EmpMapper {
	

	public void deleteEmp(Integer[] eids);
	
	//想实现添加功能应该有几个方法？分别是干什么的方法？
	@Select("select * from dept")
	public List<Dept> queryDept();
	
	@Select("select * from city where fid=#{fid}")
	public List<City> queryCity(Integer fid);
	
	@Select("select * from city")
	public List<City> queryAllCity();
	
	@Insert("insert into emp(ename,sex,hobby,img,birthday,did,addr,shengid,shiid,quid) values(#{ename},#{sex},#{hobby},#{img},#{birthday},#{did},#{addr},#{shengid},#{shiid},#{quid})")
	public void addEmp(Emp emp);

	@Select("select * from emp where eid=#{eid}")
	public Emp queryByEid(Integer eid);

	@Update("update emp set ename=#{ename},sex=#{sex},hobby=#{hobby},img=#{img},birthday=#{birthday},did=#{did},shengid=#{shengid},shiid=#{shiid},quid=#{quid} where eid=#{eid}")
	public void updateEmp(Emp emp);
	
	@Delete("delete from emp where eid=#{eid}")
	public void delete(Integer eid);
	
	
	public List<EmpVo> queryEmp(EmpVo e);
	

}
