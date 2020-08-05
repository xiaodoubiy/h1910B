package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.DevicetypeMapper;
import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.DevicetypeVo;
import com.xiaoshu.entity.User;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class DevicetypeService {

	@Autowired
	private DevicetypeMapper devicetypeMapper;
	@Autowired
	private JedisPool jedisPool;

	public PageInfo<DevicetypeVo> findUserPage(DevicetypeVo devicetypeVo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<DevicetypeVo>list=devicetypeMapper.findUserPage(devicetypeVo);
		return new PageInfo<>(list);
	}



	public void updateDevice(Devicetype devicetype) {
		// TODO Auto-generated method stub
		devicetypeMapper.updateDevice(devicetype);
	}

	public void addDevice(Devicetype devicetype) {
		Jedis jedis = jedisPool.getResource();
		jedis.set(devicetype.getDevicename(),devicetype.getPrice().toString());
		
		// TODO Auto-generated method stub
		devicetypeMapper.addDevice(devicetype);
	}

	public void deleteDevice(int parseInt) {
		// TODO Auto-generated method stub
		devicetypeMapper.deleteDevice(parseInt);
	}



	public Devicetype existdeviceWithdeviceName(String devicename) {
		// TODO Auto-generated method stub
		return devicetypeMapper.existdeviceWithdeviceName(devicename);
	}



	public List<DevicetypeVo> echartsAll() {
		// TODO Auto-generated method stub
		return devicetypeMapper.echartsAll();
	}



	public List<DevicetypeVo> findAll(DevicetypeVo devicetypeVo) {
		// TODO Auto-generated method stub
		return devicetypeMapper.findUserPage(devicetypeVo);
	}



	
}
