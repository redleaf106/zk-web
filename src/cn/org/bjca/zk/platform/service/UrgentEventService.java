package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.UrgentEvent;
import cn.org.bjca.zk.platform.dao.UrgentEventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UrgentEventService
 * @Author redleaf
 * @Date 2020/4/9 20:01
 **/
@Component
@Transactional(readOnly = true)
public class UrgentEventService {

    @Autowired
    private UrgentEventDao urgentEventDao;

    //紧急事件记录
    public void save(UrgentEvent urgentEvent){
        urgentEventDao.save(urgentEvent);
    }
}
