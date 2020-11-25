package cn.org.bjca.zk.platform.dao;

import cn.org.bjca.zk.db.entity.Monitor;

@MyBatisRepository
public interface MonitorDao {

    //@Insert("insert into BO_MONITOR(CABINETDOOREVENTID,PICFILEPATH)VALUES(#{0},#{1})")
    int addPic(String cabinetDoorEventId, String picFilePath);

    int addVideo(String videoFilePath, String cabinetDoorEventId);

    Monitor findUniqueById(String id);

    Monitor findEntityByCabinetId(String id);
}
