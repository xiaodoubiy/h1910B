package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;


@Service
public class ContentServcie {

	@Autowired
	private ContentMapper contentMapper;

	public PageInfo<ContentVo> findUserPage(ContentVo contentVo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<ContentVo> list = contentMapper.findUserPage(contentVo);
		return new PageInfo<>(list);
	}

	public Content existContentWithContentName(String contenttitle) {
		// TODO Auto-generated method stub
		Content content = new Content();
		content.setContenttitle(contenttitle);
		return contentMapper.selectOne(content);
	}

	public void updateContent(Content content) {
		// TODO Auto-generated method stub
	     contentMapper.updateByPrimaryKey(content);
	}

	public void addContent(Content content) {
		// TODO Auto-generated method stub
		contentMapper.insert(content);
	}

	public void deleteContent(int parseInt) {
		// TODO Auto-generated method stub
		contentMapper.deleteByPrimaryKey(parseInt);
	}

//	public List<ContentVo> echartsAll() {
//		// TODO Auto-generated method stub
//		return contentMapper.echartsAll();
//	} 
}
