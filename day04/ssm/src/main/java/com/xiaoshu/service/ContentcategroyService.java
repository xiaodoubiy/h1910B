package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.ContentcategoryMapper;
import com.xiaoshu.entity.Contentcategory;

@Service
public class ContentcategroyService {

	@Autowired
	private ContentcategoryMapper contentcategoryMapper;

	public List<Contentcategory> findByAll() {
		// TODO Auto-generated method stub
		return contentcategoryMapper.selectAll();
	}

	public Contentcategory findByName(String categoryname) {
		// TODO Auto-generated method stub
		Contentcategory contentcategory = new Contentcategory();
		contentcategory.setCategoryname(categoryname);
		return contentcategoryMapper.selectOne(contentcategory);
	}
}
