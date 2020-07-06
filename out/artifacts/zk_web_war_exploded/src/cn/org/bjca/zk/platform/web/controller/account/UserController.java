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
import cn.org.bjca.zk.db.entity.Role;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.service.OrganizationService;
import cn.org.bjca.zk.platform.service.RoleService;
import cn.org.bjca.zk.platform.service.UserService;
import cn.org.bjca.zk.platform.utils.PDFSealUtil;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.UserPage;

/***************************************************************************
 * <pre>用户管理</pre>
 * @文件名称:  UserController.java
 * @包   路   径：  cn.org.bjca.zk.platform.web.controller.account
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： tangyuhua
 * @创建时间：2013-2-15 下午10:54:44
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
@RequestMapping(value = "/account/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value ="")
	public String list( ModelMap modelMap, HttpServletRequest request) {
		UserPage<User> userPage = new UserPage<User>();
        Page page = new Pagination();
        String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		
		String userName = request.getParameter("userName");//用户名称
		if(StringUtils.isNotBlank(userName)) {
			userPage.setUserName("%"+userName.trim()+"%");
		}
		
		userPage.setPageVO(page);
		userPage = userService.findPage(userPage);
		userPage.setUserName(userName);
		
		modelMap.put("userPage", userPage);
		return "/account/user/userList";
	}
	
	/**
	  * <p>机构列表</p>
	  * @Description:
	  * @param model
	  * @return
	 */
	@RequestMapping("orgList")
	public String orgList(ModelMap modelMap) {
		Organization org = organizationService.findUniqueById("1");
		this.recursive(org);
		modelMap.put("org",org);
		return "/account/user/orgTree";
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
	  * <p>指向用户表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{userId}", method = RequestMethod.GET)
	public String toEditFormPage( @PathVariable("userId") String userId, ModelMap modelMap){	
		User user = null;
		if(StringUtils.isNotBlank(userId)&& !BLANK_PARAM_VALUE.equals(userId)){
			user = userService.findUniqueById(userId);
		}else{
			user = new User();
		}
		modelMap.put("user", user);
		
		List<Role> roleList = roleService.getAll(); // 获取当前机构下角色和初始化的角色列表
		modelMap.put("roleList", roleList); // 角色列表
		return "/account/user/userForm";
	}
	
	/**
	  * <p>保存或更新表单信息</p>
	  * @Description:
	  * @param user
	  * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate( User user,HttpServletRequest request) throws Exception {
		Message message = new Message();
				
		if(StringUtils.isNotBlank(user.getId()))
			message.setContent(this.UPDATE);//内容提示
		else{			
			message.setContent(this.SAVE);//内容提示
			//用户登录名查重
			List<User> users = userService.findUserByFields(user.getLoginName(), user.getSerialNumber());
			if(users != null && users.size() != 0){
				message.setContent("登陆名重复!");//内容提示
				message.setStatusCode(this.FAIL);
				return this.ajaxDone(message);
			}
			String digestPwd = PDFSealUtil.digest(user.getPwd());
			user.setPwd(digestPwd);
		}
		
		
		
		User loginUser = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
		user.setUserId(loginUser.getId());
		user.setOrgFlag(loginUser.getOrgFlag());
		user.setOrgId(loginUser.getOrgId());
		
		userService.saveOrUpdate(user);
		message.setStatusCode(this.SUCCESS);
		message.setCallbackType("closeCurrent");
		message.setNavTabId("user");
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
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody String delete(@PathVariable("id") String id) throws Exception{
		userService.delUserById(id);
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.DELETE);//内容提示
//		message.setRel("userBox");
		return this.toJsonString(message);
	}
}

