package cn.org.bjca.zk.platform.web.controller.cabinet;

import cn.org.bjca.zk.db.entity.Cabinet;
import cn.org.bjca.zk.db.entity.CabinetDoor;
import cn.org.bjca.zk.db.entity.CabinetDoorEvent;
import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.CabinetDoorEventService;
import cn.org.bjca.zk.platform.service.CabinetDoorService;
import cn.org.bjca.zk.platform.service.CabinetService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetDoorEventPage;
import com.alibaba.fastjson.JSONObject;
import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName SendEventController
 * @Author redleaf
 * @Date 2020/6/15 18:54
 **/
@EnableAsync
@Service
@RequestMapping("/cabinet/sendEvent")
@Controller
public class SendEventController extends BaseController {

    @Autowired
    private CabinetDoorEventService cabinetDoorEventService;
    @Autowired
    private CabinetService cabinetService;
    @Autowired
    private CabinetDoorService cabinetDoorService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("")
    public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        CabinetDoorEventPage<CabinetDoorEvent> cabinetDoorEventPage = new CabinetDoorEventPage<CabinetDoorEvent>();
        Page page = new Pagination();
        String pageNum = request.getParameter("pageNum");//当前页码
        String source = request.getParameter("source");//请求来源
        if(StringUtils.isNotBlank(pageNum)) {
            page.setCurrentPage(Integer.parseInt(pageNum));
        }
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        numPerPage = "300";
        if(StringUtils.isNotBlank(numPerPage)) {
            page.setPageSize(Integer.parseInt(numPerPage));
        }

        String cabinetNumber = request.getParameter("cabinetNumber");//机柜编号
        if(StringUtils.isNotBlank(cabinetNumber)) {
            cabinetDoorEventPage.setCabinetNumber("%"+cabinetNumber.trim()+"%");
        }

        String cabinetDoorNumber = request.getParameter("cabinetDoorNumber");//柜门编号
        if(StringUtils.isNotBlank(cabinetDoorNumber)) {
            cabinetDoorEventPage.setCabinetDoorNumber("%"+cabinetDoorNumber.trim()+"%");
        }
        cabinetDoorEventPage.setPageVO(page);
        cabinetDoorEventPage = cabinetDoorEventService.findTemporaryPage(cabinetDoorEventPage);
        cabinetDoorEventPage.setCabinetNumber(cabinetNumber);
        cabinetDoorEventPage.setCabinetDoorNumber(cabinetDoorNumber);
        modelMap.put("cabinetDoorEventPage", cabinetDoorEventPage);
        JSONObject jsonObject = new JSONObject();
        if(source!=null&&source.length()>0){
            response.getWriter().write(jsonObject.toJSONString(cabinetDoorEventPage.getData()));
            return null;
        }
        return "/cabinet/cabinetDoorEvent/sendEventList";
    }

    @RequestMapping(value = "saveOrUpdate")
    public ModelAndView saveOrUpdate(HttpServletRequest request) throws DialogException {
        try {
            Message message = new Message();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String cabinetId = request.getParameter("cabinetId");
            String employeeId = request.getParameter("employeeId");
            String all = request.getParameter("allEmployee");
            String doorOptTime = request.getParameter("doorOptTime");
            doorOptTime = doorOptTime.replace("T"," ")+":00";
            String status = request.getParameter("status");
            Cabinet cabinet = cabinetService.findUniqueById(cabinetId);
            if("0".equals(all)){
                List<CabinetDoor> doorList = cabinetDoorService.findByCabinetID(cabinetId);
                for(CabinetDoor cabinetDoor:doorList){
                    Employee employee = employeeService.findUniqueById(cabinetDoor.getEmployeeId());
                    CabinetDoorEvent cabinetDoorEvent = new CabinetDoorEvent();
                    cabinetDoorEvent.setCabinetNumber(cabinet.getCabinetNumber());
                    cabinetDoorEvent.setCabinetIp(cabinet.getCabinetIP());
                    cabinetDoorEvent.setCabinetDoorNumber(cabinetDoor.getCabinetDoorNumber());
                    cabinetDoorEvent.setTemporaryStatus(1);
                    cabinetDoorEvent.setEmployeeCardNumber(employee.getIcCardNumber());
                    cabinetDoorEvent.setStatus(status);
                    cabinetDoorEvent.setDoorOptTime(new Timestamp(simpleDateFormat.parse(doorOptTime).getTime()));
                    cabinetDoorEventService.saveOrUpdate(cabinetDoorEvent);
                }
            }else if ("1".equals(all)){
                Employee employee = employeeService.findUniqueById(employeeId);
                CabinetDoor cabinetDoor = cabinetDoorService.selectDoorByEmployeeIdAndIP(employeeId,cabinet.getCabinetIP());
                CabinetDoorEvent cabinetDoorEvent = new CabinetDoorEvent();
                cabinetDoorEvent.setCabinetNumber(cabinet.getCabinetNumber());
                cabinetDoorEvent.setCabinetIp(cabinet.getCabinetIP());
                cabinetDoorEvent.setCabinetDoorNumber(cabinetDoor.getCabinetDoorNumber());
                cabinetDoorEvent.setTemporaryStatus(1);
                cabinetDoorEvent.setEmployeeCardNumber(employee.getIcCardNumber());
                cabinetDoorEvent.setStatus(status);
                cabinetDoorEvent.setDoorOptTime(new Timestamp(simpleDateFormat.parse(doorOptTime).getTime()));
                cabinetDoorEventService.saveOrUpdate(cabinetDoorEvent);
            }
            message.setContent(SAVE);
            message.setStatusCode(SUCCESS);
            message.setCallbackType("closeCurrent");
            message.setNavTabId("sendEvent");
            return this.ajaxDone(message);
        }catch (Exception ex){
            throw new DialogException(ex);
        }
    }

}
