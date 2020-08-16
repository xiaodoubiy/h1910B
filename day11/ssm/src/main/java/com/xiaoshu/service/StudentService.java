package com.xiaoshu.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;

import redis.clients.jedis.Jedis;

@Service
public class StudentService {

	@Autowired
	private StudentMapper studentMapper;

	public PageInfo<StudentVo> findStudentPage(StudentVo studentVo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<StudentVo>list=studentMapper.findStudentPage(studentVo);
		return new PageInfo<>(list);
	}

	public Student findByName(String name) {
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
	    Jedis jedis=new Jedis("127.0.0.1",6379);
	    Student pram = new Student();
		pram.setName(student.getName());
		Student student2 = studentMapper.selectOne(pram);
		jedis.hset("信息", student2.getName(),student2.getId()+"");
	}

	public List<StudentVo> echartsgindAll() {
		// TODO Auto-generated method stub
		return studentMapper.echartsgindAll();
	}




}
