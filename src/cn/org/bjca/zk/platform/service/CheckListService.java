package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.HTFCheck;
import cn.org.bjca.zk.platform.dao.CheckListDao;
import cn.org.bjca.zk.platform.web.page.CheckListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName CheckListService
 * @Author redleaf
 * @Date 2020/1/9 18:25
 **/
@Component
public class CheckListService {

    @Autowired
    private CheckListDao checkListDao;

    public CheckListPage<HTFCheck> findPage(CheckListPage<HTFCheck> checkListPage){
        List<HTFCheck> list = checkListDao.findPage(checkListPage);
        checkListPage.setData(list);
        return checkListPage;
    }

    public int add(HTFCheck htfCheck){
        return checkListDao.save(htfCheck);
    }

    public HTFCheck findById(int id){
        return checkListDao.findById(id);
    }
}
