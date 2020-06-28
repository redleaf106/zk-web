package cn.org.bjca.zk.platform.dao;

import cn.org.bjca.zk.db.entity.BannerPicture;
import cn.org.bjca.zk.platform.web.page.CabinetBannerPage;

import java.util.List;

@MyBatisRepository
public interface CabinetBannerDao {

    int save(BannerPicture bannerPicture);

    List<BannerPicture> findActivePicture(int picStatus);

    List<BannerPicture> findPage(CabinetBannerPage<BannerPicture> cabinetBannerPage);

    BannerPicture findUniqueById(int id);

    int update(int id);

    int updateTop(int id);

    int delBannerPicById(int id);
}
