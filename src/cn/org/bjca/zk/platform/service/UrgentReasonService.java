package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.UrgentReason;
import cn.org.bjca.zk.platform.dao.UrgentReasonDao;
import cn.org.bjca.zk.platform.web.page.UrgentReasonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName UrgentReasonService
 * @Author redleaf
 * @Date 2020/6/9 11:33
 **/
@Component
public class UrgentReasonService {

    @Autowired
    private UrgentReasonDao urgentReasonDao;

    public int saveOrUpdate(UrgentReason reason){
        int result = 0;
        if(reason.getId() != 0){
            result = urgentReasonDao.update(reason);
        }else {
            result = urgentReasonDao.insert(reason);
        }
        return result;
    }

    public List<UrgentReason> findAllUrgent(){
        return urgentReasonDao.findAllUrgentReason();
    }

    public List<UrgentReason> findAllUrgent(int type){
        return urgentReasonDao.findAllUrgentReasonByType(type);
    }

    public UrgentReasonPage<UrgentReason> findPage(UrgentReasonPage<UrgentReason> urgentReasonPage){
        List<UrgentReason> list = urgentReasonDao.findPage(urgentReasonPage);
        urgentReasonPage.setData(list);
        return urgentReasonPage;
    }

    public UrgentReason findById(int id){
        return urgentReasonDao.findById(id);
    }

    public int deleteById(int id){
        return urgentReasonDao.delete(id);
    }
}


