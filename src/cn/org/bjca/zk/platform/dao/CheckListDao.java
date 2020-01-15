package cn.org.bjca.zk.platform.dao;


import cn.org.bjca.zk.db.entity.CheckInfo;
import cn.org.bjca.zk.db.entity.HTFCheck;
import cn.org.bjca.zk.platform.web.page.CheckListPage;

import java.util.List;

@MyBatisRepository
public interface CheckListDao {

    List<HTFCheck> findPage(CheckListPage<HTFCheck> checkListPage);

    int save(HTFCheck htfCheck);
}
