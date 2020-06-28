package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.platform.dao.MonitorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MonitorService
 * @Author redleaf
 * @Date 2020/6/1 7:05
 **/
@Component
public class MonitorService {

    @Autowired
    private MonitorDao monitorDao;

    public int addPic(String cabinetDoorEventId, String picFilePath){
        return monitorDao.addPic(cabinetDoorEventId,picFilePath);
    }
    public int addVideo(String cabinetDoorEventId, String videoFilePath){
        return monitorDao.addVideo(videoFilePath,cabinetDoorEventId);
    }

}
