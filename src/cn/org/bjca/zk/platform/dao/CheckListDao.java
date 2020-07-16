package cn.org.bjca.zk.platform.dao;


import cn.org.bjca.zk.db.entity.HTFCheck;
import cn.org.bjca.zk.platform.web.page.CheckListPage;

import java.util.List;

@MyBatisRepository
public interface CheckListDao {

    /**
     * <p>获取所有列表/p>
     * @Description:
     * @return
     */
    List<HTFCheck> findPage(CheckListPage<HTFCheck> checkListPage);

    int save(HTFCheck htfCheck);

    HTFCheck findById(int id);
}
