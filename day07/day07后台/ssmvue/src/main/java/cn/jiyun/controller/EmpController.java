package cn.jiyun.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.jiyun.pojo.City;
import cn.jiyun.pojo.Dept;
import cn.jiyun.pojo.Emp;
import cn.jiyun.pojo.EmpVo;
import cn.jiyun.service.EmpService;

@CrossOrigin(origins = { "http://localhost:63342" })
@RestController
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	private EmpService es;

	@RequestMapping("/queryemp")
	public List<EmpVo> queryemp(EmpVo ev) {
		return es.queryAllEmp(ev);
	}

	// 分页
	@RequestMapping("/queryempfy")
	public PageInfo<EmpVo> queryAllEmp(@RequestParam(defaultValue = "1", required = true) Integer pageNum,
			@RequestBody EmpVo ev) {
		PageHelper.startPage(pageNum, 2);
		List<EmpVo> el = es.queryAllEmp(ev);
		PageInfo<EmpVo> pageInfo = new PageInfo<EmpVo>(el);
		return pageInfo;
	}

	// 新增
	@RequestMapping("/add")
	public void addEmp(@RequestBody Emp emp) {
		es.addEmp(emp);
	}

	// 新增
	@RequestMapping("/addemp")
	public void addEmp(Emp emp, MultipartFile pic) throws Exception {
		if (pic.getSize() > 0) {
			String fileName = pic.getOriginalFilename();
			pic.transferTo(new File("f:/pic/" + fileName));
			emp.setImg(fileName);
		} else {
			emp.setImg(null);
		}
		es.addEmp(emp);
	}

	// 修改
	@RequestMapping("/updateemp")
	public void updateEmp(Emp emp, MultipartFile pic) throws Exception {
		if (pic!=null&&pic.getSize() > 0) {
			String fileName = pic.getOriginalFilename();
			pic.transferTo(new File("f:/pic/" + fileName));
			emp.setImg(fileName);
		}
		es.updateEmp(emp);
	}

	// 查询所有部门
	@RequestMapping("/querydept")
	public List<Dept> queryDept() {
		return es.queryDept();
	}

	@RequestMapping("/queryByEid")
	public Emp queryByEid(Integer eid) {
		return es.queryByEid(eid);
	}

	@RequestMapping("/delete")
	public void delete(Integer eid) {
		es.delete(eid);
	}
	
	@RequestMapping("/deltesome")
	public void deleteSome(@RequestBody Integer[] eids) {
		es.deleteEmp(eids);
	}
	
	

}
