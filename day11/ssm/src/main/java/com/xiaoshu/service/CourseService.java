package com.xiaoshu.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoshu.dao.CourseMapper;
import com.xiaoshu.entity.Course;

@Service
public class CourseService {


	@Autowired
	private CourseMapper courseMapper;

	public Object findAll() {
		// TODO Auto-generated method stub
		return courseMapper.selectAll();
	}

	public void addCourse(Course course) {
		// TODO Auto-generated method stub
		courseMapper.insert(course);
	}

	public List<Course> findCourse(Course course) {
		// TODO Auto-generated method stub
		return courseMapper.selectAll();
	}

}
