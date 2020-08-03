package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentExample;
import com.xiaoshu.entity.ContentVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentMapper extends BaseMapper<Content> {

	List<ContentVo> findUserPage(ContentVo contentVo);

	List<ContentVo> echartsAll();
  
}