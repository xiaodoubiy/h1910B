package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StuMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Stu;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class StuService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	StuMapper stuMapper;
	
	@Autowired
	MajorMapper majorMapper;

	public List<Major> findMajor() {
		// TODO Auto-generated method stub
		return majorMapper.selectAll();
	}

	public PageInfo<Stu> findStuPage(StuVo stu, Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);
		List<Stu> userList = stuMapper.findAll(stu);
		PageInfo<Stu> pageInfo = new PageInfo<Stu>(userList);
		return pageInfo;
	}

	public void updateStu(Stu t) throws Exception {
		stuMapper.updateByPrimaryKey(t);
	};

	public void addStu(Stu t) throws Exception {
		stuMapper.insert(t);
	}

	
	public void deleteStu(Integer id) throws Exception {
		stuMapper.deleteByPrimaryKey(id);
	};

}
