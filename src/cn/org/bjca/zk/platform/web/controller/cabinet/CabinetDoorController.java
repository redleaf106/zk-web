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
import cn.org.bjca.zk.platform.tools.SocketServer;
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
import java.util.ArrayList;
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
		}else{
			page.setPageSize(50);
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
		String yirenduogui = request.getParameter("yirenduogui");
		if("yes".equals(yirenduogui)){
			cabinetDoorPage.setPageVO(page);
			cabinetDoorPage = cabinetDoorService.findPageyrdg(cabinetDoorPage);
			cabinetDoorPage.setCabinetNumber(cabinetNumber);
			cabinetDoorPage.setCabinetDoorNumber(cabinetDoorNumber);
			cabinetDoorPage.setEmployeeName(employeeName);
		}else{
			cabinetDoorPage.setPageVO(page);
			cabinetDoorPage = cabinetDoorService.findPage(cabinetDoorPage);
			cabinetDoorPage.setCabinetNumber(cabinetNumber);
			cabinetDoorPage.setCabinetDoorNumber(cabinetDoorNumber);
			cabinetDoorPage.setEmployeeName(employeeName);
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
		List<Employee> employeeList = new ArrayList<>();
		if(StringUtils.isNotBlank(id)&& "-2".equals(id)){
			employeeList = employeeService.getAll();
		}else {
			employeeList = employeeService.findAllNoDoor();
		}
		modelMap.put("cabinetDoor",cabinetDoor);
		modelMap.put("cabinetList", cabinetList);
		modelMap.put("employeeList", employeeList);
		modelMap.addAttribute("msg","cccc_cccccc_cccc");
		List<String> yixuan = new ArrayList<>();
		yixuan.add("A_3");
		yixuan.add("A_4");
		modelMap.addAttribute("yishou",yixuan);
		return "/cabinet/cabinetDoor/cabinetDoorForm";
	}

	@RequestMapping(value = "/chooseDoor", method = RequestMethod.GET)
	public String chooseDoor(ModelMap modelMap){

		List<Cabinet> cabinetList = cabinetService.getAll();
		List<Employee> employeeList = employeeService.findAllNoDoor();
		modelMap.put("cabinetList", cabinetList);
		modelMap.put("employeeList", employeeList);
		//modelMap.addAttribute("flood","16");
		return "/cabinet/cabinetDoor/index2";
	}

	@RequestMapping(value = "/updateCabinetImg",method = RequestMethod.POST)
	@ResponseBody
	public void  updateCabinetImg(String cabinetId,String pageImg){
		cabinetDoorService.saveCabinetImg(cabinetId,pageImg);
	}
	/**
	 * <p>保存或更新表单信息</p>
	 * @Description:
	 * @param cabinetDoor
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(CabinetDoor cabinetDoor,HttpServletRequest request) throws DialogException {
		String pageImg = cabinetDoor.getExt2();
		Message message = new Message();
		boolean canUpdateFlag = true;
		//String cabinetDoorName = cabinetDoorService.setCabinetDoorName(cabinetDoor);
		//cabinetDoor.setCabinetDoorName(cabinetDoorName);
		CabinetDoor cabinetDoorResult = cabinetDoorService.selectDoorByCabinetIdAndCabinetDoorNumber(cabinetDoor.getCabinetId(),cabinetDoor.getCabinetDoorNumber());
		if(cabinetDoorResult!=null){
			Employee employee = cabinetDoorResult.getEmployee();
			if(employee!=null){
				message.setContent(this.OCCUPIED);
				message.setStatusCode(this.FAIL);
				message.setNavTabId("cabinetDoor");
				canUpdateFlag = false;//查到柜门已被占用，不允许再次绑定
			}else {
				canUpdateFlag = true;//柜门被绑定，但是没有使用人
				message.setContent(this.UPDATE);//内容提示，修改成功
				message.setStatusCode(this.SUCCESS);
				message.setCallbackType("closeCurrent");
				message.setNavTabId("cabinetDoor");
				//cabinetDoorService.saveOrUpdate(cabinetDoor);
			}
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
						cabinetDoorService.saveCabinetImg(cabinetDoor.getCabinetId(),pageImg);
					}else{
						message.setContent(this.DOORFULL);
						message.setStatusCode(this.FAIL);
						message.setNavTabId("cabinetDoor");
						return this.ajaxDone(message);
					}
				}else {
					cabinetDoorService.saveOrUpdate(cabinetDoor);
					cabinetDoorService.saveCabinetImg(cabinetDoor.getCabinetId(),pageImg);
				}
				message.setStatusCode(this.SUCCESS);
				message.setCallbackType("closeCurrent");
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
				if(cabinetDoor.getEmployee()!=null){//如果柜门有人在使用
					//给安卓发送指令解除绑定关系
					SocketServer socketServer = SocketServer.getInstance();
					socketServer.removeCabinetOfEmp("2",cabinet.getCabinetIP(),cabinetDoor.getEmployee().getIcCardNumber());
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
		List<CabinetDoor> cabinetDoor = cabinetDoorService.selectDoorByEmployeeId(employeeId);
		JSONObject jsonObject = new JSONObject();
		try {
			response.getWriter().write(jsonObject.toJSONString(cabinetDoor));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "getDoorByEmployeeIdAndIP")
	public void getDoorByEmployeeIdAndIP(HttpServletRequest request, HttpServletResponse response){
		String employeeId = request.getParameter("employeeId");
		String ip = request.getParameter("IP");
		CabinetDoor cabinetDoor = cabinetDoorService.selectDoorByEmployeeIdAndIP(employeeId,ip);
		String result = "";
		if(cabinetDoor!=null) {
			JSONObject jsonObject = new JSONObject();
			result = jsonObject.toJSONString(cabinetDoor);
		}else{
			result = "no cabinetDoor found!";
		}
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@RequestMapping(value = "openCabinetDoor/{id}",produces = "text/plain;charset=UTF-8")
	public @ResponseBody String openCabinetDoor(@PathVariable("id") String id){
		System.out.println("获取到id为："+id);
		CabinetDoor cabinetDoor = cabinetDoorService.findUniqueById(id);
		String ip = cabinetService.findUniqueById(cabinetDoor.getCabinetId()).getCabinetIP();
		System.out.println("柜门的ip地址为"+ip);
		SocketServer socketServer = SocketServer.getInstance();
		System.out.println(cabinetDoor.getCabinetDoorNumber());
		socketServer.sendDoorMessage("0",ip, cabinetDoor.getCabinetDoorNumber());
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.OPENDOOR);//内容提示
		return this.toJsonString(message);
	}

	@RequestMapping("test")
	@ResponseBody
	public String test(){
		System.out.println("test...");
		return "success";
	}

	@RequestMapping(value = "/getCabinedoorList",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCabinedoorList(String cabinetId){
		List<CabinetDoor> list = new ArrayList<CabinetDoor>();
		list = cabinetDoorService.findCEByCabinetID(cabinetId);
		return toJsonString(list);
	}
}
