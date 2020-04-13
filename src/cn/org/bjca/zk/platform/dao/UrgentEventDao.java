package cn.org.bjca.zk.platform.dao;

import cn.org.bjca.zk.db.entity.UrgentEvent;

@MyBatisRepository
public interface UrgentEventDao {
    void save(UrgentEvent urgentEvent);
}
