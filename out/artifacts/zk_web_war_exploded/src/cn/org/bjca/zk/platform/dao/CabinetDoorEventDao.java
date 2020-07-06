/**
 * 
 */
package cn.org.bjca.zk.platform.dao;

import cn.org.bjca.zk.db.entity.CabinetDoorEvent;
import cn.org.bjca.zk.db.entity.CheckInfo;
import cn.org.bjca.zk.db.entity.UrgentEvent;
import cn.org.bjca.zk.platform.web.page.CabinetDoorEventPage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


/***************************************************************************

 * @文件名称: CabinetDoorEventDao.java
 * @包   路   径： cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/

@MyBatisRepository
public interface CabinetDoorEventDao {
	
	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */
	List<CabinetDoorEvent> findPage(CabinetDoorEventPage<CabinetDoorEvent> webPage);
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Delete("DELETE FROM BO_CABINETDOOREVENT WHERE id = #{id}")
	void delCabinetDoorEventById(String id);
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param cabinetDoorEvent
	 */
	void save(CabinetDoorEvent cabinetDoorEvent);
	
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_CABINETDOOREVENT WHERE id = #{id}")
	CabinetDoorEvent findUniqueById(String id);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_CABINETDOOREVENT")
	List<CabinetDoorEvent> getAll();

	/**
	 *
	 * @param date
	 * @return
	 */
	List<CabinetDoorEvent> findOneDay(Date date);

	/**
	 *
	 * @param employeeIcCardNumber
	 * @param date
	 * @return
	 */
	List<CabinetDoorEvent> findOneEMPByOneDay(String employeeIcCardNumber, Date date);

	List<CheckInfo> findDayInfo(String today);

	void insertUrgentEvent(UrgentEvent urgentEvent);

	List<UrgentEvent> getAllUnactivatedUrgentEvent();

	int updateUrgentEventEmailStatus(int id);



}
