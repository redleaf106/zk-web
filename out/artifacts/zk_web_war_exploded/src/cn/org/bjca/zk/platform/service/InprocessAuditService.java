package cn.org.bjca.zk.platform.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.zk.db.entity.InprocessAudit;
import cn.org.bjca.zk.platform.dao.InprocessAuditDao;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.page.InprocessAuditPage;

/***************************************************************************
 * <pre></pre>
 ***************************************************************************/

//Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class InprocessAuditService {
	
	@Autowired
	private InprocessAuditDao inprocessAuditDao;
	
	/**
	  * <p>保存或更新记录</p>
	  * @Description:
	  * @param inprocessAudit
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdate(InprocessAudit inprocessAudit){
		if(StringUtils.isNotBlank(inprocessAudit.getId())){//id不为空时
			inprocessAuditDao.update(inprocessAudit);
		}else{
			inprocessAudit.setId(EssPdfUtil.genrRandomUUID());
			inprocessAuditDao.save(inprocessAudit);
		}
	}
	
	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	public InprocessAudit findUniqueById(String id){
		return inprocessAuditDao.findUniqueById(id);
	}
	
	

	/**
	  * <p>分页查询</p>
	  * @Description:
	  * @param InprocessAuditPage
	  * @return
	 */
	public InprocessAuditPage<InprocessAudit> findPage(InprocessAuditPage<InprocessAudit> inprocessAuditPage) {
		List<InprocessAudit> list = inprocessAuditDao.findPage(inprocessAuditPage);
		inprocessAuditPage.setData(list);
		return inprocessAuditPage;
	}
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	public List<InprocessAudit> getAll() {
		return inprocessAuditDao.getAll();
	}


}
