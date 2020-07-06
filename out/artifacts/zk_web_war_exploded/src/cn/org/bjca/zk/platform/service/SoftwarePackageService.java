package cn.org.bjca.zk.platform.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.db.entity.SoftwarePackage;
import cn.org.bjca.zk.platform.dao.EmployeeDao;
import cn.org.bjca.zk.platform.dao.SoftwarePackageDao;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.EmployeePage;
import cn.org.bjca.zk.platform.web.page.SoftwarePackagePage;

/***************************************************************************
 * <pre></pre>
 ***************************************************************************/

//Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class SoftwarePackageService {
	/**
	 * 软件包dao
	 */
	@Autowired
	private SoftwarePackageDao softwarePackageDao;
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param employee
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(SoftwarePackage softwarePackage){
		if(StringUtils.isNotBlank(softwarePackage.getId())){//id不为空时
			softwarePackageDao.update(softwarePackage);
		}else{
			softwarePackage.setId(EssPdfUtil.genrRandomUUID());
			softwarePackageDao.save(softwarePackage);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public SoftwarePackage findUniqueById(String id){
		return softwarePackageDao.findUniqueById(id);
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delSoftwarePackageById(String id){
		softwarePackageDao.delSoftwarePackageById(id);
	}

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param softwarePackagePage
	  * @return
	 */
	public SoftwarePackagePage<SoftwarePackage> findPage(SoftwarePackagePage<SoftwarePackage> softwarePackagePage) {
		List<SoftwarePackage> list = softwarePackageDao.findPage(softwarePackagePage);
		softwarePackagePage.setData(list);
		return softwarePackagePage;
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<SoftwarePackage> getAll() {
		return softwarePackageDao.getAll();
	}

	/**
	 * 获取最新版本
	 */
	public SoftwarePackage getLatestSoftwarePackage() {
		return softwarePackageDao.getLatestSoftwarePackage();
	}

}
