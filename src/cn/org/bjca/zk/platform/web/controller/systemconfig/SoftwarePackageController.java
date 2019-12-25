/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.systemconfig;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;

import cn.org.bjca.zk.db.entity.SoftwarePackage;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.service.SoftwarePackageService;
import cn.org.bjca.zk.platform.utils.PDFSealUtil;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.SoftwarePackagePage;

/***************************************************************************

 * @文件名称: SoftwarePackageController.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.systemconfig
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/
@Controller
@RequestMapping("/systemConfig/softwarePackage")
public class SoftwarePackageController extends BaseController{
	
	@Autowired
	private SoftwarePackageService softwarePackageService;
	
	@Value("#{sysConfig['APKPath']}")
	private  String apkPath;
	
	@RequestMapping(value ="")
	public String list( ModelMap modelMap, HttpServletRequest request) {
		SoftwarePackagePage<SoftwarePackage> softwarePackagePage = new SoftwarePackagePage<SoftwarePackage>();
        Page page = new Pagination();
        String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		
		String name = request.getParameter("name");//版本名称
		if(StringUtils.isNotBlank(name)) {
			softwarePackagePage.setName("%"+name.trim()+"%");
		}
		
		String version = request.getParameter("version");//版本号用户名称
		if(StringUtils.isNotBlank(version)) {
			softwarePackagePage.setVersion("%"+version.trim()+"%");
		}
		
		softwarePackagePage.setPageVO(page);
		softwarePackagePage = softwarePackageService.findPage(softwarePackagePage);
		softwarePackagePage.setName(name);
		softwarePackagePage.setVersion(version);
		
		modelMap.put("softwarePackagePage", softwarePackagePage);

		return "/systemConfig/softwarePackage/softwarePackageList";
	}
	
	
	
	/**
	  * <p>指向升级包表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{softwarePakcageId}", method = RequestMethod.GET)
	public String toEditFormPage( @PathVariable("softwarePakcageId") String softwarePakcageId, ModelMap modelMap){	
		SoftwarePackage softwarePackage = null;
		if(StringUtils.isNotBlank(softwarePakcageId)&& !BLANK_PARAM_VALUE.equals(softwarePakcageId)){
			softwarePackage = softwarePackageService.findUniqueById(softwarePakcageId);
		}else{
			softwarePackage = new SoftwarePackage();
		}
		modelMap.put("softwarePackage", softwarePackage);
		
		return "/systemConfig/softwarePackage/softwarePackageForm";
	}
	
	/**
	  * <p>保存或更新表单信息</p>
	  * @Description:
	  * @param
	  * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate( SoftwarePackage softwarePackage,@RequestParam(value = "file", required = false) CommonsMultipartFile mFile,HttpServletRequest request) throws Exception {
		Message message = new Message();
		if(StringUtils.isNotBlank(softwarePackage.getId()))
			message.setContent(this.UPDATE);//内容提示
		else{			
			message.setContent(this.SAVE);//内容提示
		}
		
		if(null!=mFile && null!=mFile.getBytes() && mFile.getBytes().length>0) {
			String storePath = apkPath+softwarePackage.getName()+softwarePackage.getVersion()+".apk";
//			softwarePackage.setSoftwarePackage(mFile.getBytes());
			FileUtils.writeByteArrayToFile(new File(storePath), mFile.getBytes());
			softwarePackage.setStorePath(storePath);
			softwarePackage.setSize(mFile.getBytes().length/1024);
			byte[] digest = PDFSealUtil.digest(mFile.getBytes(),"md5");
			System.out.println("md5=============="+digest);
			softwarePackage.setDigest(new String(Hex.encode(digest)));
		}
		
		User loginUser = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
		if(null!=loginUser) {
			softwarePackage.setUserId(loginUser.getId());
		}
		softwarePackageService.saveOrUpdate(softwarePackage);
		message.setStatusCode(this.SUCCESS);
		message.setCallbackType("closeCurrent");
		message.setNavTabId("softwarePackage");
		message.setRel("softwarePackageBox");
		return this.ajaxDone(message);
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id ,id主键
	  * @param
	  * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody String delete(@PathVariable("id") String id) throws Exception{
		softwarePackageService.delSoftwarePackageById(id);
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.DELETE);//内容提示
		return this.toJsonString(message);
	}
}
