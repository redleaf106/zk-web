/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.cabinet;

import cn.org.bjca.zk.db.entity.Cabinet;
import cn.org.bjca.zk.db.entity.CabinetDoor;
import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.CabinetDoorService;
import cn.org.bjca.zk.platform.service.CabinetService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.tools.CabinetDoorServer;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetDoorPage;
import com.alibaba.fastjson.JSONObject;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/***************************************************************************

 * @文件名称: CabinetDoorController.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.cabinet
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
@Controller
@RequestMapping("/cabinet/cabinetDoor")
public class CabinetDoorController extends BaseController {
	
	@Autowired
	private CabinetService cabinetService; 
	
	@Autowired
	private CabinetDoorService cabinetDoorService;
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	  * <p>角色管理列表</p>
	  * @Description:
	  * @param modelMap
	  * @param request
	  * @return
	 */
	@RequestMapping("")
	public String list(ModelMap modelMap,HttpServletRequest request) {
		CabinetDoorPage<CabinetDoor> cabinetDoorPage = new CabinetDoorPage<CabinetDoor>();
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
			cabinetDoorPage.setCabinetNumber("%"+cabinetNumber.trim()+"%");
		}
		
		String cabinetDoorNumber = request.getParameter("cabinetDoorNumber");//柜门编号
		if(StringUtils.isNotBlank(cabinetDoorNumber)) {
			cabinetDoorPage.setCabinetDoorNumber("%"+cabinetDoorNumber.trim()+"%");
		}
		
		String employeeName = request.getParameter("employeeName");//员工姓名
		if(StringUtils.isNotBlank(employeeName)) {
			cabinetDoorPage.setEmployeeName("%"+employeeName.trim()+"%");
		}
		
		cabinetDoorPage.setPageVO(page);
		cabinetDoorPage = cabinetDoorService.findPage(cabinetDoorPage);
		cabinetDoorPage.setCabinetNumber(cabinetNumber);
		cabinetDoorPage.setCabinetDoorNumber(cabinetDoorNumber);
		cabinetDoorPage.setEmployeeName(employeeName);
		for (CabinetDoor cabinetDoor:cabinetDoorPage.getData()){
			System.out.println(cabinetDoor.getDoorOptTime());
		}
		modelMap.put("cabinetDoorPage", cabinetDoorPage);
		return "/cabinet/cabinetDoor/cabinetDoorList";
	}
	
	/**
	  * <p>指向编辑表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{id}", method = RequestMethod.GET)
	public String toEditFormPage(@PathVariable String id,ModelMap modelMap) throws Exception {
		CabinetDoor cabinetDoor = null;
		if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
			cabinetDoor = cabinetDoorService.findUniqueById(id);
		}else {
			cabinetDoor = new CabinetDoor();
		}
		
		List<Cabinet> cabinetList = cabinetService.getAll(); 
		List<Employee> employeeList = employeeService.findAllNoDoor();
		modelMap.put("cabinetDoor",cabinetDoor);
		modelMap.put("cabinetList", cabinetList);
		modelMap.put("employeeList", employeeList);
		return "/cabinet/cabinetDoor/cabinetDoorForm";
	}
	
	/**
	  * <p>保存或更新表单信息</p>
	  * @Description:
	  * @param cabinetDoor
	  * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(CabinetDoor cabinetDoor,HttpServletRequest request) throws DialogException {
		System.out.println(cabinetDoor.toString());
		Message message = new Message();
		CabinetDoor cabinetDoorResult = cabinetDoorService.selectDoorByCabinetIdAndCabinetDoorNumber(cabinetDoor.getCabinetId(),cabinetDoor.getCabinetDoorNumber());
		if(cabinetDoorResult!=null){
			message.setContent(this.OCCUPIED);
			message.setStatusCode(this.FAIL);
			message.setCallbackType("closeCurrent");
			message.setNavTabId("cabinetDoor");
			return this.ajaxDone(message);
		}else{
			try{
				boolean flag = false;
				if(StringUtils.isNotBlank(cabinetDoor.getId()))
					message.setContent(this.UPDATE);//内容提示
				else{
					message.setContent(this.SAVE);//内容提示
					flag = true;
				}
				User user = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
				if(null!=user) {
					cabinetDoor.setUserId(user.getId());
				}
				//已使用柜门+1
				if(flag){
					Cabinet cabinet = cabinetService.findUniqueById(cabinetDoor.getCabinetId());
					if(cabinet.getFullDoorCount()<cabinet.getDoorCount()){
						cabinet.setFullDoorCount(cabinet.getFullDoorCount()+1);
						cabinetService.saveOrUpdate(cabinet);
						cabinetDoorService.saveOrUpdate(cabinetDoor);
					}else{
						message.setContent(this.DOORFULL);
						message.setStatusCode(this.FAIL);
						message.setCallbackType("closeCurrent");
						message.setNavTabId("cabinetDoor");
						return this.ajaxDone(message);
					}
				}else {
					cabinetDoorService.saveOrUpdate(cabinetDoor);
				}

				message.setStatusCode(this.SUCCESS);
				message.setNavTabId("cabinetDoor");
				return this.ajaxDone(message);
			}catch(Exception ex){
				throw new DialogException(ex);
			}
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
			CabinetDoor cabinetDoor = cabinetDoorService.findUniqueById(id);
			Cabinet cabinet = cabinetService.findUniqueById(cabinetDoor.getCabinetId());
			int result = cabinetDoorService.delCabinetDoorById(id);
			//已使用柜门-1
			System.out.println(result);
			if(result>0){
				System.out.println("使用中的柜门数量为："+cabinet.getFullDoorCount());
				if(cabinet.getFullDoorCount()>0){
					cabinet.setFullDoorCount(cabinet.getFullDoorCount()-1);
					cabinetService.saveOrUpdate(cabinet);
				}
			}
			Message message = new Message();
			message.setStatusCode(this.SUCCESS);
			message.setContent(this.DELETE);//内容提示
			return this.toJsonString(message);
		}catch(Exception ex){
			throw new DialogException(ex);
		}
	}

	@RequestMapping(value = "selectDoorByEmployeeId")
	public void selectDoorByEmployeeId(HttpServletRequest request, HttpServletResponse response){
		String employeeId = request.getParameter("employeeId");
		CabinetDoor cabinetDoor = cabinetDoorService.selectDoorByEmployeeId(employeeId);
		JSONObject jsonObject = new JSONObject();
		try {
			response.getWriter().write(jsonObject.toJSONString(cabinetDoor));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "openCabinetDoor/{id}")
	public @ResponseBody String openCabinetDoor(@PathVariable("id") String id){
		System.out.println("获取到id为："+id);
		CabinetDoor cabinetDoor = cabinetDoorService.findUniqueById(id);
		String ip = cabinetService.findUniqueById(cabinetDoor.getCabinetId()).getCabinetIP();
		System.out.println("柜门的ip地址为"+ip);
		CabinetDoorServer cabinetDoorServer = CabinetDoorServer.getInstance();
		System.out.println(cabinetDoor.getCabinetDoorNumber());
		cabinetDoorServer.openDoor(ip, cabinetDoor.getCabinetDoorNumber());
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.OPENDOOR);//内容提示
		return this.toJsonString(message);
	}
	
}
