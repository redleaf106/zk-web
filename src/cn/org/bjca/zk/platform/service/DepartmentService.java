/**
 * 
 */
package cn.org.bjca.zk.platform.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.Department;
import cn.org.bjca.zk.db.entity.TimeArea;
import cn.org.bjca.zk.platform.dao.DepartmentDao;
import cn.org.bjca.zk.platform.dao.TimeAreaDao;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.DepartmentPage;

/***************************************************************************

 * @文件名称: DepartmentService.java
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
public class DepartmentService {
	
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private TimeAreaDao timeAreaDao;

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param departmentPage
	  * @return
	 */
	public DepartmentPage<Department> findPage(DepartmentPage<Department> departmentPage){
		List<Department> list =departmentDao.findPage(departmentPage);
		departmentPage.setData(list);
		return departmentPage;
	}
	
	/**
	  * <p>查询所有组织机构列表</p>
	  * @Description:
	  * @return
	 */
	public List<Department> getAll(){
		return departmentDao.getAll();
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delDepartmentById(String id){
		timeAreaDao.delTimeAreaByDepartmentId(id);
		departmentDao.delDepartmentById(id);
	}
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param department
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(Department department) throws Exception{
		if(StringUtils.isNotBlank(department.getId())){//id不为空时
			//删除原时间区域
			timeAreaDao.delTimeAreaByDepartmentId(department.getId());
			List<String> startTimeList = department.getStartTime();
			List<String> endTimeList = department.getEndTime();
			List<TimeArea> timeAreaList = department.getTimeAreas();
			if(null!= startTimeList && null!=endTimeList && startTimeList.size()==endTimeList.size()) {
				for(int i=0;i<startTimeList.size();i++) {
					TimeArea timeArea = new TimeArea();
					timeArea.setId(EssPdfUtil.genrRandomUUID());
					timeArea.setDepartmentId(department.getId());
					SimpleDateFormat sdfStart = new SimpleDateFormat("HH:mm:ss");
					Date startTimeDate = sdfStart.parse(startTimeList.get(i));
					timeArea.setStartTime(new Timestamp(startTimeDate.getTime()));
					SimpleDateFormat sdfEnd = new SimpleDateFormat("HH:mm:ss");
					Date endTimeDate = sdfEnd.parse(endTimeList.get(i));
					timeArea.setEndTime(new Timestamp(endTimeDate.getTime()));
					timeArea.setUserId(department.getUserId());
					timeArea.setOptTime(new Timestamp(new Date().getTime()));
					timeAreaList.add(timeArea);
				}
			}
			
			//添加新的时间区域
			if(null!=timeAreaList && !timeAreaList.isEmpty()) {
				timeAreaDao.batchInsert(timeAreaList);
			}
			departmentDao.update(department);
		}else{
			department.setId(EssPdfUtil.genrRandomUUID());
			List<String> startTimeList = department.getStartTime();
			List<String> endTimeList = department.getEndTime();
			List<TimeArea> timeAreaList = department.getTimeAreas();
			if(null!= startTimeList && null!=endTimeList && startTimeList.size()==endTimeList.size()) {
				for(int i=0;i<startTimeList.size();i++) {
					TimeArea timeArea = new TimeArea();
					timeArea.setId(EssPdfUtil.genrRandomUUID());
					timeArea.setDepartmentId(department.getId());
					SimpleDateFormat sdfStart = new SimpleDateFormat("HH:mm:ss");
					Date startTimeDate = sdfStart.parse(startTimeList.get(i));
					timeArea.setStartTime(new Timestamp(startTimeDate.getTime()));
					SimpleDateFormat sdfEnd = new SimpleDateFormat("HH:mm:ss");
					Date endTimeDate = sdfEnd.parse(endTimeList.get(i));
					timeArea.setEndTime(new Timestamp(endTimeDate.getTime()));
					timeArea.setUserId(department.getUserId());
					timeArea.setOptTime(new Timestamp(new Date().getTime()));
					timeAreaList.add(timeArea);
				}
			}
			
			//添加新的时间区域
			if(null!=timeAreaList && !timeAreaList.isEmpty()) {
				timeAreaDao.batchInsert(timeAreaList);
			}
			departmentDao.save(department);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public Department findUniqueById(String id){
		return departmentDao.findUniqueById(id);
	}
	
	/**
	  * <p>根据部门印章名称查询列表</p>
	  * @Description:
	  * @param depatmentName 印章名称
	  * @return
	 */
	public List<Department> findByDepartmentName(String depatmentName) {
		return departmentDao.findByDepartmentName(depatmentName);
	}
}
