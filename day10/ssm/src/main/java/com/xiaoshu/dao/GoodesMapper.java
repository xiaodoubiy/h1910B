package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Goodes;
import com.xiaoshu.entity.GoodesExample;
import com.xiaoshu.entity.GoodsVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodesMapper extends BaseMapper<Goodes> {

	

	List<GoodsVo> findUserPage(GoodsVo goodsVo);
	List<GoodsVo> echartsFindAll();
}