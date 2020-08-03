package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.Contentcategory;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.ContentServcie;
import com.xiaoshu.service.ContentcategroyService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("content")
public class ContentController extends LogController{
	static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private OperationService operationService;
	@Autowired
	private ContentcategroyService contentcategroyService;
	@Autowired
	private ContentServcie contentServcie;
	
	@RequestMapping("contentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Contentcategory> cList = contentcategroyService.findByAll();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("cList", cList);
		return "content";
	}
	
	@RequestMapping(value="contentList",method=RequestMethod.POST)
	public void userList(HttpServletRequest request,HttpServletResponse response,String offset,String limit,ContentVo contentVo) throws Exception{
		try {
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<ContentVo> page= contentServcie.findUserPage(contentVo,pageNum,pageSize,ordername,order);
			
		
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
		@RequestMapping("reserveContent")
		public void reserveUser(HttpServletRequest request,User user,HttpServletResponse response,Content content){
			Integer contentid = content.getContentid();
			content.setCreatetime(new Date());
			JSONObject result=new JSONObject();
			try {
				Content contenttitle = contentServcie.existContentWithContentName(content.getContenttitle());
				if (contentid != null) {   // userId不为空 说明是修改
					if(contenttitle == null || (contenttitle!=null&&contenttitle.getContentid().compareTo(contentid)==0)){
						content.setContentid(contentid);
						contentServcie.updateContent(content);
						result.put("success", true);
					}else{
						result.put("success", true);
						result.put("errorMsg", "该用户名被使用");
					}
					
				}else {   // 添加
					//contenttitle==null
					if(contentServcie.existContentWithContentName(content.getContenttitle())==null){  // 没有重复可以添加
						contentServcie.addContent(content);
						result.put("success", true);
					} else {
						result.put("success", true);
						result.put("errorMsg", "该用户名被使用");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存用户信息错误",e);
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
	
		@RequestMapping("deleteContent")
		public void delUser(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				String[] ids=request.getParameter("ids").split(",");
				for (String id : ids) {
					contentServcie.deleteContent(Integer.parseInt(id));
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
		
//		@RequestMapping("deleteContent")
//		public void deleteContent(HttpServletRequest request,HttpServletResponse response){
//			JSONObject result=new JSONObject();
//			try {
//				
//					List<ContentVo>list= contentServcie.echartsAll();
//				
//				result.put("success", true);
//				result.put("data", list);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("删除用户信息错误",e);
//				result.put("errorMsg", "对不起，删除失败");
//			}
//			WriterUtil.write(response, result.toString());
//		}

		@RequestMapping("importContent")
		public void importContent(HttpServletRequest request,HttpServletResponse response,MultipartFile importFile){
			JSONObject result=new JSONObject();
			try {
				     Workbook workbook = WorkbookFactory.create(importFile.getInputStream());
				     Sheet sheet = workbook.getSheetAt(0);
				     int lastRowNum = sheet.getLastRowNum();
				     for (int i = 0; i < lastRowNum; i++) {
				    	 System.out.println(lastRowNum);
						Row row = sheet.getRow(i+1);
						String contenttitle = row.getCell(0).toString();
						String categoryname = row.getCell(1).toString();
						String contenturl = row.getCell(2).toString();
						String picpath = row.getCell(3).toString();
						String price = row.getCell(4).toString();
						String status = row.getCell(5).toString();
						Date createtime = row.getCell(6).getDateCellValue();
						Contentcategory contentcategory=contentcategroyService.findByName(categoryname);
						Content content=new Content();
						content.setContenttitle(contenttitle);
						content.setContentcategoryid(contentcategory.getContentcategoryid());
						content.setContenturl(contenturl);
						content.setPicpath(picpath);
						content.setPrice(Double.parseDouble(price));
						content.setStatus(status);
						content.setCreatetime(new Date());
						contentServcie.addContent(content);
						
					}
					
				
				result.put("success", true);
			
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，导入失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
}
