package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.platform.dao.MonitorDao;
import org.springframework.stereotype.Component;

/**
 * @ClassName MonitorService
 * @Author redleaf
 * @Date 2020/6/1 7:05
 **/
@Component
public class MonitorService {

    private MonitorDao monitorDao;

    public int addPic(String cabinetDoorEventId, String picFilePath){
        monitorDao.addPic(cabinetDoorEventId,picFilePath);
        return 0;
    }
}
