package cn.org.bjca.zk.platform.web.controller.cabinet;

import cn.org.bjca.zk.db.entity.UrgentReason;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.UrgentReasonService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.UrgentReasonPage;
import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName UrgentReasonController
 * @Author redleaf
 * @Date 2020/6/9 11:49
 **/
@Controller
@RequestMapping("/cabinet/urgentReason")
public class UrgentReasonController extends BaseController {

    @Autowired
    private UrgentReasonService urgentReasonService;

    @RequestMapping(value = "findAll")
    @ResponseBody
    public List<UrgentReason> findAll(String reasonType){
        if(StringUtils.isNotBlank(reasonType)){
            int type = Integer.parseInt(reasonType);
            return urgentReasonService.findAllUrgent(type);
        }else {
            return urgentReasonService.findAllUrgent();
        }
    }

    @RequestMapping("")
    public String list(ModelMap modelMap, HttpServletRequest request) {
        System.out.println("进入页面");
        UrgentReasonPage<UrgentReason> urgentReasonPage = new UrgentReasonPage<UrgentReason>();
        Page page = new Pagination();
        String pageNum = request.getParameter("pageNum");//当前页码
        if(StringUtils.isNotBlank(pageNum)) {
            page.setCurrentPage(Integer.parseInt(pageNum));
        }
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
            page.setPageSize(Integer.parseInt(numPerPage));
        }

        String id = request.getParameter("id");
        if(StringUtils.isNotBlank(id)) {
            urgentReasonPage.setId(Integer.parseInt(id));
        }
        urgentReasonPage.setPageVO(page);
        urgentReasonPage = urgentReasonService.findPage(urgentReasonPage);
//        urgentReasonPage.setId(Integer.parseInt(id));
        modelMap.put("urgentReasonPage", urgentReasonPage);
        return "/cabinet/urgentReason/urgentReasonList";
    }

    @RequestMapping(value = "saveOrUpdate")
    @ResponseBody
    public ModelAndView saveOrUpdate(UrgentReason reason){
        Message message = new Message();
        List<UrgentReason> list = urgentReasonService.findAllUrgent(reason.getType());
        int count = list.size();
        int result = 0;
        if(reason.getId()!=0){
            result = urgentReasonService.saveOrUpdate(reason);
            if(result>0){
                message.setContent(this.UPDATE);
            }else{
                message.setContent(this.UPDATE_ERROR);
                message.setStatusCode(this.FAIL);
            }
        }else{
            if(count>=5){
                message.setContent(this.TOOMORE);
                message.setStatusCode(this.FAIL);
            }else {
                result = urgentReasonService.saveOrUpdate(reason);
                if(result>0){
                    message.setContent(this.SAVE);//内容提示
                    message.setStatusCode(this.SUCCESS);
                }else{
                    message.setContent(this.SAVE_ERROR);
                    message.setStatusCode(this.FAIL);
                }
            }
        }
        message.setCallbackType("closeCurrent");
        message.setNavTabId("cabinet");
        return this.ajaxDone(message);
    }


    /**
     * <p>指向编辑表单页面</p>
     * @Description:
     * @return
     */
    @RequestMapping(value = "toEditFormPage/{id}", method = RequestMethod.GET)
    public String toEditFormPage(@PathVariable String id, ModelMap modelMap) throws Exception {
        System.out.println(id);
        UrgentReason urgentReason = null;
        if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
            urgentReason = urgentReasonService.findById(Integer.parseInt(id));
        }else{
            urgentReason = new UrgentReason();
        }
        modelMap.put("urgentReason",urgentReason);
        return "/cabinet/urgentReason/urgentReasonForm";
    }

    @RequestMapping(value = "delete/{id}")
    public @ResponseBody String delete(@PathVariable("id") String id) throws DialogException {
        try{
            int result = urgentReasonService.deleteById(Integer.parseInt(id));
            Message message = new Message();
            if(result>0){
                message.setStatusCode(this.SUCCESS);
                message.setContent(this.DELETE);//内容提示
            }else{
                message.setStatusCode(this.FAIL);
                message.setContent(this.DELETE_ERROR);//内容提示
            }
            return this.toJsonString(message);
        }catch(Exception ex){
            throw new DialogException(ex);
        }
    }


}
