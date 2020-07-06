package cn.org.bjca.zk.platform.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UploadController
 * @Author redleaf
 * @Date 2020/1/15 16:50
 **/
@Controller
@RequestMapping(value = "/uploadInterface")
public class UploadController extends BaseController {

    @RequestMapping("uploadLogs")
    @ResponseBody
    public String uploadFile(HttpServletRequest request){
        return null;
    }
}
