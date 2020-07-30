package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceExample;
import com.xiaoshu.entity.DeviceExample.Criteria;

@Service
public class DeviceService {

	
	@Autowired
	DeviceMapper deviceMapper;
	
	@Autowired
	UserMapper userMapper;

	public List<Device> findDevice() {
		// TODO Auto-generated method stub
		return deviceMapper.selectAll();
	}
	
	
	public PageInfo<Device> findDevicePage(Device device, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Device> userList = deviceMapper.findAll(device);
		PageInfo<Device> pageInfo = new PageInfo<Device>(userList);
		return pageInfo;
	}


	// 修改
	public void updateDevice(Device t) throws Exception {
		deviceMapper.updateByPrimaryKeySelective(t);
	};


	public void addDevice(Device t) throws Exception {
		deviceMapper.insert(t);
	}


	// 删除
	public void deleteDevice(Integer id) throws Exception {
		deviceMapper.deleteByPrimaryKey(id);
	};


}
