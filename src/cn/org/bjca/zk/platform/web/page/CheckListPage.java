package cn.org.bjca.zk.platform.web.page;

import com.cn.bjca.seal.esspdf.core.pagination.annotation.Paging;

/**
 * @ClassName CheckListPage
 * @Author redleaf
 * @Date 2020/1/9 18:14
 **/
@Paging(field = "pageVO")
public class CheckListPage <T> extends BasePage<T>{

    private static final long serialVersionUID = -6183879852924320372L;

    private String reportType;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
