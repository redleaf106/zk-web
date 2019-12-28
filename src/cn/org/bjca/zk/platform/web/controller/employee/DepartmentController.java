/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.employee;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;

import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.DepartmentService;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.DepartmentPage;
import cn.org.bjca.zk.db.entity.Department;

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
	
	/**
	  * <p>角色管理列表</p>
	  * @Description:
	  * @param model
	  * @param request
	  * @return
	 */
	@RequestMapping("")
	public String list(ModelMap modelMap,HttpServletRequest request) {
		DepartmentPage<Department> departmentPage = new DepartmentPage<Department>();
        Page page = new Pagination();
        String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		
		String departmentName = request.getParameter("departmentName");//角色名称
		if(StringUtils.isNotBlank(departmentName)) {
			departmentPage.setDepartmentName("%"+departmentName.trim()+"%");
		}
		
		departmentPage.setPageVO(page);
		departmentPage = departmentService.findPage(departmentPage);
		departmentPage.setDepartmentName(departmentName);
		modelMap.put("departmentPage", departmentPage);
		return "/employee/department/departmentList";
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
	  * @param signKey
	  * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(Department department,HttpServletRequest request) throws DialogException {
		try{
			Message message = new Message();
			if(StringUtils.isNotBlank(department.getId()))
				message.setContent(this.UPDATE);//内容提示
			else{
				message.setContent(this.SAVE);//内容提示
			}
			User user = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
			if(null!=user) {
				department.setUserId(user.getId());
			}
			departmentService.saveOrUpdate(department);
			message.setStatusCode(this.SUCCESS);
			message.setCallbackType("closeCurrent");
			message.setNavTabId("department");
			return this.ajaxDone(message);
		}catch(Exception ex){
			throw new DialogException(ex);
		}
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id ,id主键
	  * @param model
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
}
