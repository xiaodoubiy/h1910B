package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.CourseMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

import redis.clients.jedis.Jedis;

@Service
public class CourseService {

	@Autowired
	private CourseMapper courseMapper;

	public Object findAll() {
		// TODO Auto-generated method stub
		return courseMapper.selectAll();
	}

	public Course findCourseCode(String code) {
		// TODO Auto-generated method stub
		Course parm = new Course();
		parm.setCode(code);
		
		return courseMapper.selectOne(parm);
	}

	public void addCourse(Course course) {
		// TODO Auto-generated method stub
		courseMapper.insert(course);
		Jedis jedis  = new Jedis("127.0.0.1",6379);
		Course pram=new Course();
		pram.setName(course.getName());
		Course course2 = courseMapper.selectOne(pram);
		jedis.hset("学生信息", course2.getId()+"", JSONObject.toJSONString(course2));
		
		
	}

	public List<Course> findCou(Course course) {
		// TODO Auto-generated method stub
		return courseMapper.selectAll();
	}


}
