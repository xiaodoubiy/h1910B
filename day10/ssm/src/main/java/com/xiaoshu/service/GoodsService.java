package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.GoodesMapper;
import com.xiaoshu.entity.Goodes;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Types;

import redis.clients.jedis.Jedis;

@Service
public class GoodsService {
@Autowired
private GoodesMapper goodesMapper;



public PageInfo<GoodsVo> findUserPage(GoodsVo goodsVo, Integer pageNum, Integer pageSize, String ordername,
		String order) {
	// TODO Auto-generated method stub
	PageHelper.startPage(pageNum, pageSize);
	List<GoodsVo>list=goodesMapper.findUserPage(goodsVo);
	return new PageInfo<>(list);
}
public List<GoodsVo> echartsFindAll() {
// TODO Auto-generated method stub
return goodesMapper.echartsFindAll();
}



public void updateGoods(Goodes goodes) {
	// TODO Auto-generated method stub
	goodesMapper.updateByPrimaryKeySelective(goodes);
}



public void addGoods(Goodes goodes) {
	// TODO Auto-generated method stub
	goodesMapper.insert(goodes);
	
}




}
