package cn.org.bjca.zk.platform.dao;

import cn.org.bjca.zk.db.entity.UrgentReason;
import cn.org.bjca.zk.platform.web.page.UrgentReasonPage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MyBatisRepository
public interface UrgentReasonDao {

    @Insert("INSERT INTO BO_URGENTREASON(reason,type,opttime) VALUES(#{reason},#{type},now())")
    int insert(UrgentReason urgentReason);

    int update(UrgentReason urgentReason);
    @Delete("DELETE FROM BO_URGENTREASON WHERE ID = #{0}")
    int delete(int id);
    @Select("SELECT * FROM BO_URGENTREASON")
    List<UrgentReason> findAllUrgentReason();
    @Select("SELECT * FROM BO_URGENTREASON WHERE TYPE = #{0}")
    List<UrgentReason> findAllUrgentReasonByType(int type);

    List<UrgentReason> findPage(UrgentReasonPage<UrgentReason> webPage);
    @Select("SELECT * FROM BO_URGENTREASON WHERE ID = #{0}")
    UrgentReason findById(int id);
}
