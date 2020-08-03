package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.entity.Device;

@Service
public class DeviceService {

	@Autowired
	private DeviceMapper deviceMapper;

	public List<Device> findByAll() {
		// TODO Auto-generated method stub
		return deviceMapper.findByAll();
	}
}
