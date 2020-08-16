package com.xiaoshu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.GoodesMapper;
import com.xiaoshu.dao.TypesMapper;
import com.xiaoshu.entity.Types;

import redis.clients.jedis.Jedis;

@Service
public class TypesService {
@Autowired
private TypesMapper typesMapper;

public Object findAll() {
	// TODO Auto-generated method stub
	return typesMapper.selectAll();
}

public void addTypes(Types types) {
	// TODO Auto-generated method stub
	typesMapper.insert(types);
	Jedis jedis = new Jedis("127.0.0.1",6379);
	Types parm = new Types();
	parm.setTypename(types.getTypename());
	Types type = typesMapper.selectOne(parm);
	jedis.hset("商品分类", type.getTypename(), type.getTypeid()+"");
	
}



}
