package cn.org.bjca.zk.platform.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.web.page.UserPage;

/***************************************************************************
 * <pre></pre>
 * @文件名称:  UserDao.java
 * @包   路   径：  cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2013
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： tangyuhua
 * @创建时间：2013-3-19 上午11:21:07
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

@MyBatisRepository
public interface UserDao {
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param signKey
	 */
	void save(User user);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param signKey
	 */
	void update(User User);

	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_USER WHERE id = #{id}")
	User findUniqueById(String id);
	
	/**
	  * <p>根据ORGID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_USER WHERE ORGID = #{id}")
	List<User> findUsersByOrgId(String id);
	
	/**
	  * <p>根据ORGID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Delete("DELETE FROM BO_USER WHERE id = #{id}")
	void delUserById(String id);

	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */

	List<User> findPage(UserPage<User> webPage);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_USER")
	List<User> getAll();
	
	/**
	  * <p>通过登陆名获得证书序列号查询用户对象</p>
	  * @Description:
	  * @param userFieldsCheck
	  * @return
	 */
	List<User> findUserByFields(Map<String, String> userFieldsCheck);
	
	/**
	  * <p>通过登陆名获得证书序列号查询用户对象</p>
	  * @Description:
	  * @param map
	  * @return
	 */
	List<User> findByFields(Map<String, String> map);
	
	/**
	  * <p>>根据登录名和密码查询用户信息</p>
	  * @Description:
	  * @param loginName 用户登录名
	  * @param pwd 用户密码
	  * @return
	 */
	@Select("SELECT * FROM BO_USER WHERE LOGINNAME=#{loginName} AND PWD=#{pwd}")
	User findUniqueByLoginNameAndPwd(@Param(value = "loginName") String loginName, @Param(value = "pwd") String pwd);

}
