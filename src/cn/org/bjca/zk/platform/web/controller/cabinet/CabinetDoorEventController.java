/**
 *
 */
package cn.org.bjca.zk.platform.web.controller.cabinet;


import cn.org.bjca.zk.db.entity.*;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.*;
import cn.org.bjca.zk.platform.tools.CreateDayCheckUtils;
import cn.org.bjca.zk.platform.tools.SocketServer;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.vo.HTFOAResult;
import cn.org.bjca.zk.platform.web.controller.AutoRunTask;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetDoorEventPage;
import cn.org.bjca.zk.platform.web.page.EventInfoPage;
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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
@EnableAsync
@Service
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

	@Autowired
	private CabinetService cabinetService;

	@Autowired
	private MonitorService monitorService;

	@Autowired
	private SendEventToKM sendEventToKM;


	/**
	 * <p>角色管理列表</p>
	 * @Description:
	 * @param
	 * @param request
	 * @return
	 */
	@RequestMapping("kk")
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
		}else{
			page.setPageSize(300);
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

	@RequestMapping("")
	public String eventInfoList(ModelMap modelMap, HttpServletRequest request)throws IOException{
		EventInfoPage<EventInfo> eventInfoPage = new EventInfoPage<EventInfo>();
		Page page = new Pagination();
		String pageNum = request.getParameter("pageNum");//当前页码
		String source = request.getParameter("source");//请求来源
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
		String numPerPage = request.getParameter("numPerPage");//每页显示条数
		if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}else{
			page.setPageSize(300);
		}
		String cabinetNumber = request.getParameter("cabinetNumber");//机柜编号
		if(StringUtils.isNotBlank(cabinetNumber)) {
			eventInfoPage.setCabinetNumber("%"+cabinetNumber.trim()+"%");
		}
		String cabinetDoorName = request.getParameter("cabinetDoorNumber");//柜门编号
		if(StringUtils.isNotBlank(cabinetDoorName)) {
			eventInfoPage.setCabinetDoorName("%"+cabinetDoorName.trim()+"%");
		}
		eventInfoPage.setPageVO(page);
		eventInfoPage = cabinetDoorEventService.findEventInfoPage(eventInfoPage);
		eventInfoPage.setCabinetNumber(cabinetNumber);
		eventInfoPage.setCabinetDoorName(cabinetDoorName);
		modelMap.put("eventInfoPage", eventInfoPage);
		return "/cabinet/cabinetDoorEvent/eventInfoList";
	}

	@RequestMapping("testEventInfo")
    @ResponseBody
    public String testEventInfo(){
	    List<EventInfo> list = cabinetDoorEventService.test();
	   return JSONObject.toJSONString(list);
    }

	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate(@RequestBody CabinetDoorEvent CabinetDoorEvent, HttpServletRequest request) throws DialogException {
		try {
			System.out.println("状态码是："+CabinetDoorEvent.getStatus());
			Message message = new Message();
			String doorStatus = "";
			String cabinetNumber = CabinetDoorEvent.getCabinetNumber();
			String cabinetDoorNumber = CabinetDoorEvent.getCabinetDoorNumber();
			CabinetDoor cabinetDoor = new CabinetDoor();
			cabinetDoor.setCabinetDoorNumber(cabinetDoorNumber);
			Cabinet cabinet = cabinetService.findByCabinetNumber(cabinetNumber);
			CabinetDoor cabinetDoorResult = cabinetDoorService.selectDoorByCabinetIdAndCabinetDoorNumber(cabinet.getId(),cabinetDoorNumber);
			cabinetDoorResult.setDoorOptTime(new Timestamp(new Date().getTime()));
			cabinetDoorService.saveOrUpdate(cabinetDoorResult);
			int status = Integer.parseInt(CabinetDoorEvent.getStatus());
			if(status==0||status==2){
				doorStatus = "1";
			}else{
				doorStatus = "2";
			}
			cabinetDoorResult.setStatus(doorStatus);
			String icCardNumber = CabinetDoorEvent.getEmployeeCardNumber();
			//根据卡号查找工号
			Employee employeeResult = employeeService.findByicCardNumber(icCardNumber);
//			CabinetDoorEvent.setEmployeeCardNumber(employeeResult.getEmployeeNumber());
			if(status==4||status==2){//这个是判断晚存和紧急for金鹰
				System.out.println("紧急开门");
				UrgentEvent urgentEvent = sendEventToKM.createUrgent(CabinetDoorEvent,employeeResult);
				cabinetDoorEventService.insertUrgentEvent(urgentEvent);
			}
			if (StringUtils.isNotBlank(CabinetDoorEvent.getId()))
				message.setContent(this.UPDATE);//内容提示
			else {
				message.setContent(this.SAVE);//内容提示
				cabinetDoor.setId(EssPdfUtil.genrRandomUUID());
			}
			CabinetDoorEvent.setRemark(CabinetDoorEvent.getRemark().replace(" ",""));
			String cabinetEventId = cabinetDoorEventService.saveOrUpdate(CabinetDoorEvent);
			cabinetDoorService.saveOrUpdate(cabinetDoorResult);
			message.setStatusCode(this.SUCCESS);
			message.setCallbackType("closeCurrent");
			message.setNavTabId("cabinetDoorEvent");
			CabinetDoorEvent.setCabinetIp(cabinet.getCabinetIP());
			sendEventToKM.sendEventMessageToKM(CabinetDoorEvent,employeeResult.getEmployeeNumber());
			Date date = new Date(CabinetDoorEvent.getDoorOptTime().getTime());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = simpleDateFormat.format(date);
			sendEventToKM.createPic(CabinetDoorEvent,startTime);
			return this.ajaxDone(message);
		}catch (Exception ex){
			throw new DialogException(ex);
		}
	}

//	@RequestMapping("")
	public String eventInfoList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		EventInfoPage<EventInfo> eventInfoPage = new EventInfoPage<EventInfo>();
		Page page = new Pagination();
		String pageNum = request.getParameter("pageNum");//当前页码
		String source = request.getParameter("source");//请求来源
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
		String numPerPage = request.getParameter("numPerPage");//每页显示条数
		numPerPage = "300";
		if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		String cabinetNumber = request.getParameter("cabinetNumber");//机柜编号
		if(StringUtils.isNotBlank(cabinetNumber)) {
			eventInfoPage.setCabinetNumber("%"+cabinetNumber.trim()+"%");
		}

		String cabinetDoorName = request.getParameter("cabinetDoorName");//柜门编号
		if(StringUtils.isNotBlank(cabinetDoorName)) {
			eventInfoPage.setCabinetDoorName("%"+cabinetDoorName.trim()+"%");
		}
		eventInfoPage.setPageVO(page);
		eventInfoPage = cabinetDoorEventService.findEventInfoPage(eventInfoPage);
		modelMap.put("eventInfoPage", eventInfoPage);
		eventInfoPage.setCabinetNumber(cabinetNumber);
		eventInfoPage.setCabinetDoorName(cabinetDoorName);
		return "/cabinet/cabinetDoorEvent/eventInfoList";

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
		}else if(BLANK_PARAM_VALUE.equals(id)){
			List<Cabinet> cabinetList = cabinetService.getAll();
			modelMap.put("cabinetList",cabinetList);
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
		SocketServer socketServer = SocketServer.getInstance();
		socketServer.sendDoorMessage("0","192.168.3.140", "1");
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

	@RequestMapping(value = "findOneDayEvent",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findOneDayEvent(HttpServletRequest request)throws ParseException{
		String date = request.getParameter("date");
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date requestDate = sdf.parse(date);
		List<CabinetDoorEvent> list = cabinetDoorEventService.findOneDay(requestDate);
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping(value = "checkDayInfoTest" ,produces ="text/html;charset=UTF-8")
	public String checkDayInfoTest(){
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toJSONString(cabinetDoorEventService.findDayInfo(new Date()));
	}

	//手动生成当天日报表
	@RequestMapping(value = "checkDayInfo" ,produces ="text/html;charset=UTF-8")
	public ModelAndView checkDayInfo(String date) throws ParseException {
		JSONObject jsonObject = new JSONObject();
		Message message = new Message();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(date);
		List<CheckInfo> listMata = cabinetDoorEventService.findDayInfo(simpleDateFormat.parse(date));//获取当天存取记录
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
					checkInfo.setPushTime(checkInfo.getPushTime()+" "+ci.getDoorOptTime()+" 晚存："+ci.getRemark().replaceAll(" ",""));
					checkInfo.setPushStatus(checkInfo.getPushStatus()+" 晚存");
					reason = " 晚存：";
				}else if("1".equals(ci.getDoorOptStatus())){//修改取件时间
					checkInfo.setPullTime(checkInfo.getPullTime()+" "+ci.getDoorOptTime());
					checkInfo.setPullStatus(checkInfo.getPullStatus()+" 正常取");
				}else if("3".equals(ci.getDoorOptStatus())){//修改取件时间
					checkInfo.setPullTime(checkInfo.getPullTime()+" "+ci.getDoorOptTime()+" 早取："+ci.getRemark().replaceAll(" ",""));
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
				checkInfo.setEmployeeNumber(ci.getEmployeeNumber());
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
					checkInfo.setPushTime(ci.getDoorOptTime()+" 晚存："+ci.getRemark().replaceAll(" ",""));
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
					checkInfo.setPullTime(ci.getDoorOptTime()+" 早取："+ci.getRemark().replaceAll(" ",""));
					checkInfo.setPullStatus("早取");
					checkInfo.setPushTime("");
					checkInfo.setPushStatus("");
					checkInfo.setRemark("早取："+ci.getRemark());
				}
				responseList.add(checkInfo);
			}
		}
		//未交手机生成事件
		List<CheckInfo> checkInfoList = cabinetDoorEventService.absentEmp(simpleDateFormat.parse(date));
		responseList.addAll(checkInfoList);
		String objNo = "";
		String OAInfo = "";
		for (CheckInfo c:responseList){
			String employeeNumber = employeeService.findByicCardNumber(c.getIcCardNumber()).getEmployeeNumber();
			objNo += employeeNumber+",";
			//金鹰考勤
			//String OAInfo = getOA(objNo);
			//String OAInfo = "";
			//c.setOAInfo(OAInfo);
		}
		//汇添富考勤
		OAInfo = new AutoRunTask().getHtfOA(objNo,date);
		JSONArray jsonArray = JSONArray.parseArray(OAInfo);
		JSONObject jsonObjectArray = (JSONObject) jsonArray.get(0);
		String resultMessage = (String)jsonObjectArray.get("message");
		if("ok".equals(resultMessage)){
			JSONArray dataArray = (JSONArray) jsonObjectArray.get("data");
			List<HTFOAResult> list = dataArray.toJavaList(HTFOAResult.class);
			for(CheckInfo checkInfo:responseList){
				String employeeNumber = employeeService.findByicCardNumber(checkInfo.getIcCardNumber()).getEmployeeNumber();
				if(list.size()>0){
					for(HTFOAResult htfoaResult:list){
//							System.out.println(checkInfo.getEmployeeNumber());
						if(htfoaResult.getObjno().equals(employeeNumber)){
							checkInfo.setOAInfo(htfoaResult.getOfftime()+returnOAInfo(htfoaResult.getAtttype()));
							list.remove(htfoaResult);
							break;
						}else {
							checkInfo.setOAInfo("正常出勤");
						}
					}
				}else{
					checkInfo.setOAInfo("正常出勤");
				}
			}
		}
		else{
			for(CheckInfo checkInfo:responseList){
				checkInfo.setOAInfo("正常出勤");
			}
		}
		HTFCheck htfCheck = CreateDayCheckUtils.checkDayCheck(responseList, simpleDateFormat.parse(date));
		checkListService.add(htfCheck);
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.SAVE);
		message.setNavTabId("checkList");
		return this.ajaxDone(message);
	}

	//循环生成日报表
	//@RequestMapping(value = "checkDayInfo" ,produces ="text/html;charset=UTF-8")
	public ModelAndView checkDayInfoXunHuan(String date) throws ParseException {
		Date today = new Date();
		long l1 = today.getTime();
		while (true){
			JSONObject jsonObject = new JSONObject();
			Message message = new Message();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(date);
			if(simpleDateFormat.parse(date).getTime()>=l1){
				break;
			}
			List<CheckInfo> listMata = cabinetDoorEventService.findDayInfo(simpleDateFormat.parse(date));//获取当天存取记录
			if(listMata.size()==0){
				Date date1 = simpleDateFormat.parse(date);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date1);
				calendar.add(Calendar.DAY_OF_MONTH,1);
				date = simpleDateFormat.format(calendar.getTime());
				continue;
			}
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
						checkInfo.setPushTime(checkInfo.getPushTime()+" "+ci.getDoorOptTime()+" 晚存："+ci.getRemark().replaceAll(" ",""));
						checkInfo.setPushStatus(checkInfo.getPushStatus()+" 晚存");
						reason = " 晚存：";
					}else if("1".equals(ci.getDoorOptStatus())){//修改取件时间
						checkInfo.setPullTime(checkInfo.getPullTime()+" "+ci.getDoorOptTime());
						checkInfo.setPullStatus(checkInfo.getPullStatus()+" 正常取");
					}else if("3".equals(ci.getDoorOptStatus())){//修改取件时间
						checkInfo.setPullTime(checkInfo.getPullTime()+" "+ci.getDoorOptTime()+"早取："+ci.getRemark().replaceAll(" ",""));
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
					checkInfo.setEmployeeNumber(ci.getEmployeeNumber());
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
						checkInfo.setPushTime(ci.getDoorOptTime()+" 晚存："+ci.getRemark().replaceAll(" ",""));
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
						checkInfo.setPullTime(ci.getDoorOptTime()+" 早取："+ci.getRemark().replaceAll(" ",""));
						checkInfo.setPullStatus("早取");
						checkInfo.setPushTime("");
						checkInfo.setPushStatus("");
						checkInfo.setRemark("早取："+ci.getRemark());
					}
					responseList.add(checkInfo);
				}
			}
			//未交手机生成事件
			List<CheckInfo> checkInfoList = cabinetDoorEventService.absentEmp(simpleDateFormat.parse(date));
			responseList.addAll(checkInfoList);
			String objNo = "";
			String OAInfo = "";
			for (CheckInfo c:responseList){
				String employeeNumber = employeeService.findByicCardNumber(c.getIcCardNumber()).getEmployeeNumber();
				objNo += employeeNumber+",";
				//金鹰考勤
				//String OAInfo = getOA(objNo);
				//String OAInfo = "";
				//c.setOAInfo(OAInfo);
			}
			//汇添富考勤
			OAInfo = new AutoRunTask().getHtfOA(objNo,date);
			JSONArray jsonArray = JSONArray.parseArray(OAInfo);
			JSONObject jsonObjectArray = (JSONObject) jsonArray.get(0);
			String resultMessage = (String)jsonObjectArray.get("message");
			if("ok".equals(resultMessage)){
				JSONArray dataArray = (JSONArray) jsonObjectArray.get("data");
				List<HTFOAResult> list = dataArray.toJavaList(HTFOAResult.class);
				for(CheckInfo checkInfo:responseList){
					String employeeNumber = employeeService.findByicCardNumber(checkInfo.getIcCardNumber()).getEmployeeNumber();
					if(list.size()>0){
						for(HTFOAResult htfoaResult:list){
//							System.out.println(checkInfo.getEmployeeNumber());
							if(htfoaResult.getObjno().equals(employeeNumber)){
								checkInfo.setOAInfo(htfoaResult.getOfftime()+returnOAInfo(htfoaResult.getAtttype()));
								list.remove(htfoaResult);
								break;
							}else {
								checkInfo.setOAInfo("正常出勤");
							}
						}
					}else{
						checkInfo.setOAInfo("正常出勤");
					}
				}
			}else{
				for(CheckInfo checkInfo:responseList){
					checkInfo.setOAInfo("正常出勤");
				}
			}
			HTFCheck htfCheck = CreateDayCheckUtils.checkDayCheck(responseList, simpleDateFormat.parse(date));
			checkListService.add(htfCheck);
			message.setStatusCode(this.SUCCESS);
			message.setContent(this.SAVE);
			message.setNavTabId("checkList");
//			System.out.println(date);
			Date date1 = simpleDateFormat.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			calendar.add(Calendar.DAY_OF_MONTH,1);
			date = simpleDateFormat.format(calendar.getTime());
//			return this.ajaxDone(message);
		}
		return null;

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

	//转换考勤信息
	public String returnOAInfo(String s){
		if(s.equals("leave")){
			return "请假";
		}else if(s.equals("trip")){
			return "出差";
		}else{
			return "获取失败";
		}
	}

	@ResponseBody
	@RequestMapping("testVideo")
	public String testVideo(String cabinetNumber, String cabinetDoorEventId,String startTime){
		new SendEventToKM().createVideos(cabinetNumber,cabinetDoorEventId,startTime);
		return "hello hikvision";
	}

	@ResponseBody
	@RequestMapping("testConVideo")
	public String testConVideo(String oldpath,String newpath){
		new SendEventToKM().conVideo(oldpath,newpath);
		return "hello hikvision";
	}

	@RequestMapping("todayVideo")
	public void todayVideo(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<CabinetDoorEvent> list = cabinetDoorEventService.findOneDay(simpleDateFormat1.parse(date));
		SendEventToKM sendEventToKM = new SendEventToKM();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(CabinetDoorEvent cabinetDoorEvent:list){
			long l = cabinetDoorEvent.getDoorOptTime().getTime();
			Date date1 = new Date(l);
			String starttime = simpleDateFormat.format(date1);
			sendEventToKM.createVideos(cabinetDoorEvent.getCabinetNumber(),cabinetDoorEvent.getId(),starttime);
		}
	}

	@RequestMapping("jiematodayVideo")
	public void jiemaTodayVideo(String date) throws ParseException {
	    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<CabinetDoorEvent> list = cabinetDoorEventService.findOneDay(simpleDateFormat1.parse(date));
		String filePath = "/usr/share/tomcat/hk/HKVideos/";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SendEventToKM sendEventToKM = new SendEventToKM();
		for(CabinetDoorEvent cabinetDoorEvent:list){
			Date date1 = new Date();
			String fileName = simpleDateFormat.format(date1)+".mp4";
			String newpath = filePath+fileName;
			String oldpath = filePath+cabinetDoorEvent.getId()+".mp4";
			sendEventToKM.conVideo(oldpath,newpath);
			monitorService.addVideo(cabinetDoorEvent.getId(),fileName);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	@ResponseBody
	@RequestMapping("testPic")
	public String testPic(String startTime){
		new SendEventToKM().createPic(new CabinetDoorEvent(),startTime);
		return "hello hikvision";
	}

	@RequestMapping(value = "sendEventToKM")
	public void sendEventToKM(@RequestBody CabinetDoorEvent cabinetDoorEvent, HttpServletRequest request) throws DialogException {
		try {
			//new SendEventToKM().sendEventMessageToKM(cabinetDoorEvent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("showMonitorPic")
	public String showPic(String picFilePath, ModelMap modelMap){
		modelMap.addAttribute("picFilePath",picFilePath);
		return "/cabinet/cabinetDoorEvent/showPic";
	}

	@RequestMapping("showMonitorVideo")
	public String showVideo(String videoFilePath, ModelMap modelMap){
		modelMap.addAttribute("videoFilePath",videoFilePath);
		return "/cabinet/cabinetDoorEvent/showVideo";
	}

	@RequestMapping("sendAllEvent")
	@ResponseBody
	public String sendAllEvent(){
		List<CabinetDoorEvent> cabinetDoorEventList = cabinetDoorEventService.findAllNeedSend();
		cabinetDoorEventService.sendAll();
		for(CabinetDoorEvent cabinetDoorEvent:cabinetDoorEventList){
			String icCard = cabinetDoorEvent.getEmployeeCardNumber();
			Employee employee = employeeService.findByicCardNumber(icCard);
			try {
				cabinetDoorEvent.setTemporaryStatus(0);
				sendEventToKM.sendEventMessageToKM(cabinetDoorEvent,employee.getEmployeeNumber());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.UPDATE);//内容提示
		return this.toJsonString(message);
	}

	@RequestMapping(value = "sendOneEvent", method = RequestMethod.GET)
	public String sendOneEvent(String id,ModelMap modelMap){
		cabinetDoorEventService.send(id);
		CabinetDoorEvent cabinetDoorEvent = cabinetDoorEventService.findUniqueById(id);
		String icCard = cabinetDoorEvent.getEmployeeCardNumber();
		Employee employee = employeeService.findByicCardNumber(icCard);
		try {
			sendEventToKM.sendEventMessageToKM(cabinetDoorEvent,employee.getEmployeeNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.DELETE);//内容提示
		return this.toJsonString(message);
	}

}
