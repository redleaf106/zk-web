package cn.org.bjca.zk.platform.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.org.bjca.zk.db.entity.Menu;
import cn.org.bjca.zk.db.entity.RoleMenu;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.ErrorCodeConstants;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.service.MenuService;
import cn.org.bjca.zk.platform.service.RoleMenuService;
import cn.org.bjca.zk.platform.service.UserService;
import cn.org.bjca.zk.platform.utils.DateUtil;
import cn.org.bjca.zk.platform.utils.PDFSealUtil;

/***************************************************************************
 * <pre>登录管理</pre>
 ***************************************************************************/
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	
	protected static final String LOGIN_NAME_PARAM_NAME = "loginName"; // 登录名参数名
	
	protected static final String SESSION_SCOPERANDOM_PARAM_NAME = "scope_session"; // 会话随机数参数名
	
//	protected static final String LOGIN_RANDOM_PARAM_NAME = "verifyCode"; // 登录随机数参数名
	
	protected static final String LOGIN_PWD_PARAM_NAME = "pwd"; // 登录密码参数名
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleMenuService roleMenuService;
	
	
	/**
	  * <p>转向登录页面</p>
	  * @Description:
	  * @param request
	  * @param modelMap
	  * @return
	  * @throws Exception 
	 */
	@RequestMapping(value = "toLogin")
	public String toLoginPage(HttpServletRequest request, ModelMap modelMap) throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute(PDFSealConstants.SESSION_USER) != null) {
			User user = (User) session.getAttribute(PDFSealConstants.SESSION_USER);
			return toMainPage(modelMap, request, user);
		} else {
			// ********************session漏洞修复 start
			// 更新会话标识，不接受在登录时由用户的浏览器提供的会话标识；始终生成新会话以供用户在成功认证后登录。
			// 在对新用户会话授权之前废除任何现有会话标识。
			session = initSession(session, request);
			return "/login";
		}
	}
	
	
	/**
	  * <p>用户登录</p>
	  * @Description:
	  * @param modelMap
	  * @param request
	  * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String login(ModelMap modelMap,HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String sessionUser = this.filterSafeStringXSS(PDFSealConstants.SESSION_USER);
		User user = (User)session.getAttribute(sessionUser);
		if (user != null) {
			return toMainPage(modelMap, request, user);
		}
		
        String loginType = filterSafeStringXSS(request.getParameter(PDFSealConstants.LOGIN_TYPE_PARAMNAME));
		if (PDFSealConstants.LOGIN_TYPE_PWD.equals(loginType)) { // 用户名密码认证
			String loginName = getXssParameter(request, LOGIN_NAME_PARAM_NAME);
			String loginPcode = getXssParameter(request, LOGIN_PWD_PARAM_NAME);
			user = userService.findUniqueByLoginNameAndPwd(loginName, PDFSealUtil.digest(loginPcode));
		} else {
			throw new Exception(String.format("unsupport login type %s" + loginType))  ;
		}
		session = initSession(session, request);
		session.setAttribute(PDFSealConstants.SESSION_USER, user); // 设置seesion用户
		return  toMainPage(modelMap,request,user);
	}
	
	/**
	  * <p>跳转到主页面</p>
	  * @Description:
	  * @param modelMap
	  * @param request
	  * @param user
	  * @return
	  * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toMainPage(ModelMap modelMap, HttpServletRequest request, User user) throws Exception {
	
		
		// ************************************授权菜单
		List<Menu> menuList = null;
		// 1:为超级用户，2：为普通用户
		if (PDFSealConstants.USER_TYPE_SUPER.equals(user.getUserType()) && StringUtils.isBlank(user.getRoleId())) {
			menuList = menuService.getAllByParentId(ROOT_MENU_TYPE); // 获取所有菜单
		} else { // 普通用户登录
			menuList = menuService.getAllParentList(); // 父节点菜单列表
			List<RoleMenu> authMenuList = roleMenuService.findMapsByRoleId(user.getRoleId()); // 获取授权菜单列表
			for (RoleMenu roleMenu : authMenuList) { // 添加用户授权菜单
				Menu menuObj = roleMenu.getMenu();
				for (Menu obj : menuList) {
					if (obj.getId().equals(menuObj.getParentId())) {
						obj.getSubMenuList().add(menuObj);
						break;
					}
				}
			}
			List<Menu> freeMenu = new ArrayList<Menu>(); // 空闲菜单列表
			for (Menu obj : menuList) { // 统计父节点没有子菜单的对象
				if (obj.getSubMenuList().size() == 0) {
					freeMenu.add(obj);
				} else { // 根据显示顺序，进行对象排序
					Collections.sort(obj.getSubMenuList(), new Comparator() {
						public int compare(Object o1, Object o2) {
							String one = ((Menu) o1).getShowOrder();
							String two = ((Menu) o2).getShowOrder();
							return Integer.parseInt(one) - Integer.parseInt(two);
						}
					});
				}
			}
			menuList.removeAll(freeMenu); // 删除没有授权的菜单
		}
		modelMap.put("menu", menuList);
		
		return "/main";
	}
	
	
	/**
	  * <p>退出系统</p>
	  * @Description:
	  * @param session
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	 */
	@RequestMapping(value = "exit")
	public String exit(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		session.invalidate(); // session 销毁
		return "/exitPage";
	}
	
	// 获取系统当前时间
		@RequestMapping(value = "getCurrTime", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
		@ResponseBody
		public String getCurrTime() {
			String currTime = DateUtil.getCurrTime(new Date());
			currTime = currTime.replaceAll("Monday", "星期一");
			currTime = currTime.replaceAll("Tuesday", "星期二");
			currTime = currTime.replaceAll("Wednesday", "星期三");
			currTime = currTime.replaceAll("Thursday", "星期四");
			currTime = currTime.replaceAll("Friday", "星期五");
			currTime = currTime.replaceAll("Saturday", "星期六");
			currTime = currTime.replaceAll("Sunday", "星期日");
			return currTime;
		}
		
		
	/**
	  * <p>用户身份认证</p>
	  * @Description:
	  * @param request
	  * @param session
	  * @return
	  * @throws Exception
	 */
	@RequestMapping(value = "ajaxAuth", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String ajaxAuth(HttpServletRequest request, HttpSession session) throws Exception {
		return pwdAuth(getXssParameter(request, LOGIN_NAME_PARAM_NAME), getXssParameter(request, LOGIN_PWD_PARAM_NAME), session);
	}
	
	/**
	  * <p>用户口令身份认证</p>
	  * @Description:
	  * @param session
	  * @return
	  * @throws Exception
	 */
	private String pwdAuth(String loginName, String pwd, HttpSession session) throws Exception {
		String randomCode = (String) session.getAttribute(SESSION_SCOPERANDOM_PARAM_NAME); // 验证码session
//		if (!verifyCode.equals(randomCode)) {
//			return "验证码输入错误，请重新输入！";
//		}
		List<User> userList = userService.findByFields(loginName, null);
		if (userList.size() > 0) {
			for(User userObj : userList) {
				if (PDFSealUtil.digest(pwd).equals(userObj.getPwd())) {
					return ErrorCodeConstants.SUCCESS; // 200
				}
			}
			return "密码错误，请重新输入！";
		}else {
			return "不存在该账号，请输入正确的登录名！";
		}
		
	}
	
	private HttpSession initSession(HttpSession session, HttpServletRequest request) {
		// 更新会话标识，不接受在登录时由用户的浏览器提供的会话标识；始终生成新会话以供用户在成功认证后登录。在对新用户会话授权之前废除任何现有会话标识。
		if (session != null && session.getId() != null) {
			try {
				session.invalidate();
			} catch (Exception e) {
				logger.warn("会话失效异常：" + e.getMessage(), e);
			}
			Cookie[] cookie = request.getCookies();
			if (cookie != null) {
				cookie[0].setMaxAge(0);
			}
		}
		// 创建新的会话
		return request.getSession(true);
	}

}
