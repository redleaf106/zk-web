/**
 *
 */
package cn.org.bjca.zk.platform.web.controller.cabinet;


import cn.org.bjca.zk.db.entity.*;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.*;
import cn.org.bjca.zk.platform.tools.CabinetDoorServer;
import cn.org.bjca.zk.platform.tools.CreateDayCheckUtils;
import cn.org.bjca.zk.platform.web.controller.AutoRunTask;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetDoorEventPage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.bjca.seal.esspdf.core.pagination.page.Page;
import com.cn.bjca.seal.esspdf.core.pagination.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

	@Autowired
	private CheckListService checkListService;

	@Autowired
	private EmployeeService employeeService;


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
			Message message = new Message();
			if(Integer.parseInt(CabinetDoorEvent.getStatus())==4){
				System.out.println("紧急开门");
				UrgentEvent urgentEvent = new UrgentEvent();
				String icCardNumber = CabinetDoorEvent.getEmployeeCardNumber();
				urgentEvent.setEmployeeCardNumber(icCardNumber);
				String employeeName = employeeService.findByicCardNumber(icCardNumber).getEmployeeName();
				System.out.println("员工姓名为"+employeeName);
				urgentEvent.setEmployeeName(employeeName);
				urgentEvent.setRemark(CabinetDoorEvent.getRemark());
				cabinetDoorEventService.insertUrgentEvent(urgentEvent);
			}else if(Integer.parseInt(CabinetDoorEvent.getStatus())>1){
				System.out.println("存取出现异常行为");
				//emailService.sendOneMail(CabinetDoorEvent);
			}else {

				if(StringUtils.isNotBlank(CabinetDoorEvent.getId()))
					message.setContent(this.UPDATE);//内容提示
				else{
					message.setContent(this.SAVE);//内容提示
				}
				//User user = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
				cabinetDoorEventService.saveOrUpdate(CabinetDoorEvent);
				List<CabinetDoor> list = cabinetDoorService.findByCabinetNumberAndDoorNumber(CabinetDoorEvent.getCabinetNumber(), CabinetDoorEvent.getCabinetDoorNumber());
				CabinetDoor cabinetDoor = list.get(0);
				//存取次数+1
				cabinetDoor.setAccessCount(cabinetDoor.getAccessCount()+1);
				message.setStatusCode(this.SUCCESS);
				message.setCallbackType("closeCurrent");
				message.setNavTabId("cabinetDoorEvent");
			}
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

	/**
	 * 生成日报表月报表
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "createExcel")
	@ResponseBody
	public String jsonToExecl(String type) throws IOException {
		List list = cabinetDoorEventService.getAll();
		JSONObject jsonObject = new JSONObject();
		Set<String> keys = null;
		// 创建HSSFWorkbook对象
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建HSSFSheet对象
		HSSFSheet sheet = wb.createSheet("sheet0");
		int roleNo = 0;
		int rowNo = 0;
		// 创建HSSFRow对象
		HSSFRow row = sheet.createRow(roleNo++);
		jsonObject.put("data", list);
		// 创建HSSFCell对象
		if (keys == null) {
			//标题
			keys = jsonObject.keySet();
			for (String s : keys) {
				HSSFCell cell = row.createCell(rowNo++);
				cell.setCellValue(s);
			}
			rowNo = 0;
			row = sheet.createRow(roleNo++);
		}

		for (String s : keys) {
			HSSFCell cell = row.createCell(rowNo++);
			cell.setCellValue(jsonObject.getString(s));
		}
		rowNo = 0;
		System.out.println(rowNo);
		FileOutputStream output = new FileOutputStream("c://target.xls");
		wb.write(output);
		output.flush();
		output.close();

		return jsonObject.toJSONString(list);
	}

	@RequestMapping(value = "findOneDay")
	@ResponseBody
	public String findOneDay(HttpServletRequest request) throws ParseException {
		String employeeIcCardNumber = request.getParameter("employeeIcCardNumber");
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		Date dangtian = sdf.parse(str);
		List list = cabinetDoorEventService.findOneEMPByOneDay(employeeIcCardNumber,dangtian);
		return jsonObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping(value = "checkDayInfoTest" ,produces ="text/html;charset=UTF-8")
	public String checkDayInfoTest(){
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toJSONString(cabinetDoorEventService.findDayInfo());
	}

	@ResponseBody
	@RequestMapping(value = "checkDayInfo" ,produces ="text/html;charset=UTF-8")
	public String checkDayInfo(){
		JSONObject jsonObject = new JSONObject();
		List<CheckInfo> listMata = cabinetDoorEventService.findDayInfo();//获取当天存取记录
		List<CheckInfo> responseList = new LinkedList<>();
		for(CheckInfo ci:listMata){
			if(responseList.contains(ci)){//判断开关门事件是否是同属于一个人
				//如果是一个人，则修改数据
				int index = responseList.indexOf(ci);//获取这个元素的下标
				CheckInfo checkInfo = responseList.get(index);
				String reason = "";
				if("0".equals(ci.getDoorOptStatus())){//修改存件时间
					checkInfo.setPushTime(checkInfo.getPushTime()+" "+ci.getDoorOptTime());
					checkInfo.setPushStatus(checkInfo.getPushStatus()+" 正常存");
				}else if("2".equals(ci.getDoorOptStatus())){//修改存件时间
					checkInfo.setPushTime(checkInfo.getPushTime()+" "+ci.getDoorOptTime());
					checkInfo.setPushStatus(checkInfo.getPushStatus()+" 晚存");
					reason = " 晚存：";
				}else if("1".equals(ci.getDoorOptStatus())){//修改取件时间
					checkInfo.setPullTime(checkInfo.getPullTime()+" "+ci.getDoorOptTime());
					checkInfo.setPullStatus(checkInfo.getPullStatus()+" 正常取");
				}else if("3".equals(ci.getDoorOptStatus())){//修改取件时间
					checkInfo.setPullTime(checkInfo.getPullTime()+" "+ci.getDoorOptTime());
					checkInfo.setPullStatus(checkInfo.getPullStatus()+" 早取");
					reason = " 早取：";
				}
				if(ci.getRemark()!=null||ci.getRemark().length()>0){
					checkInfo.setRemark(checkInfo.getRemark()+reason+ci.getRemark());
				}
				responseList.set(index,checkInfo);
			}else{
				//如果不是一个人，则加上这个人
				CheckInfo checkInfo = new CheckInfo();
				checkInfo.setCabinetDoorNumber(ci.getCabinetDoorNumber());
				checkInfo.setDepartmentName(ci.getDepartmentName());
				checkInfo.setEmployeeName(ci.getEmployeeName());
				checkInfo.setIcCardNumber(ci.getIcCardNumber());
				checkInfo.setRemark(ci.getRemark());
				if("0".equals(ci.getDoorOptStatus())){//修改存件时间
					checkInfo.setPushTime(ci.getDoorOptTime());
					checkInfo.setPushStatus("正常存");
					checkInfo.setPullTime("");
					checkInfo.setPullStatus("");
				}else if("2".equals(ci.getDoorOptStatus())){//修改存件时间
					checkInfo.setPushTime(ci.getDoorOptTime());
					checkInfo.setPushStatus("晚存");
					checkInfo.setPullTime("");
					checkInfo.setPullStatus("");
					checkInfo.setRemark("晚存："+ci.getRemark());
				}else if("1".equals(ci.getDoorOptStatus())){//修改取件时间
					checkInfo.setPullTime(ci.getDoorOptTime());
					checkInfo.setPullStatus("正常取");
					checkInfo.setPushTime("");
					checkInfo.setPushStatus("");
				}else if("3".equals(ci.getDoorOptStatus())){//修改取件时间
					checkInfo.setPullTime(ci.getDoorOptTime());
					checkInfo.setPullStatus("早取");
					checkInfo.setPushTime("");
					checkInfo.setPushStatus("");
					checkInfo.setRemark("早取："+ci.getRemark());
				}
				responseList.add(checkInfo);
			}
		}
		for (CheckInfo c:responseList){
			String objNo = c.getIcCardNumber();
			AutoRunTask autoRunTask = new AutoRunTask();
			String OAInfo = autoRunTask.getOA(objNo);
			c.setOAInfo(OAInfo);
		}
		HTFCheck htfCheck = CreateDayCheckUtils.checkDayCheck(responseList);
		checkListService.add(htfCheck);
		return jsonObject.toJSONString(responseList);
	}

	//获取所有未发邮件的应急开门事件
	@RequestMapping(value = "getAllUnactivatedUrgentEvent" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllUnactivatedUrgentEvent(){
		List<UrgentEvent> list = cabinetDoorEventService.getAllUnactivatedUrgentEvent();
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
		return jsonArray.toJSONString();
	}

	//应急事件邮件状态更新
	@ResponseBody
	@RequestMapping(value = "updateUrgentEventEmailStatus")
	public String updateUrgentEventEmailStatus(int id){
		int result =cabinetDoorEventService.updateUrgentEventEmailStatus(id);
		if(result>0){
			return "success";
		}
		return "error";
	}


}
