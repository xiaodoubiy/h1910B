package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonVo;

@Service
public class PersonService {

@Autowired
private PersonMapper personMapper;

public PageInfo<PersonVo> findUserPage(PersonVo personVo, Integer pageNum, Integer pageSize, String ordername,
		String order) {
	// TODO Auto-generated method stub
	PageHelper.startPage(pageNum, pageSize);
	List<PersonVo>list =personMapper.findUserPage(personVo);
	return new PageInfo<>(list);
}

public void updatePerson(Person person) {
	// TODO Auto-generated method stub
	personMapper.updateByPrimaryKeySelective(person);
}

public void addPerson(Person person) {
	// TODO Auto-generated method stub
	personMapper.insert(person);
}

public void deletePerson(int parseInt) {
	// TODO Auto-generated method stub
	personMapper.deleteByPrimaryKey(parseInt);
}

}
