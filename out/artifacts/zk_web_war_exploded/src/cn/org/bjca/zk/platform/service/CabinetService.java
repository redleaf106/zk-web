/**
 * 
 */
package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.Cabinet;
import cn.org.bjca.zk.platform.dao.CabinetDao;
import cn.org.bjca.zk.platform.po.CabinetPO;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.CabinetPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***************************************************************************

 * @文件名称: CabinetService.java
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
public class CabinetService {
	
	@Autowired
	private CabinetDao cabinetDao;

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param CabinetPage
	  * @return
	 */
	public CabinetPage<Cabinet> findPage(CabinetPage<Cabinet> cabinetPage){
		List<Cabinet> list =cabinetDao.findPage(cabinetPage);
		cabinetPage.setData(list);
		return cabinetPage;
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delCabinetById(String id){
		cabinetDao.delCabinetById(id);
	}
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param role
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(Cabinet cabinet){
		if(StringUtils.isNotBlank(cabinet.getId())){//id不为空时
			cabinetDao.update(cabinet);
		}else{
			cabinet.setId(EssPdfUtil.genrRandomUUID());
			cabinetDao.save(cabinet);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public Cabinet findUniqueById(String id){
		return cabinetDao.findUniqueById(id);
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<Cabinet> getAll() {
		return cabinetDao.getAll();
	}
	
	/**
	  * <p>根据条件查询机柜列表</p>
	  * @Description:
	  * @param cabinetPO 机柜编号
	  * @return
	 */
	public List<Cabinet> findByConditon(CabinetPO cabinetPO) {
		return cabinetDao.findByCondition(cabinetPO);
	}

	public Cabinet findByIp(String ip){
		return cabinetDao.findByIP(ip);
	}

	public Cabinet findByCabinetNumber(String cabinetNumber){
		return cabinetDao.findByCabinetNumber(cabinetNumber);
	}
}
