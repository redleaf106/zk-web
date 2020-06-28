package cn.org.bjca.zk.platform.web.controller.employee;

import cn.org.bjca.zk.db.entity.Assistant;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.web.page.AssistantPage;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName AssistantController
 * @Author redleaf
 * @Date 2020/6/1 5:31
 **/
@Controller
@RequestMapping("/employee/assistant")
public class AssistantController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "")
    public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("ssssssssss");
        AssistantPage<Assistant> assistantPage = new AssistantPage<Assistant>();
        Page page = new Pagination();
        String purpose = request.getParameter("activation");
        String ip = request.getParameter("IPAdress");
        String pageNum = request.getParameter("pageNum");//当前页码
        if(StringUtils.isNotBlank(pageNum)) {
            page.setCurrentPage(Integer.parseInt(pageNum));
        }
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
            page.setPageSize(Integer.parseInt(numPerPage));
        }

        String assistantName = request.getParameter("assistantName");//用户名称
        if(StringUtils.isNotBlank(assistantName)) {
            assistantPage.setAssistantName("%"+assistantName.trim()+"%");
        }

        assistantPage.setPageVO(page);
        if(purpose==null||purpose.length()==0){
            assistantPage = employeeService.findAssistantPage(assistantPage);
            assistantPage.setAssistantName(assistantName);
            modelMap.put("assistantPage", assistantPage);
            return "/employee/assistant/assistantList";
        }else{
            assistantPage = employeeService.getAllAssistantNotActive(assistantPage);
            assistantPage.setAssistantName(assistantName);
            response.getWriter().write(JSONObject.toJSONString(assistantPage.getData(), SerializerFeature.DisableCircularReferenceDetect));
            return null;
        }
    }

    //给安卓传助理列表
    @RequestMapping(value = "sentAssistantToAndroid",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String assistantList(String ip){
        List<Assistant> list = employeeService.getAllAssistantNoActive(ip);
        return JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
    }
}
