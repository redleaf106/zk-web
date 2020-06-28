package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.BannerPicture;
import cn.org.bjca.zk.platform.dao.CabinetBannerDao;
import cn.org.bjca.zk.platform.web.page.CabinetBannerPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName CabinetBannerService
 * @Author redleaf
 * @Date 2020/6/9 16:00
 **/
@Component
public class CabinetBannerService {

    @Autowired
    private CabinetBannerDao cabinetBannerDao;

    public int save(BannerPicture bannerPicture){
        return cabinetBannerDao.save(bannerPicture);
    }

    public List<BannerPicture> findAllActive(){
        return cabinetBannerDao.findActivePicture(0);
    }

    public CabinetBannerPage<BannerPicture> findPage(CabinetBannerPage<BannerPicture> cabinetBannerPage){
        List<BannerPicture> list =  cabinetBannerDao.findPage(cabinetBannerPage);
        cabinetBannerPage.setData(list);
        return cabinetBannerPage;
    }

    public BannerPicture findUniqueById(int id){
        return cabinetBannerDao.findUniqueById(id);
    }

    public int update(int id){
        return cabinetBannerDao.update(id);
    }

    public int updateTop(int id){
        return cabinetBannerDao.updateTop(id);
    }

    public int delBannerPicById(int id){
        return cabinetBannerDao.delBannerPicById(id);
    }
}
