package cn.org.bjca.zk.platform.web.controller.account;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;

import cn.org.bjca.zk.db.entity.Menu;
import cn.org.bjca.zk.db.entity.Role;
import cn.org.bjca.zk.db.entity.RoleMenu;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.MenuService;
import cn.org.bjca.zk.platform.service.RoleMenuService;
import cn.org.bjca.zk.platform.service.RoleService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.RolePage;

/***************************************************************************
 * <pre>角色管理</pre>
 * @文件名称:  RoleController.java
 * @包   路   径：  cn.org.bjca.zk.platform.web.controller.account
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： wangwenc
 * @创建时间：2013-2-16 下午7:25:32
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
@RequestMapping(value = "/account/role")
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;//角色服务管理
	
	@Autowired
	private MenuService menuService;//功能菜单服务管理
	
	@Autowired
	private RoleMenuService roleMenuService;//角色菜单服务管理
	
	/**
	  * <p>角色管理列表</p>
	  * @Description:
	  * @param model
	  * @param request
	  * @return
	 */
	@RequestMapping("")
	public String list(ModelMap modelMap,HttpServletRequest request) {
		RolePage<Role> rolePage = new RolePage<Role>();
        Page page = new Pagination();
        String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		
		String roleName = request.getParameter("roleName");//角色名称
		if(StringUtils.isNotBlank(roleName)) {
			rolePage.setRoleName("%"+roleName.trim()+"%");
		}
		
		rolePage.setPageVO(page);
		rolePage = roleService.findPage(rolePage);
		rolePage.setRoleName(roleName);
		modelMap.put("rolePage", rolePage);
		return "/account/role/roleList";
	}
	
	/**
	  * <p>指向编辑表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{id}", method = RequestMethod.GET)
	public String toEditFormPage(@PathVariable String id,ModelMap modelMap) throws Exception {
		Role role = null;
		if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
			role = roleService.findUniqueById(id);
		}else {
			role = new Role();
		}
		modelMap.put("role",role);
		return "/account/role/roleForm";
	}
	
	/**
	  * <p>保存或更新表单信息</p>
	  * @Description:
	  * @param signKey
	  * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(Role role,HttpServletRequest request) throws DialogException {
		try{
			Message message = new Message();
			if(StringUtils.isNotBlank(role.getId()))
				message.setContent(this.UPDATE);//内容提示
			else{
				message.setContent(this.SAVE);//内容提示
				List<Role> list = roleService.findByRoleName(role.getRoleName());
				if(list != null && list.size() > 0){
					message.setContent("角色名称已存在，请重新输入!");//内容提示
					message.setStatusCode(this.FAIL);
					return this.ajaxDone(message);
				}
			}
			User user = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
			role.setUserId(user.getId());
			role.setOrgFlag(user.getOrgFlag());
			role.setOrgId(user.getOrgId());
			roleService.saveOrUpdate(role);
			message.setStatusCode(this.SUCCESS);
			message.setCallbackType("closeCurrent");
			message.setNavTabId("role");
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
			roleService.delRoleById(id);
			Message message = new Message();
			message.setStatusCode(this.SUCCESS);
			message.setContent(this.DELETE);//内容提示
			return this.toJsonString(message);
		}catch(Exception ex){
			throw new DialogException(ex);
		}
	}
	
	/**
	  * <p>指向选择功能菜单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toSelMenuPage/{roleId}", method = RequestMethod.GET)
	public String toSelMenuPage(@PathVariable("roleId") String roleId,ModelMap modelMap) throws Exception {
		List<RoleMenu> roleMenuList = roleMenuService.findByRoleId(roleId);//根据角色id，查询所授权的菜单
		List<String> menuIdList = new ArrayList<String>();
		for(RoleMenu roleMenu:roleMenuList){
			menuIdList.add(roleMenu.getMenuId());
		}
		List<Menu> subMenuList = menuService.getAllByParentId("-1");//-1表示从根节点开始查找
		Menu menu = new Menu();
		menu.setId("-1");
		menu.setMenuName("系统主菜单");
		menu.setParentId("-1");
		menu.setSubMenuList(subMenuList);
		modelMap.put("menu", menu);
		modelMap.put("roleId", roleId);
		modelMap.put("menuIdList", menuIdList);
		return "/account/role/menuTree";
	}
	
	/**
	  * <p>角色授权</p>
	  * @Description:
	  * @param menuIds
	  * @return
	  * @throws Exception
	 */
	@RequestMapping(value = "roleAuth")
	public ModelAndView roleAuth(@RequestParam("roleId") String roleId, @RequestParam("menuIds") String[] menuIds) throws Exception {
		roleMenuService.delRoleMenuByRoleId(roleId);
		RoleMenu roleMenu = null;
		for(String menuId:menuIds){
			if(!"-1".equals(menuId)) {
				roleMenu = new RoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuId);
				roleMenuService.save(roleMenu);
			}
		}
		Message message = new Message();
		message.setContent("角色授权成功！");
		message.setStatusCode(this.SUCCESS);
		message.setCallbackType("closeCurrent");
		return this.ajaxDone(message);
	}

}
