/**
 * 
 */
package cn.org.bjca.zk.platform.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.CabinetDoor;
import cn.org.bjca.zk.platform.dao.CabinetDoorDao;
import cn.org.bjca.zk.platform.po.CabinetDoorPO;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.CabinetDoorPage;

/***************************************************************************

 * @文件名称: CabinetDoorService.java
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
public class CabinetDoorService {
	
	@Autowired
	private CabinetDoorDao cabinetDoorDao;

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param CabinetDoorPage
	  * @return
	 */
	public CabinetDoorPage<CabinetDoor> findPage(CabinetDoorPage<CabinetDoor> cabinetDoorPage){
		List<CabinetDoor> list =cabinetDoorDao.findPage(cabinetDoorPage);
		cabinetDoorPage.setData(list);
		return cabinetDoorPage;
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id
	 */
	@Transactional(readOnly = false)
	public void delCabinetDoorById(String id){
		cabinetDoorDao.delCabinetDoorById(id);
	}
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param role
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(CabinetDoor cabinetDoor){
		if(StringUtils.isNotBlank(cabinetDoor.getId())){//id不为空时
			cabinetDoorDao.update(cabinetDoor);
		}else{
			cabinetDoor.setId(EssPdfUtil.genrRandomUUID());
			cabinetDoorDao.save(cabinetDoor);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public CabinetDoor findUniqueById(String id){
		return cabinetDoorDao.findUniqueById(id);
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<CabinetDoor> getAll() {
		return cabinetDoorDao.getAll();
	}
	
	/**
	  * <p>根据机柜id编号查询列表</p>
	  * @Description:
	  * @param cabinetNumber 机柜编号
	  * @return
	 */
	public List<CabinetDoor> findByCabinetNumberAndDoorNumber( String cabinetNumber,String cabinetDoorNumber) {
		CabinetDoorPO cabinetDoorPO = new CabinetDoorPO();
		cabinetDoorPO.setCabinetNumber(cabinetNumber);
		cabinetDoorPO.setCabinetDoorNumber(cabinetDoorNumber);
		return cabinetDoorDao.findByCondition(cabinetDoorPO);
	}
}
