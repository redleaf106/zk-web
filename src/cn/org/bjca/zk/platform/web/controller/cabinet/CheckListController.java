package cn.org.bjca.zk.platform.web.controller.cabinet;

import cn.org.bjca.zk.db.entity.HTFCheck;
import cn.org.bjca.zk.platform.service.CheckListService;
import cn.org.bjca.zk.platform.web.page.CheckListPage;
import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CheckListController
 * @Author redleaf
 * @Date 2020/1/9 18:08
 **/
@Controller
@RequestMapping("/cabinet/checkList")
public class CheckListController {

    @Autowired
    private CheckListService checkListService;

    @RequestMapping("")
    public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){

        CheckListPage<HTFCheck> checkListPage = new CheckListPage<HTFCheck>();
        Page page = new Pagination();
        String reportType = request.getParameter("reportType");
        String pageNum = request.getParameter("pageNum");//当前页码
        if(StringUtils.isNotBlank(pageNum)) {
            page.setCurrentPage(Integer.parseInt(pageNum));
        }
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
            page.setPageSize(Integer.parseInt(numPerPage));
        }
        if(StringUtils.isNotBlank(reportType)) {
            checkListPage.setReportType(reportType);
        }else{
            checkListPage.setReportType("1");
        }
        checkListPage.setPageVO(page);
        checkListPage = checkListService.findPage(checkListPage);
        /*for(HTFCheck htfCheck:checkListPage.getData()){
            System.out.println(htfCheck.getFileName());
        }*/
        modelMap.put("checkListPage", checkListPage);
        modelMap.put("reportType",reportType);
        if (reportType.equalsIgnoreCase("1")){
            return "/cabinet/checkList/checkList";
        }else if (reportType.equalsIgnoreCase("2")){
            return "/cabinet/checkList/checkWeekList";
        }else {
            return "/cabinet/checkList/checkMonthList";
        }
    }
}
