/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.cabinet;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;

import cn.org.bjca.zk.db.entity.CabinetDoorEvent;
import cn.org.bjca.zk.platform.service.CabinetDoorEventService;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetDoorEventPage;

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
	
	
	/**
	  * <p>角色管理列表</p>
	  * @Description:
	  * @param model
	  * @param request
	  * @return
	 */
	@RequestMapping("")
	public String list(ModelMap modelMap,HttpServletRequest request) {
		CabinetDoorEventPage<CabinetDoorEvent> cabinetDoorEventPage = new CabinetDoorEventPage<CabinetDoorEvent>();
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
		return "/cabinet/cabinetDoorEvent/cabinetDoorEventList";
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
	
	
}
