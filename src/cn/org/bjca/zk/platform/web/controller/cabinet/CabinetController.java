/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.cabinet;


import cn.org.bjca.zk.db.entity.*;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.exception.DialogException;
import cn.org.bjca.zk.platform.service.CabinetDoorService;
import cn.org.bjca.zk.platform.service.CabinetService;
import cn.org.bjca.zk.platform.service.CheckListService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.tools.SocketServer;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.CabinetPage;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
	//分配柜门
	@RequestMapping(value = "toChooseDoor/{id}",method = RequestMethod.GET)
	public String toChooseDoor(@PathVariable String id,ModelMap modelMap) throws Exception{
		System.out.println(id);
		Cabinet cabinet = cabinetService.findUniqueById(id);
		String ip = cabinet.getCabinetIP();
		String ip2 = "";
		if(ip.equals("10.11.28.25")){
		    ip2 = "10.11.28.26";
        }else if(ip.equals("10.11.28.26")){
		    ip2 = "10.11.28.25";
        }else if(ip.equals("10.11.28.21")){
            ip2 = "10.11.28.22";
        }else if(ip.equals("10.11.28.22")){
            ip2 = "10.11.28.21";
        }else if(ip.equals("10.11.28.23")){
            ip2 = "10.11.28.24";
        }else if(ip.equals("10.11.28.24")){
            ip2 = "10.11.28.23";
        }
		Cabinet cabinet2 = cabinetService.findByIp(ip2);
		String flood = "";
		if(ip.equals("10.11.28.25")||ip.equals("10.11.28.23")||ip.equals("10.11.28.21")){
			flood = "1";
		}else if(ip.equals("10.11.28.26")||ip.equals("10.11.28.24")||ip.equals("10.11.28.22")){
			flood = "2";
		}else if(ip.equals("10.11.28.27")){
			flood = "3";//12层
		}else if(ip.equals("10.11.28.20")){
			flood = "4";//20层
		}
		List<CabinetDoor> cabinetDoorList1 = cabinetDoorService.findByCabinetID(id);
		List<CabinetDoor> cabinetDoorList2 = cabinetDoorService.findByCabinetID(cabinet2.getId());
		List<String> stringList = cabinetDoorService.doorNumberToDoorName(cabinetDoorList1,cabinetDoorList2,flood);
		modelMap.addAttribute("msg","cccc_cccccc_cccc");
		List<String> yixuan = new ArrayList<>();
		modelMap.addAttribute("yishou",stringList);
		return "/cabinet/cabinetDoor/cabinetDoorFarm";
	}

	@RequestMapping(value = "toInfoFormPage/{id}", method = RequestMethod.GET)
	public String toInfoFormPage(@PathVariable String id,ModelMap modelMap){
		System.out.println(id);
		Cabinet cabinet = null;
		List<CabinetDoor> cabinetDoorList1 = new ArrayList<>();
		List<Employee> employeeList = new ArrayList<>();
		List<CabinetDoor> cabinetDoorList = new ArrayList<>();
		if(StringUtils.isNotBlank(id)&& !BLANK_PARAM_VALUE.equals(id)){
			cabinet = cabinetService.findUniqueById(id);
			for(int i=1;i<=cabinet.getDoorCount();i++){
				List<CabinetDoor> cabinetResult = cabinetDoorService.findByCabinetNumberAndDoorNumber(cabinet.getCabinetNumber(),i+"");
				if(cabinetResult==null||cabinetResult.size()==0){
					CabinetDoor cabinetDoor = new CabinetDoor();
					cabinetDoor.setCabinetDoorNumber(""+i);
					cabinetDoorList.add(cabinetDoor);
				}
			}
			employeeList = employeeService.findAllNoDoor();
			//cabinetDoorList = cabinetDoorService.findByCabinetID(id);

		}else {
			cabinet = new Cabinet();
		}
		modelMap.put("cabinet",cabinet);
		modelMap.put("cabinetDoorList", cabinetDoorList);
		modelMap.put("employeeList", employeeList);
		return "/cabinet/cabinet/cabinetInfo";
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
	//获取机柜信息
	@ResponseBody
	@RequestMapping("getCabinetInfo")
	public String getCabinetInfo(String cabinetIP){
		Cabinet cabinet = cabinetService.findByIp(cabinetIP);
		JSONObject json = new JSONObject();
		if(cabinet!=null){
			return JSONObject.toJSONString(cabinet);
		}else {
			json.put("error", "No cabinet found according to cabinetIP");
			return json.toJSONString();
		}
	}

	@RequestMapping(value = "getCabinetList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCabinetList(String cabinetId){
		List<CabinetDoor> list = cabinetDoorService.findByCabinetID(cabinetId);
		Cabinet cabinet = cabinetService.findUniqueById(cabinetId);
		String ip = cabinet.getCabinetIP();
		int type = 0;//左右
		if(ip.equals("10.11.28.21")||ip.equals("10.11.28.23")||ip.equals("10.11.28.25")){
            type = 0;
        }else if(ip.equals("10.11.28.22")||ip.equals("10.11.28.24")||ip.equals("10.11.28.26")){
            type = 1;
        }
		List<Integer> resultList = new ArrayList<>();
		List<CabinetDoor> cabinetDoorList = cabinetDoorService.findByCabinetID(cabinetId);
		if(type==0){
            for(int i=1;i<=cabinet.getDoorCount();i++){
                resultList.add(i);
            }
			for(CabinetDoor cabinetDoor:cabinetDoorList){
				resultList.remove((Object)Integer.parseInt(cabinetDoor.getCabinetDoorNumber()));
			}
        }else{
            for(int i=1;i<=cabinet.getDoorCount();i++){
                resultList.add(i+119);
            }
			for(CabinetDoor cabinetDoor:cabinetDoorList){
				resultList.remove((Object)(Integer.parseInt(cabinetDoor.getCabinetDoorNumber())+119));
			}
        }

        List<Map<String,String>> stringMap = cabinetDoorService.doorNumberToDoorName(ip,resultList);
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"\"  desc=\"\">-- 请选择柜门--</option>");
		for(Map<String, String> map:stringMap){
            for(String key : map.keySet()){
                sb.append("<option value='");
                sb.append(key);
                sb.append("'>");
                sb.append(map.get(key));
                sb.append("</option>");
            }
		}
		return sb.toString();
	}

	@RequestMapping(value = "getEmployeeList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getEmployeeList(String cabinetId){
		List<CabinetDoor> cabinetDoorList = cabinetDoorService.findByCabinetID(cabinetId);
		List<Employee> employeeList = new ArrayList<>();
		if(cabinetDoorList!=null&&cabinetDoorList.size()>0){
			for(CabinetDoor cabinetDoor:cabinetDoorList){
				String employeeId = cabinetDoor.getEmployeeId();
				Employee employee = employeeService.findUniqueById(employeeId);
				if(employee!=null){
					employeeList.add(employee);
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"\"  desc=\"\">-- 请选择人员--</option>");
		for(Employee e:employeeList){
			sb.append("<option value='");
			sb.append(e.getId());
			sb.append("'>");
			sb.append(e.getEmployeeName());
			sb.append("</option>");
		}
		return sb.toString();
	}



	@RequestMapping("openAllDoor")
	@ResponseBody
	public String openAllDoor(String id,String code){
		System.out.println("获取到id为："+id);
		Cabinet cabinet = cabinetService.findUniqueById(id);
		String ip = cabinet.getCabinetIP();
		System.out.println("柜门的ip地址为"+ip);
		SocketServer socketServer = SocketServer.getInstance();
		socketServer.openAllDoor(code,ip);
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.OPENDOOR);//内容提示
		return this.toJsonString(message);
	}

	@RequestMapping("getCabinetTable")
	@ResponseBody
	public String getCabinetTable(String cabinetId){
		return "";
	}


}
