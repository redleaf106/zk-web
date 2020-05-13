/**
 *
 */
package cn.org.bjca.zk.platform.web.controller.employee;

import cn.org.bjca.zk.db.entity.*;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.AttachmentMessage;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.service.*;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.EmployeePage;
import com.alibaba.fastjson.JSONObject;
import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	@Autowired
	private MessageService messageService;
	@Autowired
    private CabinetService cabinetService;
	@Autowired
    private CabinetDoorService cabinetDoorService;
	@Autowired
	private CabinetDoorEventService cabinetDoorEventService;


	@Value("#{sysConfig['picMaxSize']}")
	private String picMaxSizeStr ;


	@RequestMapping(value ="")
	public String list( ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		EmployeePage<Employee> employeePage = new EmployeePage<Employee>();
		Page page = new Pagination();
		String purpose = request.getParameter("activation");
		String ip = request.getParameter("IPAdress");
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
			employeePage = employeeService.getAllNotActive(employeePage,ip);
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
			//employee.setEmployeeNumber(EssPdfUtil.genrRandomUUID());
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
		System.out.println("紧急开门权限"+employee.getCheckPower());
		Message message = new Message();
//		if ("稽核部".equals(employee.getDepartmentId())){
//			employee.setCheckPower(0);
//		}else{
//			employee.setCheckPower(1);
//		}
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
					message.setContent("此门卡号已经被使用，请您重新输入!"); // 内容提示
					hasErrors = true;
				}
				Employee employeeResult = employeeService.findByEmployeeNumber(employee.getEmployeeNumber());
				if(employeeResult!=null){
					message.setContent("员工工号已存在，请重新输入！");
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
					String  picfileName= EssPdfUtil.genrRandomUUID()+".png";
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
        System.out.println("coming ...");
		EmployeePage<Employee> employeePage = new EmployeePage<Employee>();
		Page page = new Pagination();
		employeePage.setPageVO(page);
		employeePage = employeeService.findPage(employeePage);
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toJSONString(employeePage.getData());
	}

	@RequestMapping(value = "insertOrUpdate")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取参数
		String employeeName = request.getParameter("employeeName");
		String departmentName = request.getParameter("departmentName");
		String employeeNumber = request.getParameter("employeeNumber");
		String mobilePhone = request.getParameter("mobilePhone");
		String email = request.getParameter("email");

		JSONObject json = new JSONObject();
		int code = 0;
		//校验
		boolean hasErrors = false; // 成功失败标识 ： true表示有错误
		boolean repeat = false; //员工是否存在：false表示存在
		Employee employeeExist = employeeService.findByicCardNumber(employeeNumber);
		if (employeeExist==null){
			employeeExist = new Employee();
			repeat = true;
		}

		List<Department> list = departmentService.findByDepartmentName(departmentName);
		if(list==null||list.isEmpty()){
			hasErrors = true;//部门不存在
			json.put("msg","部门不存在!");
			code = 401;
		}else{
			Department d = list.get(0);
			employeeExist.setDepartmentId(d.getId());
			if(!"稽核部".equals(departmentName)){
				employeeExist.setCheckPower(1);
			}else{
				employeeExist.setCheckPower(0);
			}
		}
		employeeExist.setEmployeeName(employeeName);
		employeeExist.setIcCardNumber(employeeNumber);
		employeeExist.setEmail(email);
		employeeExist.setMobilePhone(mobilePhone);
		if (!hasErrors) {
			if(!repeat){
				code = 201;
			}else{
				code = 200;
				employeeExist.setEmployeeNumber(EssPdfUtil.genrRandomUUID());
			}
			employeeService.saveOrUpdate(employeeExist);
			json.put("msg","success");
		}
		json.put("code", code);
		response.getWriter().write(json.toJSONString());
		return null;
	}

	//@RequestMapping(value = "insertOrUpdate")
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
            System.out.println("部门名称是"+departmentName);
			List<Department> list = departmentService.findByDepartmentName(departmentName);
			if(list==null||list.isEmpty()){
				hasErrors = true;//部门不存在
				json.put("errmsg","部门不存在!");
			}else{
				Department d = list.get(0);
				employeeExist.setDepartmentId(d.getId());
				if(!"稽核部".equals(d.getDepartmentName())){
					employeeExist.setCheckPower(1);
				}else{
                    employeeExist.setCheckPower(0);
                }
			}
			employeeExist.setEmployeeName(employee.getEmployeeName());
			employeeExist.setEmail(employee.getEmail());
			employeeExist.setMobilePhone(employee.getMobilePhone());
			employeeExist.setIcCardNumber(employee.getIcCardNumber());
			if (!repeat) {
				message.setContent(UPDATE); // 内容提示
				json.put("code","201");
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
		json.put("message",message);
		json.put("code",200);
		response.getWriter().write(json.toJSONString());
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "getOAInfo",produces="text/html;charset=UTF-8")
	public String getOA(String startdate,String enddate,String objno) throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("startdate",startdate);
		jsonObj.put("enddate",enddate);
		jsonObj.put("objno",objno);
		jsonObj.put("secret","4028818230db6dbd0130fe847d6742ba");
		String finalR = "";
		try {
			Client client = new Client(new URL("http://10.50.115.190:8288/services/checkLeaveByEMP?wsdl"));
			Object[] results = client.invoke("GetInfoByEmp", new Object[] { jsonObj.toJSONString() });
			for (Object o:results){
				System.out.println(o);
				finalR += o;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return finalR;

	}

	@RequestMapping(value = "sendMessageTest",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendMessageTest(String tel){
		List<String> list = new ArrayList<>();
		list.add(tel);
		String result = messageService.sendMessage(list);
		return result;
	}

	@RequestMapping(value = "getOtherEmp",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getOtherEmp(String ip) throws ParseException {
		String ip2 = "";
	    if(ip.equals("10.1.81.209")){
            ip2 = "172.16.1.71";
        }else if(ip.equals("172.16.63.155")){
	    	ip2 = "172.16.63.156";
		}else if(ip.equals("172.16.63.156")){
	    	ip2 = "172.16.63.155";
		}else if(ip.equals("172.16.63.158")){
	    	ip2 = "172.16.63.159";
		}else if(ip.equals("172.16.63.159")){
	    	ip2 = "172.16.63.158";
		}else{
	    	return "ip no found";
		}
	    //当天时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String today = simpleDateFormat.format(new Date());
	    Date date = simpleDateFormat.parse(today);
	    //根据ip查机柜
	    Cabinet cabinet = cabinetService.findByIp(ip);
	    Cabinet cabinet1 = cabinetService.findByIp(ip2);
	    //根据机柜查柜门
        List<CabinetDoor> cabinetDoorList = cabinetDoorService.findByCabinetID(cabinet.getId());
        List<CabinetDoor> cabinetDoorList1 = cabinetDoorService.findByCabinetID(cabinet1.getId());
        List<Employee> employeeList = new ArrayList<>();
        for(CabinetDoor cabinetDoor:cabinetDoorList){
            String empid = cabinetDoor.getEmployeeId();
            Employee employee = employeeService.findUniqueById(empid);
            if(employee.getPicFile()!=null){
				CabinetDoor cabinetDoor1 = cabinetDoorService.selectDoorByEmployeeId(employee.getId());
				String boxID = cabinetDoor1.getCabinetDoorNumber();
				String cabinetNumber = cabinetService.findUniqueById(cabinetDoor1.getCabinetId()).getCabinetNumber();
				List<CabinetDoorEvent> cabinetDoorEventList = cabinetDoorEventService.findOneEMPByOneDay(employee.getIcCardNumber(),date);
				String ext3 = "";
				if(cabinetDoorEventList.size()==0){
					ext3 = "0";
				}else {
					String status = cabinetDoorEventList.get(0).getStatus();
					if(status.equals("0")||status.equals("2")){
						ext3 = "1";
					}else if(status.equals("1")||status.equals("3")){
						ext3 = "0";
					}
				}
				employee.setExt1(boxID);
				employee.setExt2(cabinetNumber);
				employee.setExt3(ext3);
                employeeList.add(employee);
            }
        }
        for(CabinetDoor cabinetDoor:cabinetDoorList1){
			String empid = cabinetDoor.getEmployeeId();
			Employee employee = employeeService.findUniqueById(empid);
			if(employee.getPicFile()!=null){
				CabinetDoor cabinetDoor1 = cabinetDoorService.selectDoorByEmployeeId(employee.getId());
				String boxID = cabinetDoor1.getCabinetDoorNumber();
				String cabinetNumber = cabinetService.findUniqueById(cabinetDoor1.getCabinetId()).getCabinetNumber();
				List<CabinetDoorEvent> cabinetDoorEventList = cabinetDoorEventService.findOneEMPByOneDay(employee.getIcCardNumber(),date);
				String ext3 = "";
				if(cabinetDoorEventList.size()==0){
					ext3 = "0";
				}else {
					String status = cabinetDoorEventList.get(0).getStatus();
					if(status.equals("0")||status.equals("2")){
						ext3 = "1";
					}else if(status.equals("1")||status.equals("3")){
						ext3 = "0";
					}
				}
				employee.setExt1(boxID);
				employee.setExt2(cabinetNumber);
				employee.setExt3(ext3);
				employeeList.add(employee);
			}
		}
        JSONObject jsonObject = new JSONObject();
	    return jsonObject.toJSONString(employeeList);
    }


}
