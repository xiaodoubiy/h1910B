package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.DevicetypeVo;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.DeviceService;
import com.xiaoshu.service.DevicetypeService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("device")
public class DevicetypeController extends LogController{
	static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private OperationService operationService;
	@Autowired
	private DevicetypeService devicetypeService;
	@Autowired
	private DeviceService deviceService;   
	
	@RequestMapping("deviceIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Device> dList = deviceService.findByAll();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("dList", dList);
		return "device";
	}
	
	@RequestMapping(value="deviceList",method=RequestMethod.POST)
	public void userList(HttpServletRequest request,HttpServletResponse response,String offset,String limit,DevicetypeVo devicetypeVo) throws Exception{
		try {
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<DevicetypeVo> page= devicetypeService.findUserPage(devicetypeVo,pageNum,pageSize,ordername,order);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",page.getTotal() );
			jsonObj.put("rows", page.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	// 新增或修改
		@RequestMapping("reserveDevice")
		public void reserveUser(HttpServletRequest request,HttpServletResponse response,Devicetype devicetype){
			Integer id = devicetype.getId();
			
			JSONObject result=new JSONObject();
			devicetype.setCreatetime(new Date());
			try {
			
				if (id != null) {   // userId不为空 说明是修改
					
				
						devicetype.setId(id);
						devicetypeService.updateDevice(devicetype);
						result.put("success", true);
					
				}else {   // 添加
					
						devicetypeService.addDevice(devicetype);
						result.put("success", true);
					} 
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存用户信息错误",e);
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		
		@RequestMapping("deleteDevice")
		public void delUser(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				String[] ids=request.getParameter("ids").split(",");
				for (String id : ids) {
					devicetypeService.deleteDevice(Integer.parseInt(id));
				}
				result.put("success", true);
				result.put("delNums", ids.length);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		@RequestMapping("echartsContent")
		public void echartsContent(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				
					List<DevicetypeVo>list= devicetypeService.echartsAll();
				
				result.put("success", true);
				result.put("data", list);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}

}
