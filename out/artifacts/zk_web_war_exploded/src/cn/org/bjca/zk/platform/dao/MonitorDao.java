package cn.org.bjca.zk.platform.dao;

import cn.org.bjca.zk.db.entity.Monitor;

@MyBatisRepository
public interface MonitorDao {

    int addPic(String cabinetDoorEventId, String picFilePath);

    Monitor findUniqueById(String id);
}
