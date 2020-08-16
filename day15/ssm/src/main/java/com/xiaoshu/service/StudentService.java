package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;

@Service
public class StudentService {

	@Autowired
	private StudentMapper studentMapper;

	public PageInfo<StudentVo> findUserPage(StudentVo studentVo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<StudentVo>list=studentMapper.findUserPage(studentVo);
		return new PageInfo<>(list);
	}

	public Student existStudentName(String name) {
		// TODO Auto-generated method stub
		
		Student parm=new Student();
		parm.setName(name);
		
		return studentMapper.selectOne(parm);
	}

	public void updateStudent(Student student) {
		// TODO Auto-generated method stub
		studentMapper.updateByPrimaryKeySelective(student);
	}

	public void addStudent(Student student) {
		// TODO Auto-generated method stub
		studentMapper.insert(student);
		
		
	}

	public List<StudentVo> echartsAll() {
		// TODO Auto-generated method stub
		return studentMapper.echartsAll();
	}

	public List<StudentVo> findAll(StudentVo studentVo) {
		// TODO Auto-generated method stub
		return studentMapper.findUserPage(studentVo);
	}

}
