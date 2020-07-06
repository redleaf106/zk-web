package cn.org.bjca.zk.platform.web.controller.account;

import java.io.IOException;
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

import cn.org.bjca.zk.db.entity.Organization;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.service.OrganizationService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.OrganizationPage;

/***************************************************************************
 * <pre>组织机构管理</pre>
 * @文件名称:  organizationController.java
 * @包   路   径：  cn.org.bjca.zk.platform.web.controller.accountManager
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： wangwenc
 * @创建时间：2013-2-15 下午10:00:14 
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 ***************************************************************************/
@Controller
@RequestMapping(value = "/account/organization")
public class OrganizationController extends BaseController {
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	  * <p>组织机构列表</p>
	  * @Description:
	  * @param model
	  * @param request
	  * @return
	 */
	@RequestMapping("")
	public String list(ModelMap modelMap,HttpServletRequest request) {
		Organization org = organizationService.findUniqueById("1");
		this.recursive(org);
		modelMap.put("org",org);
		return "/account/organization/orgTree";
	}
	
	/**
	  * <p>组织机构递归查询</p>
	  * @Description:
	  * @param org
	 */
	private void recursive(Organization org) {
		List<Organization> subOrgList = organizationService.findSubOrgListById(org.getId());
		if(subOrgList != null && subOrgList.size()>0){
			org.getSubOrgList().addAll(subOrgList);
			for(Organization subOrg:subOrgList){
				this.recursive(subOrg);
			}
		}
	}
	
	/**
	  * <p>机构印章列表</p>
	  * @Description:
	  * @param orgId 组织机构ID
	  * @param modelMap
	  * @param request
	  * @return
	 */
	@RequestMapping("orgList/{orgId}")
	public String orgList(@PathVariable("orgId") String orgId,ModelMap modelMap,HttpServletRequest request) {
	   OrganizationPage<Organization> organizationPage = new OrganizationPage<Organization>();
       Page page = new Pagination();
       String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
       String numPerPage = request.getParameter("numPerPage");//每页显示条数
       if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		
		String orgName = request.getParameter("orgName");//机构名称
		if(StringUtils.isNotBlank(orgName)) {
			organizationPage.setOrgName("%"+orgName.trim()+"%");
		}
		organizationPage.setOrgId(orgId);
		organizationPage.setPageVO(page);
		organizationPage = organizationService.findPage(organizationPage);
		organizationPage.setOrgName(orgName);
		modelMap.put("organizationPage", organizationPage);
		modelMap.put("parentId", orgId);
		return "/account/organization/orgList";
	}
	
	/**
	  * <p>指向编辑表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{parentId}/{id}", method = RequestMethod.GET)
	public String toEditFormPage(@PathVariable("parentId") String parentId,@PathVariable("id") String id,ModelMap modelMap){
		Organization org = null;
		if(!"-1".equals(id)){//修改
			org = organizationService.findUniqueOrgMapById(id);
		}else{//新增
			org = new Organization();
			Organization parentOrg = organizationService.findUniqueById(parentId);
			org.setParentId(parentOrg.getId());
			org.setParentOrganization(parentOrg);
		}
		modelMap.put("org", org);
		return "/account/organization/orgForm";
	}
	
	/**
	  * <p>保存或更新机构表单信息</p>
	  * @Description:
	  * @param org
	  * @return
	  * @throws Exception
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(Organization org) throws Exception {
		Message message = new Message();
		if(StringUtils.isNotBlank(org.getId()))//更新本机构信息
			message.setContent(this.UPDATE);//内容提示
		else{//保存下级机构信息
			message.setContent(this.SAVE);//内容提示
		}
		organizationService.saveOrUpdate(org);
		message.setStatusCode(this.SUCCESS);
		message.setCallbackType("closeCurrent");
		message.setNavTabId("organization");
		return this.ajaxDone(message);
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id ,id主键
	  * @param model
	  * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "delete/{parentId}/{id}")
	public @ResponseBody String delete(@PathVariable("id") String id) throws Exception{
		organizationService.delOrgById(id);
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.DELETE);//内容提示
		message.setNavTabId("organization");
		return this.toJsonString(message);
	}
}
