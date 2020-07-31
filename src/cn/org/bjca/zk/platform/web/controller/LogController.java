package cn.org.bjca.zk.platform.web.controller;

import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @ClassName LogController
 * @Author redleaf
 * @Date 2020/7/14 13:51
 **/
@Controller
public class LogController {
    /**
     * 安卓上传日志
     */
    @RequestMapping(value = "uploadAndroidLog")
    public void uploadAndroidLog(HttpServletRequest request) throws IOException {
        String ip = request.getParameter("cabinetIp");
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    //文件存放路径
                    String path= EssPdfUtil.getTomcatPath()+"/AndroidLogs/"+ip+file.getOriginalFilename();
                    File logFile = new File(path);
                    File fileParent = logFile.getParentFile();
                    if(!fileParent.exists()){
                        fileParent.mkdirs();
                    }
                    //上传
                    file.transferTo(logFile);
                }
            }
        }
    }
}
