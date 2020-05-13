/**
 * 
 */
package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.*;
import cn.org.bjca.zk.platform.dao.CabinetDoorDao;
import cn.org.bjca.zk.platform.dao.CabinetDoorEventDao;
import cn.org.bjca.zk.platform.dao.DepartmentDao;
import cn.org.bjca.zk.platform.dao.EmployeeDao;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.CabinetDoorEventPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***************************************************************************

 * @文件名称: CabinetDoorEvnetService.java
 * @包   路   径： cn.org.bjca.zk.platform.service
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/
@Component
@Transactional(readOnly = true)
public class CabinetDoorEventService {
	
	@Autowired
	private CabinetDoorEventDao cabinetDoorEventDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CabinetDoorDao cabinetDoorDao;

	@Autowired
	private DepartmentDao departmentDao;

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param cabinetDoorEventPage
	  * @return
	 */
	public CabinetDoorEventPage<CabinetDoorEvent> findPage(CabinetDoorEventPage<CabinetDoorEvent> cabinetDoorEventPage){
		List<CabinetDoorEvent> list =cabinetDoorEventDao.findPage(cabinetDoorEventPage);
		cabinetDoorEventPage.setData(list);
		return cabinetDoorEventPage;
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delCabinetDoorEventById(String id){
		cabinetDoorEventDao.delCabinetDoorEventById(id);
	}
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param cabinetDoorEvent
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(CabinetDoorEvent cabinetDoorEvent){
		if(StringUtils.isNotBlank(cabinetDoorEvent.getId())){//id不为空时
		}else{
			cabinetDoorEvent.setId(EssPdfUtil.genrRandomUUID());
			cabinetDoorEventDao.save(cabinetDoorEvent);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public CabinetDoorEvent findUniqueById(String id){
		return cabinetDoorEventDao.findUniqueById(id);
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<CabinetDoorEvent> getAll() {
		return cabinetDoorEventDao.getAll();
	}

	/**
	 * <p>查询当天的记录</p>
	 * @param date
	 * @return
	 */
	public List<CabinetDoorEvent> findOneDay(Date date){
		return cabinetDoorEventDao.findOneDay(date);
	}

	/**
	 * 查询某位员工当某天的事件
	 * @param employeeIcCardNumber
	 * @param date
	 * @return
	 */
	public List<CabinetDoorEvent> findOneEMPByOneDay(String employeeIcCardNumber, Date date){
		return cabinetDoorEventDao.findOneEMPByOneDay(employeeIcCardNumber,date);
	}

	//查看当天报表
	public List<CheckInfo> findDayInfo(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return cabinetDoorEventDao.findDayInfo(simpleDateFormat.format(date));
	}

	//紧急事件记录
	@Transactional(readOnly = false)
	public void insertUrgentEvent(UrgentEvent urgentEvent){
		cabinetDoorEventDao.insertUrgentEvent(urgentEvent);
	}

	@Transactional(readOnly = false)
	public List<UrgentEvent> getAllUnactivatedUrgentEvent(){
		return cabinetDoorEventDao.getAllUnactivatedUrgentEvent();
	}

	@Transactional(readOnly = false)
	public int updateUrgentEventEmailStatus(int id){
		return cabinetDoorEventDao.updateUrgentEventEmailStatus(id);
	}

	public List<CheckInfo> absentEmp(Date date){
		List<Employee> employeeList = employeeDao.getAll();
		List<CheckInfo> checkInfoArrayList = new ArrayList<>();
		for(Employee e:employeeList){
			List<CabinetDoorEvent> cabinetDoorEventList = cabinetDoorEventDao.findOneEMPByOneDay(e.getIcCardNumber(),date);
			if(cabinetDoorEventList==null||cabinetDoorEventList.size()==0){
				CheckInfo checkInfo = new CheckInfo();
				checkInfo.setIcCardNumber(e.getIcCardNumber());
				checkInfo.setRemark("未交手机");
				checkInfo.setEmployeeName(e.getEmployeeName());
				CabinetDoor cabinetDoor = cabinetDoorDao.selectDoorByEmployeeId(e.getId());
				if(cabinetDoor!=null){
					checkInfo.setCabinetDoorNumber(cabinetDoor.getCabinetDoorNumber());
				}
				checkInfo.setDepartmentName(departmentDao.findUniqueById(e.getDepartmentId()).getDepartmentName());
				checkInfoArrayList.add(checkInfo);
			}
		}
		return checkInfoArrayList;
	}



}
