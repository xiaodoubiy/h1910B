package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.TeacherService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("stu")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	@RequestMapping("stuIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("tList", teacherService.findAllTeacher());
		return "stu";
	}
	
	
	@RequestMapping(value="stuList",method=RequestMethod.POST)
	public void userList(HttpServletRequest request,HttpServletResponse response,String offset,String limit,StudentVo studentVo) throws Exception{
		try {
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<StudentVo> page= studentService.findUserPage(studentVo,pageNum,pageSize,ordername,order);
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
	@RequestMapping("reserveStu")
	public void reserveUser(HttpServletRequest request,User user,HttpServletResponse response,Student student){
		Integer id = student.getId();
		student.setCreatetime(new Date());
		JSONObject result=new JSONObject();
		try {
			
			Student stuName = studentService.existStudentName(student.getName());
			String code=student.getCode();
			if (id != null) {   // userId不为空 说明是修改
				
				if(stuName == null ||(stuName!=null && stuName.getId().equals(id))){
					
					user.setUserid(id);
					studentService.updateStudent(student);
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加
				if(stuName==null||student.getCode().equals(code)){  // 没有重复可以添加
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
	@RequestMapping("reserveTc")
	public void reserveTc(HttpServletRequest request,Teacher teacher,HttpServletResponse response,Student student){
		teacher.setCreatetime(new Date());
		JSONObject result=new JSONObject();
		try {
					teacherService.addTc(teacher);
					result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("stuEcharts")
	public void stuEcharts(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			
			List<StudentVo>list=studentService.echartsAll(); 
			result.put("success", true);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	@RequestMapping("studentImport")
	public void studentImport(HttpServletRequest request,HttpServletResponse response,MultipartFile importFile){
		JSONObject result=new JSONObject();
		try {
			
			Workbook workbook = WorkbookFactory.create(importFile.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 0; i < lastRowNum; i++) {
				Row row = sheet.getRow(i+1);
				String name = row.getCell(0).toString();
				String tname = row.getCell(1).toString();
				long age =(long) row.getCell(2).getNumericCellValue();
				String grade = row.getCell(3).toString();
				Date entrytime = row.getCell(4).getDateCellValue();
				Teacher teacher=teacherService.findTeacherName(tname);
				Student student = new Student();
				student.setName(name);
				student.setTid(teacher.getTid());
				student.setAge(age);
				student.setGrade(grade);
				student.setEntrytime(entrytime);
				studentService.addStudent(student);
			}
			
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("studentDown")
	public void courseDown(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
			HSSFSheet sheet = wb.createSheet("操作记录备份");//第一个sheet
			HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
			String [] handers={"学生编号","学生姓名","老师姓名","年龄","所属年级","入校时间"};
			for (int i = 0; i < handers.length; i++) {
				rowFirst.createCell(i).setCellValue(handers[i]);
			}
			List<StudentVo>list=studentService.findAll(new StudentVo());
			for (int i = 0; i < list.size(); i++) {
				StudentVo studentVo=list.get(i);
				HSSFRow row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(studentVo.getId());
				row.createCell(1).setCellValue(studentVo.getName());
				row.createCell(2).setCellValue(studentVo.getCname());
				row.createCell(3).setCellValue(studentVo.getAge());
				row.createCell(4).setCellValue(studentVo.getGrade());
				row.createCell(5).setCellValue(TimeUtil.formatTime(studentVo.getEntrytime(), "yyyy-MM-dd"));
			}
			//写出文件（path为文件路径含文件名）
			OutputStream os;
			File file = new File("D:\\aaa\\导出学生.xls");
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
	
//	@RequestMapping("studentImport")
//	public void studentImport(HttpServletRequest request,HttpServletResponse response,MultipartFile importFile){
//		JSONObject result=new JSONObject();
//		try {
//			HSSFWorkbook workbook = new HSSFWorkbook(importFile.getInputStream());
//			HSSFSheet sheetAt = workbook.getSheetAt(0);
//			int rowNum = sheetAt.getLastRowNum();
//			for (int i = 1; i <=rowNum; i++) {
//				HSSFRow row = sheetAt.getRow(i);
//				String name = row.getCell(0).toString();
//				String cname = row.getCell(1).toString();
//				String age =row.getCell(2).toString();
//				String grade = row.getCell(3).toString();
//				Date entrytime = row.getCell(4).getDateCellValue();
//				String code = row.getCell(5).toString();
//				Date  createtime= row.getCell(6).getDateCellValue();
//				Teacher teacher = teacherService.findTeacherName(cname);
//				Student student = new Student();
//				student.setName(name);
//				student.setId(teacher.getTid());
//				student.setAge(Integer.parseInt(age));
//				student.setGrade(grade);
//				student.setEntrytime(entrytime);
//				student.setCode(code);
//				student.setCreatetime(new Date());
//				studentService.addStudent(student);
//			}
//			
//			result.put("success", true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("删除用户信息错误",e);
//			result.put("errorMsg", "对不起，删除失败");
//		}
//		WriterUtil.write(response, result.toString());
//	}
//	
//	
//	@RequestMapping("studentImport")
//	public void studentImport(HttpServletRequest request,HttpServletResponse response,MultipartFile importFile){
//		JSONObject result=new JSONObject();
//		try {
//			
//			
//			HSSFWorkbook workbook = new HSSFWorkbook(importFile.getInputStream());
//			HSSFSheet sheetAt = workbook.getSheetAt(0);
//			int rowNum = sheetAt.getLastRowNum();
//			for (int i = 1; i <=rowNum; i++) {
//				HSSFRow row = sheetAt.getRow(i);
//				String name = row.getCell(0).getStringCellValue();
//				String tname = row.getCell(1).getStringCellValue();
//				Double numericCellValue = row.getCell(2).getNumericCellValue();
//				int age=numericCellValue.intValue();
//				String grade = row.getCell(3).getStringCellValue();
//				Date entrytime = row.getCell(4).getDateCellValue();
//		
//		
//			Integer tid = findByStudent(tname);
//			Student student = new Student();
//			student.setName(name);
//			student.setTid(tid);
//			student.setAge(age);
//			student.setGrade(grade);
//			student.setEntrytime(entrytime);
//			
//			studentService.addStudent(student);
//			}
//			
//			result.put("success", true);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("删除用户信息错误",e);
//			result.put("errorMsg", "对不起，删除失败");
//		}
//		WriterUtil.write(response, result.toString());
//	}
//	@Autowired
//	private TeacherMapper teacherMapper;
//	public Integer findByStudent(String tname){
//		Teacher teacher = new Teacher();
//		teacher.setTname(tname);
//		Teacher one = teacherMapper.selectOne(teacher);
//		return one.getTid();
//				
//		
//	}
	
	
}
