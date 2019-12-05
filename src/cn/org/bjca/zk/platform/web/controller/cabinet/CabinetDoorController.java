/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.cabinet;

import java.util.List;

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

import cn.org.bjca.zk.db.entity.Cabinet;
import cn.org.bjca.zk.db.entity.CabinetDoor;
import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.CabinetDoorService;
import cn.org.bjca.zk.platform.service.CabinetService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetDoorPage;

/***************************************************************************

 * @文件名称: CabinetDoorController.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.cabinet
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
@Controller
@RequestMapping("/cabinet/cabinetDoor")
public class CabinetDoorController extends BaseController {
	
	@Autowired
	private CabinetService cabinetService; 
	
	@Autowired
	private CabinetDoorService cabinetDoorService;
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	  * <p>角色管理列表</p>
	  * @Description:
	  * @param model
	  * @param request
	  * @return
	 */
	@RequestMapping("")
	public String list(ModelMap modelMap,HttpServletRequest request) {
		CabinetDoorPage<CabinetDoor> cabinetDoorPage = new CabinetDoorPage<CabinetDoor>();
       Page page = new Pagination();
       String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
       String numPerPage = request.getParameter("numPerPage");//每页显示条数
       if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		
		String cabinetNumber = request.getParameter("cabinetNumber");//机柜编号
		if(StringUtils.isNotBlank(cabinetNumber)) {
			cabinetDoorPage.setCabinetNumber("%"+cabinetNumber.trim()+"%");
		}
		
		String cabinetDoorNumber = request.getParameter("cabinetDoorNumber");//柜门编号
		if(StringUtils.isNotBlank(cabinetDoorNumber)) {
			cabinetDoorPage.setCabinetDoorNumber("%"+cabinetDoorNumber.trim()+"%");
		}
		
		String employeeName = request.getParameter("employeeName");//员工姓名
		if(StringUtils.isNotBlank(employeeName)) {
			cabinetDoorPage.setEmployeeName("%"+employeeName.trim()+"%");
		}
		
		cabinetDoorPage.setPageVO(page);
		cabinetDoorPage = cabinetDoorService.findPage(cabinetDoorPage);
		cabinetDoorPage.setCabinetNumber(cabinetNumber);
		cabinetDoorPage.setCabinetDoorNumber(cabinetDoorNumber);
		cabinetDoorPage.setEmployeeName(employeeName);
		modelMap.put("cabinetDoorPage", cabinetDoorPage);
		return "/cabinet/cabinetDoor/cabinetDoorList";
	}
	
	/**
	  * <p>指向编辑表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{id}", method = RequestMethod.GET)
	public String toEditFormPage(@PathVariable String id,ModelMap modelMap) throws Exception {
		CabinetDoor cabinetDoor = null;
		if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
			cabinetDoor = cabinetDoorService.findUniqueById(id);
		}else {
			cabinetDoor = new CabinetDoor();
		}
		
		List<Cabinet> cabinetList = cabinetService.getAll(); 
		
		List<Employee> employeeList = employeeService.getAll(); 
		
		modelMap.put("cabinetDoor",cabinetDoor);
		modelMap.put("cabinetList", cabinetList);
		modelMap.put("employeeList", employeeList);
		return "/cabinet/cabinetDoor/cabinetDoorForm";
	}
	
	/**
	  * <p>保存或更新表单信息</p>
	  * @Description:
	  * @param signKey
	  * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(CabinetDoor cabinetDoor,HttpServletRequest request) throws DialogException {
		try{
			Message message = new Message();
			if(StringUtils.isNotBlank(cabinetDoor.getId()))
				message.setContent(this.UPDATE);//内容提示
			else{
				message.setContent(this.SAVE);//内容提示
			}
			User user = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
			if(null!=user) {
				cabinetDoor.setUserId(user.getId());
			}
			cabinetDoorService.saveOrUpdate(cabinetDoor);
			message.setStatusCode(this.SUCCESS);
			message.setCallbackType("closeCurrent");
			message.setNavTabId("cabinetDoor");
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
			cabinetDoorService.delCabinetDoorById(id);
			Message message = new Message();
			message.setStatusCode(this.SUCCESS);
			message.setContent(this.DELETE);//内容提示
			return this.toJsonString(message);
		}catch(Exception ex){
			throw new DialogException(ex);
		}
	}
	
}
