package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.DevicetypeVo;
import com.xiaoshu.entity.User;

public interface DevicetypeMapper {

	List<DevicetypeVo> findUserPage(Devicetype devicetype);



	void updateDevice(Devicetype devicetype);

	void addDevice(Devicetype devicetype);

	void deleteDevice(int parseInt);



	Devicetype existdeviceWithdeviceName(String devicename);



	List<DevicetypeVo> echartsAll();

}
