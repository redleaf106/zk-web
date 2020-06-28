package cn.org.bjca.zk.platform.web.page;

import com.cn.bjca.seal.esspdf.core.pagination.annotation.Paging;

/**
 * @ClassName UrgentReasonPage
 * @Author redleaf
 * @Date 2020/6/9 12:26
 **/
@Paging(field = "pageVO")
public class UrgentReasonPage<T> extends BasePage<T>{

    private static final long serialVersionUID = -444037156256961611L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
