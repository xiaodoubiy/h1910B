package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.StuMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.Stu;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class StuService {


	@Autowired
	private StuMapper stuMapper;

	public PageInfo<StuVo> findStuPage(StuVo stuVo, Integer pageNum, Integer pageSize, String ordername, String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<StuVo>list=stuMapper.findStuPage(stuVo);
		return new PageInfo<>(list);
	}


	public void updateStu(Stu stu) {
		// TODO Auto-generated method stub
		stuMapper.updateByPrimaryKeySelective(stu);
	}

	public void addStu(Stu stu) {
		// TODO Auto-generated method stub
		stuMapper.insert(stu);
	}

	public void deleteStu(int parseInt) {
		// TODO Auto-generated method stub
		stuMapper.deleteByPrimaryKey(parseInt);
	}

	public List<StuVo> findEcharts() {
		// TODO Auto-generated method stub
		return stuMapper.findEcharts();
	}

	public Stu StuName(String name) {
		// TODO Auto-generated method stub
		Stu stu = new Stu();
		stu.setName(name);
		Stu stu2 = stuMapper.selectOne(stu);
		
		return stu2;
	}



}
