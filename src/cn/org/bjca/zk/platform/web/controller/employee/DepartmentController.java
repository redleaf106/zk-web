/**
 *
 */
package cn.org.bjca.zk.platform.web.controller.employee;


import cn.org.bjca.zk.db.entity.Department;
import cn.org.bjca.zk.db.entity.TimeArea;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.DepartmentService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.tools.SocketServer;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.DepartmentPage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***************************************************************************

 * @文件名称: DepartmentController.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.employee
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/

@Controller
@RequestMapping("/employee/department")
public class DepartmentController extends BaseController {
	/**
	 *  部门服务
	 */
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private EmployeeService employeeService;

	/**
	 * <p>角色管理列表</p>
	 * @Description:
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping("")
	public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		DepartmentPage<Department> departmentPage = new DepartmentPage<Department>();
		Page page = new Pagination();
		String from = request.getParameter("from");
		String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
		String numPerPage = request.getParameter("numPerPage");//每页显示条数
		if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}else{
			page.setPageSize(30);
		}

		String departmentName = request.getParameter("departmentName");//角色名称
		if(StringUtils.isNotBlank(departmentName)) {
			departmentPage.setDepartmentName("%"+departmentName.trim()+"%");
		}
		if("android".equals(from)){
			page.setPageSize(1000);
		}
		departmentPage.setPageVO(page);
		departmentPage = departmentService.findPage(departmentPage);
		departmentPage.setDepartmentName(departmentName);
		if("android".equals(from)){
			try {
				response.getWriter().write(JSONArray.toJSONString(departmentPage.getData()));
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.put("departmentPage", departmentPage);
		return "/employee/department/departmentList";
	}

	@RequestMapping(value = "findAll", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findAllDep(){
		List<Department> list = departmentService.getAll();
		return JSONObject.toJSONString(list);
	}

	/**
	 * <p>指向编辑表单页面</p>
	 * @Description:
	 * @return
	 */
	@RequestMapping(value = "toEditFormPage/{id}", method = RequestMethod.GET)
	public String toEditFormPage(@PathVariable String id,ModelMap modelMap) throws Exception {
		Department department = null;
		if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
			department = departmentService.findUniqueById(id);
		}else {
			department = new Department();
			department.setDepartmentNumber(EssPdfUtil.genrRandomUUID());
		}
		modelMap.put("department",department);
		return "/employee/department/departmentForm";
	}

	/**
	 * <p>保存或更新表单信息</p>
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(Department department,HttpServletRequest request) throws DialogException {
		try{
			Message message = new Message();
			boolean flag = false;
			if(StringUtils.isNotBlank(department.getId())){
				message.setContent(this.UPDATE);//内容提示
				if(department.getEndTime().size()>0&&department.getStartTime().size()>0){
					flag = true;
				}
			}
			else{
				message.setContent(this.SAVE);//内容提示
			}
			User user = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
			if(null!=user) {
				department.setUserId(user.getId());
			}
			if(department.getTimeAreas().size()>0){
				for(TimeArea t:department.getTimeAreas()){
					System.out.println(t.getStartTime());
				}
			}
			departmentService.saveOrUpdate(department);
			if(flag){
				List<String> startTime = department.getStartTime();
				List<String> endTime = department.getEndTime();
				SocketServer socketServer = SocketServer.getInstance();
				socketServer.updateDepartmentTime(department.getDepartmentName(),startTime,endTime);
			}
			message.setStatusCode(this.SUCCESS);
			message.setCallbackType("closeCurrent");
			message.setNavTabId("department");
			return this.ajaxDone(message);
		}catch(Exception ex){
			throw new DialogException(ex);
		}
	}

	@RequestMapping(value = "insertOrUpdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String insertOrUpdate(HttpServletRequest request) throws Exception {
		String departmentNumber = request.getParameter("departmentNumber");
		String departmentName = request.getParameter("departmentName");
		Department department = new Department();
		department.setDepartmentNumber(departmentNumber);
		department.setDepartmentName(departmentName);

		Department departmentResult = departmentService.findByDepartmentNumber(departmentNumber);
		if(departmentResult!=null){
			department.setId(departmentResult.getId());
		}
		JSONObject jsonObject = new JSONObject();
		int status = departmentService.insertOrUpdate(department);
		if(status==200){
			jsonObject.put("message", "信息添加成功");
		}else if(status==201){
			jsonObject.put("message", "信息修改成功");
			SocketServer socketServer = SocketServer.getInstance();
			List list = employeeService.syncEmpAndDepByDepartmentNumber(departmentNumber);
			socketServer.syncEmpAndDep(list);
		}else if(status==400){
			jsonObject.put("message", "添加失败");
		}
		jsonObject.put("code", status);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping("syncDepartmentInfo")
	public String syncDepartmentInfo() throws InterruptedException {
		Gson gson = new Gson();
		SocketServer socketServer = SocketServer.getInstance();
		List<Department> departmentList = departmentService.getAll();
		for(Department department:departmentList){
			List list = employeeService.syncEmpAndDepByDepartmentNumber(department.getDepartmentNumber());
			if(list.size()>0){
				Department d = departmentService.findUniqueById(department.getId());
				List<TimeArea> t = d.getTimeAreas();
				List<String> st = new ArrayList<>();
				List<String> et = new ArrayList<>();
				for(TimeArea ttt:t){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
					Date d1 = new Date(ttt.getStartTime().getTime());
					st.add(simpleDateFormat.format(d1));
					Date d2 = new Date(ttt.getEndTime().getTime());
					et.add(simpleDateFormat.format(d2));
				}
				//todo 先更新部门名
				socketServer.syncEmpAndDep(list);
				Thread.sleep(4000);
				//todo 再更新时间
				socketServer.updateDepartmentTime(department.getDepartmentName(),st,et);
				System.out.println(gson.toJson(st));
				Thread.sleep(4000);
			}
		}


		return "success";
	}

	/**
	 * <p>根据id删除记录</p>
	 * @Description:
	 * @param id ,id主键
	 * @param id
	 * @return
	 * @throws DialogException
	 */
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody String delete(@PathVariable("id") String id) throws DialogException {
		try{
			departmentService.delDepartmentById(id);
			Message message = new Message();
			message.setStatusCode(this.SUCCESS);
			message.setContent(this.DELETE);//内容提示
			return this.toJsonString(message);
		}catch(Exception ex){
			throw new DialogException(ex);
		}
	}

	@RequestMapping("updateDepartmentTime")
	@ResponseBody
	public String updateDepartmentTime(int timeStartIndex, int timeEndIndex){
		System.out.println(timeStartIndex);
		System.out.println(timeEndIndex);
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.UPDATE);//内容提示
		if(timeStartIndex!=0||timeEndIndex!=0){
			departmentService.updateDepartmentTime(timeStartIndex,timeEndIndex);
			List<Department> list = departmentService.getAll();
			SocketServer socketServer = SocketServer.getInstance();
			socketServer.updateDepartmentTime(list);
		}
		return this.toJsonString(message);
	}
}
