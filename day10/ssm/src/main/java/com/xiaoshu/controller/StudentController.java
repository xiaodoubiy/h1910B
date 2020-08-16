package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.CourseService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("stu")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("stuIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("cList", courseService.findAll());
		return "stu";
	}
	
	
	@RequestMapping(value="stuList",method=RequestMethod.POST)
	public void userList(HttpServletRequest request,HttpServletResponse response,String offset,String limit,StudentVo studentVo) throws Exception{
		try {
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
		
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<StudentVo> page= studentService.findStudentPage(studentVo,pageNum,pageSize,ordername,order);
			
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
	@RequestMapping("reserveStudent")
	public void reserveUser(HttpServletRequest request,User user,HttpServletResponse response,Student student){
		Integer id = student.getId();
		JSONObject result=new JSONObject();
		try {
			Student stuname=studentService.findByName(student.getName());
			
			if (id != null||(stuname!=null && stuname.getName().equals(id))){   // userId不为空 说明是修改
				
					student.setId(id);
					studentService.updateStudent(student);
					result.put("success", true);
				
				
			}else {   // 添加
				if(stuname==null){  // 没有重复可以添加
					studentService.addStudent(student);
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
	// 新增或修改
	@RequestMapping("reserveKc")
	public void reserveKc(HttpServletRequest request,User user,HttpServletResponse response,Course course){
		
		JSONObject result=new JSONObject();
		try {
			
					courseService.addCourse(course);
					result.put("success", true);
				
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("echartsStudent")
	public void echartsStudent(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			
				List<StudentVo> list=studentService.echartsgindAll();
			
			result.put("success", true);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	@RequestMapping("studentDown")
	public void studentDown(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("操作记录备份");
		HSSFRow rowFirst = sheet.createRow(0);
		String [] handers={"课程编号","课程名","课程编号","创建课程时间"};
		for (int i = 0; i < handers.length; i++) {
			rowFirst.createCell(i).setCellValue(handers[i]);
		}
		List<Course>list=courseService.findCourse(new Course());
		for (int i = 0; i < list.size(); i++) {
			Course course=list.get(i);
			HSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(course.getCourseid());
			row.createCell(1).setCellValue(course.getCname());
			row.createCell(2).setCellValue(course.getCode());
			row.createCell(3).setCellValue(TimeUtil.formatTime(course.getCreatetime(), "yyyy-MM-dd"));
			
		}
		//写出文件（path为文件路径含文件名）
		OutputStream os;
		File file = new File("D:\\aaa\\导出课程.xls");
		
		if (!file.exists()){//若此目录不存在，则创建之  
			file.createNewFile();  
			logger.debug("创建文件夹路径为："+ file.getPath());  
        } 
		os = new FileOutputStream(file);
		wb.write(os);
		os.close();
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
}
