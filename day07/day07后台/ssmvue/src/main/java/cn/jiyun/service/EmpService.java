package cn.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jiyun.mapper.EmpMapper;
import cn.jiyun.pojo.City;
import cn.jiyun.pojo.Dept;
import cn.jiyun.pojo.Emp;
import cn.jiyun.pojo.EmpVo;

@Service
@Transactional
public class EmpService {
	@Autowired
	private EmpMapper em;
	
	public List<EmpVo> queryAllEmp(EmpVo ev){
		return em.queryEmp(ev);
	}

	public void deleteEmp(Integer[] eids) {
		em.deleteEmp(eids);
		
	}
	
	public List<Dept> queryDept(){
		return em.queryDept();
	}
	
	public List<City> queryCity(Integer fid){
		return em.queryCity(fid);
	}
	
	public void addEmp(Emp emp) {
		em.addEmp(emp);
	}

	public Emp queryByEid(Integer eid) {
		// TODO Auto-generated method stub
		return em.queryByEid(eid);
	}
	
	public List<City> queryAllCity(){
		return em.queryAllCity();
	}

	public void updateEmp(Emp emp) {
		em.updateEmp(emp);
		
	}
	public void delete(Integer eid) {
		em.delete(eid);
	}
}
