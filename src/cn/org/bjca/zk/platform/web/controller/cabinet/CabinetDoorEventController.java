/**
 *
 */
package cn.org.bjca.zk.platform.web.controller.cabinet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.bjca.zk.db.entity.CabinetDoor;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.CabinetDoorService;
import cn.org.bjca.zk.platform.service.EmailService;
import cn.org.bjca.zk.platform.tools.CabinetDoorServer;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;

import cn.org.bjca.zk.db.entity.CabinetDoorEvent;
import cn.org.bjca.zk.platform.service.CabinetDoorEventService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetDoorEventPage;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/***************************************************************************

 * @文件名称: CabinetDoorEventController.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.cabinet
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
@Controller
@RequestMapping("/cabinet/cabinetDoorEvent")
public class CabinetDoorEventController extends BaseController {

	@Autowired
	private CabinetDoorEventService cabinetDoorEventService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CabinetDoorService cabinetDoorService;


	/**
	 * <p>角色管理列表</p>
	 * @Description:
	 * @param
	 * @param request
	 * @return
	 */
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
		cabinetDoorEventPage = cabinetDoorEventService.findPage(cabinetDoorEventPage);
		cabinetDoorEventPage.setCabinetNumber(cabinetNumber);
		cabinetDoorEventPage.setCabinetDoorNumber(cabinetDoorNumber);
		modelMap.put("cabinetDoorEventPage", cabinetDoorEventPage);
		JSONObject jsonObject = new JSONObject();
		if(source!=null&&source.length()>0){
			response.getWriter().write(jsonObject.toJSONString(cabinetDoorEventPage.getData()));
			return null;
		}
		return "/cabinet/cabinetDoorEvent/cabinetDoorEventList";
	}

	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(@RequestBody CabinetDoorEvent CabinetDoorEvent, HttpServletRequest request) throws DialogException {
		try {
			System.out.println("状态码是："+CabinetDoorEvent.getStatus());
			if(Integer.parseInt(CabinetDoorEvent.getStatus())>0){
				System.out.println("存取出现异常行为");
				emailService.sendOneMail(CabinetDoorEvent);
			}
			Message message = new Message();
			if(StringUtils.isNotBlank(CabinetDoorEvent.getId()))
				message.setContent(this.UPDATE);//内容提示
			else{
				message.setContent(this.SAVE);//内容提示
			}
			//User user = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
			cabinetDoorEventService.saveOrUpdate(CabinetDoorEvent);
			//TODO
			List<CabinetDoor> list = cabinetDoorService.findByCabinetNumberAndDoorNumber(CabinetDoorEvent.getCabinetNumber(), CabinetDoorEvent.getCabinetDoorNumber());
			System.out.println("sssssssssssssssssssssss"+list.size());
			CabinetDoor cabinetDoor = list.get(0);
			//存取次数+1
			cabinetDoor.setAccessCount(cabinetDoor.getAccessCount()+1);
			String cabinetNumber = list.get(0).getCabinet().getCabinetNumber();
			String cabinetDoorNumber = list.get(0).getCabinetDoorNumber();
			message.setStatusCode(this.SUCCESS);
			message.setCallbackType("closeCurrent");
			message.setNavTabId("cabinetDoorEvent");
			return this.ajaxDone(message);
		}catch (Exception ex){
			throw new DialogException(ex);
		}
	}

	/**
	 * <p>指向编辑表单页面</p>
	 * @Description:
	 * @return
	 */
	@RequestMapping(value = "toDetailPage/{id}", method = RequestMethod.GET)
	public String toDetailPage(@PathVariable String id,ModelMap modelMap) throws Exception {
		CabinetDoorEvent cabinetDoorEvent = null;
		if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
			cabinetDoorEvent = cabinetDoorEventService.findUniqueById(id);
		}
		modelMap.put("cabinetDoorEvent",cabinetDoorEvent);
		return "/cabinet/cabinetDoorEvent/cabinetDoorEventDetail";
	}

	@RequestMapping("jumpMedia")
	public String jumpMedia(){
		System.out.println("jumpMedia...");
		return "/cabinet/cabinetDoorEvent/media";
	}

	@RequestMapping(value = "openSocketServer")
	@ResponseBody
	public String openSocketServer(){
		System.out.println("server start...");
		CabinetDoorServer cabinetDoorServer = CabinetDoorServer.getInstance();
		cabinetDoorServer.openDoor("192.168.3.140", "1");
		return "success";
	}


}
