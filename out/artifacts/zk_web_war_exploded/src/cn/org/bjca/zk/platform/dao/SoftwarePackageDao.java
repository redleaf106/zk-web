package cn.org.bjca.zk.platform.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import cn.org.bjca.zk.db.entity.SoftwarePackage;
import cn.org.bjca.zk.platform.web.page.SoftwarePackagePage;


@MyBatisRepository
public interface SoftwarePackageDao {
	
	/**
	  * <p>保存记录</p>
	  * @Description:
	  * @param softwarePackage
	 */
	void save(SoftwarePackage softwarePackage);
	
	/**
	  * <p>更新记录</p>
	  * @Description:
	  * @param softwarePackage
	 */
	void update(SoftwarePackage softwarePackage);

	/**
	  * <p>根据ID查询对象</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Select("SELECT * FROM BO_SOFTWAREPACKAGE WHERE id = #{id}")
	SoftwarePackage findUniqueById(String id);
	
	/**
	  * <p>删除软件包</p>
	  * @Description:
	  * @param id
	  * @return
	 */
	@Delete("DELETE FROM BO_SOFTWAREPACKAGE WHERE id = #{id}")
	void delSoftwarePackageById(String id);

	/**
	  * <p>获取所有列表/p>
	  * @Description:
	  * @return
	 */

	List<SoftwarePackage> findPage(SoftwarePackagePage<SoftwarePackage> webPage);
	
	/**
	  * <p>查询所有列表</p>
	  * @Description:
	  * @return
	 */
	@Select("SELECT * FROM BO_SOFTWAREPACKAGE")
	List<SoftwarePackage> getAll();
	
	
	@Select("SELECT id ,`name`,`version`, `updateStatus`,`size`,digest,content FROM BO_SOFTWAREPACKAGE ORDER BY `VERSION` DESC LIMIT 1")
	SoftwarePackage getLatestSoftwarePackage();

}
