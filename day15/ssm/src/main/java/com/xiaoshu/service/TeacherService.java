package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.entity.Teacher;


@Service
public class TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination queueTextDestination; 
    
	public Object findAllTeacher() {
		// TODO Auto-generated method stub
		return teacherMapper.selectAll();
	}

	public void addTc(Teacher teacher) {
		// TODO Auto-generated method stub
		teacherMapper.insert(teacher);
		jmsTemplate.convertAndSend(queueTextDestination, JSONObject.toJSONString(teacher));
		System.out.println(JSONObject.toJSONString(teacher));
	}



	public Teacher findTeacherName(String tname) {
		// TODO Auto-generated method stub
		Teacher parm = new Teacher();
		parm.setTname(tname);
		return teacherMapper.selectOne(parm);
	}

}
