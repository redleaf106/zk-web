package cn.org.bjca.zk.platform.web.page;

import com.cn.bjca.seal.esspdf.core.pagination.annotation.Paging;

/**
 * @ClassName CabinetBannerPage
 * @Author redleaf
 * @Date 2020/6/9 20:59
 **/
@Paging(field = "pageVO")
public class CabinetBannerPage<T> extends BasePage<T>{

    private static final long serialVersionUID = -444037156007223311L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
