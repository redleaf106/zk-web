/**
 *
 */
package cn.org.bjca.zk.platform.web.controller.employee;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.bjca.zk.platform.service.EmailService;
import cn.org.bjca.zk.platform.vo.EmailVO;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;

import cn.org.bjca.zk.db.entity.Department;
import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.AttachmentMessage;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.service.DepartmentService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.EmployeePage;

/***************************************************************************

 * @文件名称: EmployeeController.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.employee
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/
@Controller
@RequestMapping("/employee/employee")
public class EmployeeController extends BaseController{

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DepartmentService departmentService;
	@Value("#{sysConfig['picMaxSize']}")
	private String picMaxSizeStr ;


	@RequestMapping(value ="")
	public String list( ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		EmployeePage<Employee> employeePage = new EmployeePage<Employee>();
		Page page = new Pagination();
		String purpose = request.getParameter("activation");
		String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
		String numPerPage = request.getParameter("numPerPage");//每页显示条数
		if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}

		String employeeName = request.getParameter("employeeName");//用户名称
		if(StringUtils.isNotBlank(employeeName)) {
			employeePage.setEmployeeName("%"+employeeName.trim()+"%");
		}

		employeePage.setPageVO(page);
		if(purpose==null||purpose.length()==0){
			employeePage = employeeService.findPage(employeePage);
			employeePage.setEmployeeName(employeeName);
			modelMap.put("employeePage", employeePage);
			return "/employee/employee/employeeList";
		}else{
			employeePage = employeeService.getAllNotActive(employeePage);
			employeePage.setEmployeeName(employeeName);
			JSONObject jsonObject = new JSONObject();
			response.getWriter().write(jsonObject.toJSONString(employeePage.getData()));
			return null;
		}
	}

	/**
	 * <p>指向查找印章附件页面</p>
	 *
	 * @Description:
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toAttachmentLookUpPage", method = RequestMethod.GET)
	public String toAttachmentLookUpPage() throws Exception {
		return "/employee/employee/attachmentLookup";
	}


	/**
	 * <p>指向员工表单页面</p>
	 * @Description:
	 * @return
	 */
	@RequestMapping(value = "toEditFormPage/{employeeId}", method = RequestMethod.GET)
	public String toEditFormPage( @PathVariable("employeeId") String employeeId, ModelMap modelMap){
		System.out.println("进入表单页面"+employeeId);
		Employee employee = null;
		if(StringUtils.isNotBlank(employeeId)&& !BLANK_PARAM_VALUE.equals(employeeId)){
			employee = employeeService.findUniqueById(employeeId);
		}else{
			employee = new Employee();
			employee.setEmployeeNumber(EssPdfUtil.genrRandomUUID());
		}
		modelMap.put("employee", employee);

		List<Department> departmentList = departmentService.getAll(); // 获取部门列表
		modelMap.put("departmentList", departmentList); // 角色列表
		return "/employee/employee/employeeForm";
	}

	/**
	 * <p>保存或更新表单信息</p>
	 * @Description:
	 * @param employee
	 * @return
	 */


	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(Employee employee, HttpServletRequest request) throws Exception {
		System.out.println("sssssssssssssssss"+employee.getIcCardNumber());
		Message message = new Message();
		if ("稽核部".equals(employee.getDepartmentId())){
			employee.setCheckPower(0);
		}else{
			employee.setCheckPower(1);
		}
		boolean hasErrors = false; // 成功失败标识 ： true表示有错误
		Employee employeeExist = null;
		try {
			// 印章附件路径
			String attachmentPath = request.getParameter("attachment.attachmentPath");
			System.out.println("attachmentPath========="+attachmentPath);
			attachmentPath = new String(attachmentPath.getBytes("ISO-8859-1"), "UTF-8");
			if (StringUtils.isNotBlank(employee.getId())) {
				message.setContent(UPDATE); // 内容提示
				employeeExist = employeeService.findUniqueById(employee.getId());
			} else {
				message.setContent(SAVE); // 内容提示
				List<Employee> checkEmployeeExist = employeeService
						.findEmployeesByCardNumber(employee.getIcCardNumber());
				if (checkEmployeeExist != null && !checkEmployeeExist.isEmpty()) {
					message.setContent("员工编号已存在，请重新输入!"); // 内容提示
					hasErrors = true;
				}
//				if (!hasErrors) {
//					if (attachmentPath.equals("")) {
//						message.setContent("员工图片不能为空，请选择!"); // 内容提示
//						hasErrors = true;
//					}
//				}
			}

			if (!hasErrors) {
				if (!"".equals(attachmentPath)) {
					String filePath = EssPdfUtil.getCurrPath() + "upload/" + attachmentPath;
					byte[] picBty = FileUtils.readFileToByteArray(new File(filePath));
					employee.setPicFile(picBty);
				} else {
					employee.setPicFile(null);
				}
				if (!hasErrors) {
					User loginUser = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
					if (null != loginUser) {
						employee.setUserId(loginUser.getId());
					}
					employeeService.saveOrUpdate(employee);
					if (!"".equals(attachmentPath)) { // 删除临时附件
						FileUtils.deleteQuietly(new File(attachmentPath));
					}
				}
			}
		} catch (Exception ex) {
			logger.error("saveOrUpdate employee info error: ", ex);
			if (StringUtils.isNotBlank(employee.getId())) {
				message.setContent(UPDATE_ERROR); // 内容提示
			} else {
				message.setContent(SAVE_ERROR); // 内容提示
			}
			hasErrors = true;
		}
		if (!hasErrors) {
			message.setStatusCode(SUCCESS);
		} else {
			message.setStatusCode(FAIL);
		}

		message.setCallbackType("closeCurrent");
		message.setNavTabId("employee");
		return this.ajaxDone(message);
	}


	/**
	 * <p>根据id删除记录</p>
	 * @Description:
	 * @param id ,id主键
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody String delete(@PathVariable("id") String id) throws Exception{
		employeeService.delEmployeeById(id);
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.DELETE);//内容提示
		return this.toJsonString(message);
	}

	/**
	 * <p>上传员工图片</p>
	 *
	 * @Description:
	 * @param mFile 员工图片文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadPicAttachment")
	public void uploadSealAttachment(@RequestParam("file") CommonsMultipartFile mFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取请求头
		String requestHeader = request.getHeader("from");
		String id = request.getParameter("id");
		System.out.println("icCardNumber====="+id);
		AttachmentMessage attachMessage = new AttachmentMessage();
		byte[] imgBty = mFile.getBytes();
		System.out.println("imgBty======"+imgBty.length);
		String fileName = getXssFileName(mFile);
		String imgType = getXssContentType(mFile);
		if (imgBty.length > 0 && StringUtils.isNotBlank(fileName)) {
			String imgExt = filterImgType(imgType);
			Long picSize = mFile.getSize();
			Double picMaxSize = 1.00;
			try {
				picMaxSize = Double.parseDouble(picMaxSizeStr);
			} catch (Exception e) {
				logger.error("******picMaxSize error,can't convert Double:" + picMaxSizeStr, e);
			}
			if (imgExt != null) {
				if (picSize <= picMaxSize * PDFSealConstants.SIZE_KB) {
					String  picfileName= EssPdfUtil.genrRandomUUID()+".pic";
					String filePath = EssPdfUtil.getCurrPath()+"upload/"+picfileName;
					System.out.println("图片路径为："+filePath);
					FileUtils.writeByteArrayToFile(new File(filePath), imgBty);
					attachMessage.setAttachmentPath(new String(picfileName.getBytes(PDFSealConstants.CODING), "iso-8859-1") );
				} else {
					attachMessage.setAttachmentPath("");
				}
				attachMessage.setId(imgExt); // 图片类型
				attachMessage.setFileName(fileName);
				attachMessage.setAttachmentSize(picSize.toString());
				// 下划线之前是以byte单位，在页面上进行比较的作用 下划线之后是从配置文件中读取的数值，在页面上准确提示的作用
				attachMessage.setAttachmentMaxSize((picMaxSize * PDFSealConstants.SIZE_KB) + "_" + picMaxSize);

				//更新
				if(requestHeader!=null&&requestHeader.length()>0){
					Employee employee = employeeService.findUniqueById(id);
					String filePath = EssPdfUtil.getCurrPath() + "upload/" + attachMessage.getAttachmentPath();
					byte[] picBty = FileUtils.readFileToByteArray(new File(filePath));
					employee.setPicFile(picBty);
					employeeService.saveOrUpdate(employee);
				}
			} else {
				logger.error("******server seal type error:" + imgType);
			}
			PrintWriter out = null;
			try {
				out = response.getWriter();
				String jsonStr = toJsonString(attachMessage);
				out.println(jsonStr);
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
	}

	/**
	 * 安卓端员工注册
	 */
	@RequestMapping(value = "uploadImgForAndroid")
	public void registerForAndroid(HttpServletRequest request) throws IOException {
		Map<String, Object> map = new HashMap<>();
		//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//检查form中是否有enctype="multipart/form-data"
		if(multipartResolver.isMultipart(request))
		{
			//将request变成多部分request
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			//获取multiRequest 中所有的文件名
			Iterator iter=multiRequest.getFileNames();

			while(iter.hasNext())
			{
				//一次遍历所有文件
				MultipartFile file=multiRequest.getFile(iter.next().toString());
				if(file!=null)
				{
					//String path="/LFT/Resources/banner/"+file.getOriginalFilename();
					String path = "D:\\pic/"+file.getOriginalFilename();
					//上传
					file.transferTo(new File(path));

				}
			}
		}
	}

	/**
	 * <p>显示员工图片</p>
	 *
	 * @Description:
	 * @param id 员工id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "showPic")
	public void showPic(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
		OutputStream os = null;
		try {
			id = this.filterSafeStringXSS(id);
			byte[] picBty = null;
			if (id.length() == 32) { // 表示从数据库获取印章
				Employee employee = employeeService.findUniqueById(id);
				picBty = employee.getPicFile();
			} else { // 从目录获取印章
				String filePath = EssPdfUtil.getCurrPath()+"upload/"+id;
				filePath = new String(filePath.getBytes("iso-8859-1"), PDFSealConstants.CODING);
				logger.debug("**********server img path:" + filePath);
				picBty = FileUtils.readFileToByteArray(new File(filePath));
			}
			os = response.getOutputStream();
			os.write(picBty);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	@RequestMapping(value = "showAllEmployee")
	@ResponseBody
	public String showAllEmployee(){
		EmployeePage<Employee> employeePage = new EmployeePage<Employee>();
		Page page = new Pagination();
		employeePage.setPageVO(page);
		employeePage = employeeService.findPage(employeePage);
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toJSONString(employeePage.getData());
	}

	@RequestMapping(value = "insertOrUpdate")
	public String insertOrUpdate(Employee employee,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Message message = new Message();
		JSONObject json = new JSONObject();
		boolean hasErrors = false; // 成功失败标识 ： true表示有错误
		boolean repeat = false; //员工是否存在：false表示存在
		Employee employeeExist = employeeService.findByicCardNumber(employee.getIcCardNumber());
		//根据ic卡号查看是否有这个员工
		if (employeeExist==null){
			employeeExist = new Employee();
			repeat = true;
		}
		try {
			String departmentName = employee.getDepartmentId();
			List<Department> list = departmentService.findByDepartmentName(departmentName);
			if(list==null||list.isEmpty()){
				hasErrors = true;//部门不存在
				json.put("errmsg","部门不存在!");
			}else{
				Department d = list.get(0);
				employeeExist.setDepartmentId(d.getId());
				if(!"稽核部".equals(d.getDepartmentName())){
					employeeExist.setCheckPower(1);
				}
			}
			employeeExist.setEmployeeName(employee.getEmployeeName());
			employeeExist.setEmail(employee.getEmail());
			employeeExist.setMobilePhone(employee.getMobilePhone());
			employeeExist.setIcCardNumber(employee.getIcCardNumber());
			if (!repeat) {
				message.setContent(UPDATE); // 内容提示
			} else {
				System.out.println("新增员工");
				message.setContent(SAVE); // 内容提示
				employeeExist.setEmployeeNumber(EssPdfUtil.genrRandomUUID());
				if (employeeExist.getId() != null) {
					message.setContent("员工编号已存在，请重新输入!"); // 内容提示
					json.put("errmsg","员工编号已存在，请重新输入!");
					hasErrors = true;
				}
			}
			if (!hasErrors) {
				User loginUser = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
				if (null != loginUser) {
					employee.setUserId(loginUser.getId());
				}
				employeeService.saveOrUpdate(employeeExist);
				json.put("msg","success");
			}
		}catch (Exception ex) {
			logger.error("saveOrUpdate employee info error: ", ex);
			if (StringUtils.isNotBlank(employee.getId())) {
				message.setContent(UPDATE_ERROR); // 内容提示
			} else {
				message.setContent(SAVE_ERROR); // 内容提示
			}
			hasErrors = true;
		}
		if (!hasErrors) {
			message.setStatusCode(SUCCESS);
		} else {
			message.setStatusCode(FAIL);
		}

		message.setCallbackType("closeCurrent");
		message.setNavTabId("employee");
		response.getWriter().write(json.toJSONString());
		return null;
	}

}
