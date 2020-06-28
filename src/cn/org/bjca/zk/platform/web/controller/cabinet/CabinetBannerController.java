package cn.org.bjca.zk.platform.web.controller.cabinet;

import cn.org.bjca.zk.db.entity.BannerPicture;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.AttachmentMessage;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.CabinetBannerService;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetBannerPage;
import com.alibaba.fastjson.JSONObject;
import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @ClassName CabinetBannerController
 * @Author redleaf
 * @Date 2020/6/9 15:52
 **/
@Controller
@RequestMapping("/cabinet/cabinetBanner")
public class CabinetBannerController extends BaseController {

    @Autowired
    private CabinetBannerService cabinetBannerService;

    @Value("#{sysConfig['picMaxSize']}")
    private String picMaxSizeStr ;

//    private String picPathquanju = "D:/TomCat/apache-tomcat-9.0.16/hk/bannerUpload/";
    private String picPathquanju = "/usr/share/tomcat/hk/bannerUpload/";

    @ResponseBody
    @RequestMapping("saveBannerPic")
    public ModelAndView insertBanner(HttpServletRequest request) throws IOException {
        Message message = new Message();
        String attachmentPath = request.getParameter("attachment.attachmentPath");
        attachmentPath = new String(attachmentPath.getBytes("ISO-8859-1"), "UTF-8");
        String picStatus = "/bannerUpload/"+attachmentPath;
        System.out.println(picStatus);
        BannerPicture bannerPicture = new BannerPicture();
        bannerPicture.setPicFilePath(picStatus);
        bannerPicture.setPicStatus(Integer.parseInt(request.getParameter("picStatus")));
        int result = cabinetBannerService.save(bannerPicture);
        if(result>0){
            message.setContent(this.SAVE);
            message.setStatusCode(this.SUCCESS);
        }else {
            message.setContent(this.SAVE_ERROR);
            message.setStatusCode(this.FAIL);
        }
        message.setCallbackType("closeCurrent");
        message.setNavTabId("cabinetBanner");
        return ajaxDone(message);
    }

    @RequestMapping("")
    public String list(ModelMap modelMap, HttpServletRequest request){
        CabinetBannerPage<BannerPicture> cabinetBannerPage = new CabinetBannerPage<BannerPicture>();
        Page page = new Pagination();
        String id = request.getParameter("id");
        String pageNum = request.getParameter("pageNum");//当前页码
        if(StringUtils.isNotBlank(pageNum)) {
            page.setCurrentPage(Integer.parseInt(pageNum));
        }
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
            page.setPageSize(Integer.parseInt(numPerPage));
        }
        if(StringUtils.isNotBlank(id)){
            cabinetBannerPage.setId(Integer.parseInt(id.trim()));
        }
        cabinetBannerPage.setPageVO(page);
        cabinetBannerPage = cabinetBannerService.findPage(cabinetBannerPage);
        modelMap.put("cabinetBannerPage", cabinetBannerPage);
        return "cabinet/cabinetBanner/cabinetBannerList";
    }
    //给安卓
    @RequestMapping("findActivePic")
    @ResponseBody
    public String findActivePic(){
        List<BannerPicture> list = cabinetBannerService.findAllActive();
        for(BannerPicture bannerPicture:list){
            bannerPicture.setPicFilePath("http://10.11.28.19:8080/HKimages"+bannerPicture.getPicFilePath());
        }
        return JSONObject.toJSONString(list);
    }

    @RequestMapping(value = "uploadBannerPic")
    public void uploadSealAttachment(@RequestParam("file") CommonsMultipartFile mFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取请求头
        String requestHeader = request.getHeader("from");
        String id = request.getParameter("id");
        //System.out.println("dataBaseId====="+id);
        AttachmentMessage attachMessage = new AttachmentMessage();
        byte[] imgBty = mFile.getBytes();
        System.out.println("imgBty======"+imgBty.length);
        String fileName = getXssFileName(mFile);
        String imgType = getXssContentType(mFile);
        if (imgBty.length > 0 && StringUtils.isNotBlank(fileName)) {
            String imgExt = filterImgType(imgType);
            Long picSize = mFile.getSize();
            Double picMaxSize = 1.00;
            try {
                picMaxSize = Double.parseDouble(picMaxSizeStr);
            } catch (Exception e) {
                logger.error("******picMaxSize error,can't convert Double:" + picMaxSizeStr, e);
            }
            if (imgExt != null) {
                if (picSize <= picMaxSize * PDFSealConstants.SIZE_KB) {
                    String  picfileName= EssPdfUtil.genrRandomUUID()+".png";
                    String filePath = picPathquanju+picfileName;
                    System.out.println("图片路径为："+filePath);
                    FileUtils.writeByteArrayToFile(new File(filePath), imgBty);
                    attachMessage.setAttachmentPath(new String(picfileName.getBytes(PDFSealConstants.CODING), "iso-8859-1") );
                } else {
                    attachMessage.setAttachmentPath("");
                }
                attachMessage.setId(imgExt); // 图片类型
                attachMessage.setFileName(fileName);
                attachMessage.setAttachmentSize(picSize.toString());
                // 下划线之前是以byte单位，在页面上进行比较的作用 下划线之后是从配置文件中读取的数值，在页面上准确提示的作用
                attachMessage.setAttachmentMaxSize((picMaxSize * PDFSealConstants.SIZE_KB) + "_" + picMaxSize);


            } else {
                logger.error("******server seal type error:" + imgType);
            }
            PrintWriter out = null;
            try {
                out = response.getWriter();
                String jsonStr = toJsonString(attachMessage);
                out.println(jsonStr);
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
    }

    @RequestMapping(value = "jumpUploadBannerPic", method = RequestMethod.GET)
    public String toAttachmentLookUpPage() throws Exception {
        return "/cabinet/cabinetBanner/bannerPicUpload";
    }

    @RequestMapping(value = "toEditCabinetBannerFormPage/{id}", method = RequestMethod.GET)
    public String toEditCabinetBannerFormPage(@PathVariable String id, ModelMap modelMap) throws Exception {
        BannerPicture bannerPicture = null;
        if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
            bannerPicture = cabinetBannerService.findUniqueById(Integer.parseInt(id));
        }else {
            bannerPicture = new BannerPicture();
        }
        modelMap.put("bannerPicture",bannerPicture);
        return "/cabinet/cabinetBanner/cabinetBannerForm";
    }

    @RequestMapping("showPic/{id}")
    public String showPic(@PathVariable String id, ModelMap modelMap){
        BannerPicture bannerPicture = cabinetBannerService.findUniqueById(Integer.parseInt(id));
        modelMap.put("bannerPicture",bannerPicture);
        return "/cabinet/cabinetBanner/showPic";
    }

    @RequestMapping("updateStatus/{id}")
    @ResponseBody
    public String updateStatus(@PathVariable String id){
        Message message = new Message();
        int result = cabinetBannerService.update(Integer.parseInt(id));
        if(result>0){
            message.setStatusCode(this.SUCCESS);
            message.setContent(this.UPDATE);
        }else {
            message.setStatusCode(this.FAIL);
            message.setContent(this.UPDATE_ERROR);
        }
        return this.toJsonString(message);
    }

    @RequestMapping("updateTop/{id}")
    @ResponseBody
    public String updateTop(@PathVariable String id){
        Message message = new Message();
        int result = cabinetBannerService.updateTop(Integer.parseInt(id));
        if(result>0){
            message.setStatusCode(this.SUCCESS);
            message.setContent(this.UPDATE);
        }else {
            message.setStatusCode(this.FAIL);
            message.setContent(this.UPDATE_ERROR);
        }
        return this.toJsonString(message);
    }

    @RequestMapping(value = "delete/{id}")
    public @ResponseBody String delete(@PathVariable("id") String id) throws DialogException {
        try{
            int result = cabinetBannerService.delBannerPicById(Integer.parseInt(id));
            Message message = new Message();
            if(result>0){
                message.setStatusCode(this.SUCCESS);
                message.setContent(this.DELETE);
            }else {
                message.setStatusCode(this.FAIL);
                message.setContent(this.DELETE_ERROR);
            }
            return this.toJsonString(message);
        }catch(Exception ex){
            throw new DialogException(ex);
        }
    }

    //添加图片页面显示图片
    @RequestMapping(value = "showBannerPic")
    public void showBannerPic(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        System.out.println(id);
        OutputStream os = null;
        try {
            id = this.filterSafeStringXSS(id);
            byte[] picBty = null;
            if (id.length() == 32) { // 表示从数据库获取印章
                BannerPicture bannerPicture = cabinetBannerService.findUniqueById(Integer.parseInt(id));
            } else { // 从目录获取印章
                String filePath = "D:/TomCat/apache-tomcat-9.0.16/hk/bannerUpload/"+id;
                filePath = new String(filePath.getBytes("iso-8859-1"), PDFSealConstants.CODING);
                logger.debug("**********server img path:" + filePath);
                picBty = FileUtils.readFileToByteArray(new File(filePath));
            }
            os = response.getOutputStream();
            os.write(picBty);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }
}
