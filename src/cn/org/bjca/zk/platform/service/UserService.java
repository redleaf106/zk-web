package cn.org.bjca.zk.platform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.dao.UserDao;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.UserPage;

/***************************************************************************
 * <pre></pre>
 ***************************************************************************/

//Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class UserService {
	
	@Autowired
	private UserDao userDao;//用户DAO
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param signKey
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(User user){
		if(StringUtils.isNotBlank(user.getId())){//id不为空时
			userDao.update(user);
		}else{
			user.setId(EssPdfUtil.genrRandomUUID());
			userDao.save(user);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public User findUniqueById(String id){
		return userDao.findUniqueById(id);
	}
	
	/**
	  * <p>根据机构ID查询对象集合</p>
	  * @Description:
	  * @param orgId
	  * @return
	 */
	public List<User> findUsersByOrgId(String orgId){
		return userDao.findUsersByOrgId(orgId);
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delUserById(String id){
		userDao.delUserById(id);
	}

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param userPage
	  * @return
	 */
	public UserPage<User> findPage(UserPage<User> userPage) {
		List<User> list =userDao.findPage(userPage);
		userPage.setData(list);
		return userPage;
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<User> getAll() {
		return userDao.getAll();
	}
	

	/**
	  * <p>通过登陆名获得证书序列号查询用户对象</p>
	  * @Description:
	  * @param userFieldsCheck
	  * @return
	 */
	public List<User> findUserByFields(String loginName, String serialNumber) {
		Map<String , String> userFieldsCheck = new HashMap<String, String>();
		userFieldsCheck.put("loginName", loginName);
		userFieldsCheck.put("serialNumber", serialNumber);
		return userDao.findUserByFields(userFieldsCheck);
	}
	
	
	/**
	  * <p>根据登录名和密码查询用户信息</p>
	  * @Description:
	  * @param loginName 用户登录名称
	  * @param pwd 用户密码
	  * @return
	 */
	public User findUniqueByLoginNameAndPwd(String loginName, String pwd) {
		return userDao.findUniqueByLoginNameAndPwd(loginName, pwd);
	}
	
	/**
	  * <p>通过登陆名获得证书序列号查询用户对象</p>
	  * @Description:
	  * @param loginName 用户登录名
	  * @param serialNumber 用户证书序列号
	  * @return
	 */
	public List<User> findByFields(String loginName, String serialNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginName", loginName);
		map.put("serialNumber", serialNumber);
		return userDao.findByFields(map);
	}

}
