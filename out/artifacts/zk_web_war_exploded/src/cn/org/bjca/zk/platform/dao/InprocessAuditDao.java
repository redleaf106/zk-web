package cn.org.bjca.zk.platform.dao;

import java.util.List;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.InprocessAudit;
import cn.org.bjca.zk.platform.web.page.InprocessAuditPage;


@MyBatisRepository
public interface InprocessAuditDao {
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param inprocessAudit
	 */
	void save(InprocessAudit inprocessAudit);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param inprocessAudit
	 */
	void update(InprocessAudit inprocessAudit);

	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_INPROCESSAUDIT WHERE id = #{id}")
	InprocessAudit findUniqueById(String id);
	
	

	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */

	List<InprocessAudit> findPage(InprocessAuditPage<InprocessAudit> webPage);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_INPROCESSAUDIT")
	List<InprocessAudit> getAll();
	
	

}
