/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.cabinet;


import javax.servlet.http.HttpServletRequest;

import cn.org.bjca.zk.db.entity.HTFCheck;
import cn.org.bjca.zk.platform.service.CheckListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;

import cn.org.bjca.zk.db.entity.Cabinet;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.CabinetService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetPage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/***************************************************************************
 
 * @文件名称: CabinetController.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.cabinet
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/
@Controller
@RequestMapping("/cabinet/cabinet")
public class CabinetController extends BaseController{
	
	/**
	 * 机柜服务
	 */
	@Autowired
	private CabinetService cabinetService;

	@Autowired
	private CheckListService checkListService;

	/**
	  * <p>角色管理列表</p>
	  * @Description:
	  * @param modelMap
	  * @param request
	  * @return
	 */
	@RequestMapping("")
	public String list(ModelMap modelMap,HttpServletRequest request) {
		CabinetPage<Cabinet> cabinetPage = new CabinetPage<Cabinet>();
        Page page = new Pagination();
        String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		
		String cabinetNumber = request.getParameter("cabinetNumber");//机柜编号
		if(StringUtils.isNotBlank(cabinetNumber)) {
			cabinetPage.setCabinetNumber("%"+cabinetNumber.trim()+"%");
		}
		
		cabinetPage.setPageVO(page);
		cabinetPage = cabinetService.findPage(cabinetPage);
		cabinetPage.setCabinetNumber(cabinetNumber);
		modelMap.put("cabinetPage", cabinetPage);
		return "/cabinet/cabinet/cabinetList";
	}
	
	/**
	  * <p>指向编辑表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{id}", method = RequestMethod.GET)
	public String toEditFormPage(@PathVariable String id,ModelMap modelMap) throws Exception {
		Cabinet cabinet = null;
		if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
			cabinet = cabinetService.findUniqueById(id);
		}else {
			cabinet = new Cabinet();
		}
		modelMap.put("cabinet",cabinet);
		return "/cabinet/cabinet/cabinetForm";
	}
	
	/**
	  * <p>保存或更新表单信息</p>
	  * @Description:
	  * @param cabinet
	  * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(Cabinet cabinet,HttpServletRequest request) throws DialogException {
		try{
			Message message = new Message();
			if(StringUtils.isNotBlank(cabinet.getId())) {
				message.setContent(this.UPDATE);//内容提示
				cabinet.setFullDoorCount(cabinetService.findUniqueById(cabinet.getId()).getFullDoorCount());
			}
			else{
				message.setContent(this.SAVE);//内容提示
			}
			User user = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
			if(null!=user) {
				cabinet.setUserId(user.getId());
			}
			cabinetService.saveOrUpdate(cabinet);
			message.setStatusCode(this.SUCCESS);
			message.setCallbackType("closeCurrent");
			message.setNavTabId("cabinet");
			return this.ajaxDone(message);
		}catch(Exception ex){
			throw new DialogException(ex);
		}
	}
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id ,id主键
	  * @param id
	  * @return
	 * @throws DialogException 
	 */
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody String delete(@PathVariable("id") String id) throws DialogException {
		try{
			cabinetService.delCabinetById(id);
			Message message = new Message();
			message.setStatusCode(this.SUCCESS);
			message.setContent(this.DELETE);//内容提示
			return this.toJsonString(message);
		}catch(Exception ex){
			throw new DialogException(ex);
		}
	}

	/**
	 * 安卓上传报表
	 */
	@RequestMapping(value = "uploadDayCheck")
	public void registerForAndroid(HttpServletRequest request) throws IOException {
		String cabinetIP = request.getParameter("cabinetIP");
		System.out.println(cabinetIP);
		String city = cabinetService.findByIp(cabinetIP).getCabinetPosition();
		System.out.println(city);
		Map<String, Object> map = new HashMap<>();
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
					String path="/root/dayCheck/"+city+"/"+file.getOriginalFilename();
//					String path = "D:\\/"+city+"/"+file.getOriginalFilename();
					//上传
					file.transferTo(new File(path));
					HTFCheck htfCheck = new HTFCheck();
					htfCheck.setFilePath(path);
					htfCheck.setFileName(file.getOriginalFilename());
					checkListService.add(htfCheck);
				}
			}
		}
	}
	
	
}
