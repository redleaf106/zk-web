/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.audit;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;

import cn.org.bjca.zk.db.entity.InprocessAudit;
import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.service.InprocessAuditService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.InprocessAuditPage;

/***************************************************************************

 * @文件名称: InprocessAudit.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.audit
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月28日
 ***************************************************************************/

@Controller
@RequestMapping("/audit/inprocessAudit")
public class InprocessAuditController extends BaseController{
	
	@Autowired
	private InprocessAuditService inprocessAuditService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value ="")
	public String list( ModelMap modelMap, HttpServletRequest request) {
		InprocessAuditPage<InprocessAudit> inprocessAuditPage = new InprocessAuditPage<InprocessAudit>();
        Page page = new Pagination();
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
			inprocessAuditPage.setEmployeeName("%"+employeeName.trim()+"%");
		}
		String icCardNumber = request.getParameter("icCardNumber");//ic卡号
		if(StringUtils.isNotBlank(icCardNumber)) {
			inprocessAuditPage.setIcCardNumber("%"+icCardNumber.trim()+"%");
		}
		inprocessAuditPage.setPageVO(page);
		inprocessAuditPage = inprocessAuditService.findPage(inprocessAuditPage);
		inprocessAuditPage.setEmployeeName(employeeName);
		inprocessAuditPage.setIcCardNumber(icCardNumber);
		modelMap.put("inprocessAuditPage", inprocessAuditPage);
		return "/audit/inprocessAudit/inprocessAuditList";
	}
	
	
	
	/**
	  * <p>指向巡检表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{inprocessAuditId}", method = RequestMethod.GET)
	public String toEditFormPage( @PathVariable("inprocessAuditId") String inprocessAuditId, ModelMap modelMap){	
		InprocessAudit inprocessAudit = null;
		if(StringUtils.isNotBlank(inprocessAuditId)&& !BLANK_PARAM_VALUE.equals(inprocessAuditId)){
			inprocessAudit = inprocessAuditService.findUniqueById(inprocessAuditId);
		}else{
			inprocessAudit = new InprocessAudit();
		}
		modelMap.put("inprocessAudit", inprocessAudit);
		
		List<Employee> employeeList = employeeService.getAll(); // 获取部门列表
		modelMap.put("employeeList", employeeList); // 角色列表
		return "/audit/inprocessAudit/inprocessAuditForm";
	}
	
	/**
	  * <p>保存或更新表单信息</p>
	  * @Description:
	  * @param user
	  * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate( InprocessAudit inprocessAudit,HttpServletRequest request) throws Exception {
		Message message = new Message();
				
		if(StringUtils.isNotBlank(inprocessAudit.getId()))
			message.setContent(this.UPDATE);//内容提示
		else{			
			message.setContent(this.SAVE);//内容提示
		}
		
		
		User loginUser = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
		if(null!=loginUser) {
			inprocessAudit.setUserId(loginUser.getId());
		}
		inprocessAuditService.saveOrUpdate(inprocessAudit);
		message.setStatusCode(this.SUCCESS);
		message.setCallbackType("closeCurrent");
		message.setNavTabId("inprocessAudit");
		return this.ajaxDone(message);
	}
	
}
