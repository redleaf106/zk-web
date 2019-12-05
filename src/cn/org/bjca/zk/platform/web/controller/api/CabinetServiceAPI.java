/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.api;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.org.bjca.zk.db.entity.CabinetDoorEvent;
import cn.org.bjca.zk.db.entity.Department;
import cn.org.bjca.zk.db.entity.SoftwarePackage;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.ResultEnum;
import cn.org.bjca.zk.platform.exception.BusinessException;
import cn.org.bjca.zk.platform.service.CabinetDoorEventService;
import cn.org.bjca.zk.platform.service.DepartmentService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.service.SoftwarePackageService;
import cn.org.bjca.zk.platform.web.controller.api.vo.CabinetDoorEventRequest;
import cn.org.bjca.zk.platform.web.controller.api.vo.CabinetDoorEventResponse;
import cn.org.bjca.zk.platform.web.controller.api.vo.DepartmentResponse;
import cn.org.bjca.zk.platform.web.controller.api.vo.EmployeeRegisterRequest;
import cn.org.bjca.zk.platform.web.controller.api.vo.EmployeeRegisterResponse;
import cn.org.bjca.zk.platform.web.controller.api.vo.SoftwarePackageResponse;

/***************************************************************************

 * @文件名称: CabinetServiceAPI.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.api
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月29日
 ***************************************************************************/
@Controller
public class CabinetServiceAPI {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EmployeeService employeeService; 
	
	@Autowired
	private CabinetDoorEventService cabinetDoorEventService; 
	
	@Autowired
	private SoftwarePackageService softwarePackageService;
	
	@Autowired
	private DepartmentService  departmentService;
	
	
	
	 @Value("#{sysConfig['downloadAddress']}")
	private String address;
	
	
	@RequestMapping("/sendDoorEvent")
	public @ResponseBody CabinetDoorEventResponse sendDoorEvent(@RequestBody CabinetDoorEventRequest cabinetDoorEventRequest,HttpServletRequest request)throws BusinessException {
		logger.info("=========sendDoorEvent request = [{}]", JSON.toJSONString(cabinetDoorEventRequest));
		CabinetDoorEvent cabinetDoorEvent = new CabinetDoorEvent();
		cabinetDoorEvent.setCabinetNumber(cabinetDoorEventRequest.getMachineNumber());
		cabinetDoorEvent.setActionCardNumber(cabinetDoorEventRequest.getActionUserId());
		cabinetDoorEvent.setCabinetDoorNumber(cabinetDoorEventRequest.getDoorId());
		cabinetDoorEvent.setDoorOptTime(new Timestamp(Long.parseLong(cabinetDoorEventRequest.getActionTime())));
		cabinetDoorEvent.setEmployeeCardNumber(cabinetDoorEventRequest.getAccountPKId());
		cabinetDoorEvent.setStatus(cabinetDoorEventRequest.getStatus());
		cabinetDoorEvent.setRemark(cabinetDoorEventRequest.getRemark());
		cabinetDoorEventService.saveOrUpdate(cabinetDoorEvent);
		CabinetDoorEventResponse cabinetDoorEventResponse = new CabinetDoorEventResponse();
		cabinetDoorEventResponse.setCode(ResultEnum.SUCCESS.getCode());
		cabinetDoorEventResponse.setMessage(ResultEnum.SUCCESS.getMessage());
		logger.debug("=========sendDoorEvent response = [{}]", JSON.toJSONString(cabinetDoorEventResponse));
		return cabinetDoorEventResponse;
	} 
	
	
	@RequestMapping("/sendEmployeeRegisterInfo")
	public @ResponseBody EmployeeRegisterResponse sendUserRegisterInfo(@RequestBody EmployeeRegisterRequest employeeRegisterRequest, HttpServletRequest request) throws BusinessException{
		
		logger.debug("=========sendUserRegisterInfo request = [{}]", JSON.toJSONString(employeeRegisterRequest));
		
		employeeService.regist(employeeRegisterRequest);
		
		EmployeeRegisterResponse employeeRegisterResponse  = new EmployeeRegisterResponse(); 
		employeeRegisterResponse.setCode(ResultEnum.SUCCESS.getCode());
		employeeRegisterResponse.setMessage(ResultEnum.SUCCESS.getMessage());
		logger.debug("=========sendUserRegisterInfo response = [{}]", JSON.toJSONString(employeeRegisterResponse));
		return employeeRegisterResponse;
	} 
	

	@RequestMapping("/getDepartmentList")
	public @ResponseBody DepartmentResponse getDepartmentList(HttpServletRequest request) throws BusinessException{
		List<Department> departmentList =  departmentService.getAll();
		DepartmentResponse departmentResponse = new DepartmentResponse();
		departmentResponse.setCode(ResultEnum.SUCCESS.getCode());
		departmentResponse.setMessage(ResultEnum.SUCCESS.getMessage());
		departmentResponse.setDeparatmentList(departmentList);
		logger.debug("=========getDepartmentList response = [{}]", JSON.toJSONString(departmentResponse));
		return departmentResponse;
	} 
	
	
	
	@RequestMapping("/getSoftwarePackageInfo")
	public @ResponseBody SoftwarePackageResponse getSoftwarePackageInfo() {
		SoftwarePackage softwarePackage = softwarePackageService.getLatestSoftwarePackage();
		SoftwarePackageResponse softwarePackageResponse = new SoftwarePackageResponse();
		softwarePackageResponse.setApkMd5(softwarePackage.getDigest());
		softwarePackageResponse.setApkSize(softwarePackage.getSize());
		softwarePackageResponse.setDownloadUrl(String.format("%s/%s", address,softwarePackage.getId()));
		softwarePackageResponse.setModifyContent(softwarePackage.getContent());
		softwarePackageResponse.setUpdateStatus(Integer.parseInt(softwarePackage.getUpdateStatus()));
		softwarePackageResponse.setVersionCode(Integer.parseInt(softwarePackage.getVersion()));
		softwarePackageResponse.setVersionName(softwarePackage.getName());
		softwarePackageResponse.setCode(Integer.parseInt(ResultEnum.SUCCESS.getCode()));
		softwarePackageResponse.setMessage(ResultEnum.SUCCESS.getMessage());
		logger.debug("=========getSoftwarePackageInfo response = [{}]", JSON.toJSONString(softwarePackageResponse));
		return softwarePackageResponse;
	} 
	
	@RequestMapping("/downloadSoftwarePackage/{id}")
	public void downSoftwarePackage(@PathVariable("id") String id, HttpServletResponse response, HttpServletRequest request) {
	        OutputStream os = null;
	    	SoftwarePackage softwarePackage  = softwarePackageService.findUniqueById(id);
	        try {           
	        	String storePath = softwarePackage.getStorePath();
//	            byte[] softwarePackageBytes = softwarePackage.getSoftwarePackage();
	        	 byte[] softwarePackageBytes = FileUtils.readFileToByteArray(new File(storePath));
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeFileName(PDFSealConstants.CODING_GBK, softwarePackage.getName()+"_"+softwarePackage.getVersion()) + ".apk"  );
	            response.setContentType("application/octet-stream; charset=UTF-8" ); // 图片类型
	            os = response.getOutputStream();
	            os.write(softwarePackageBytes);
	        } catch (Exception ex) {
	            logger.error("DownLoad info error: ", ex);            
	        } finally {
	            IOUtils.closeQuietly(os);
	        }    
	} 
	
	/**
	 * <p>编码文件名</p>
	 * @Description:
	 * @param fileName
	 * @return
	* @throws UnsupportedEncodingException 
	*/
	protected String encodeFileName(String charset, String fileName) throws UnsupportedEncodingException {
		return new String(fileName.getBytes(charset), PDFSealConstants.CODING_ISO8859);
	}
	
	
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			String localIp = "127.0.0.1";
			String localIpv6 = "0:0:0:0:0:0:0:1";
			if (ipAddress.equals(localIp) || ipAddress.equals(localIpv6)) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		String ipSeparate = ",";
		int ipLength = 15;
		if (ipAddress != null && ipAddress.length() > ipLength) {
			if (ipAddress.indexOf(ipSeparate) > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(ipSeparate));
			}
		}
		return ipAddress;
	}
	
	@ExceptionHandler(Exception.class)
	public  ModelAndView exceptionHandler(Exception exception,HttpServletRequest request ,HttpServletResponse response) {
		Map<String,String> resutlMap = new HashMap<String,String>(); 
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonValue = null;
		
		try {
			if(exception instanceof BusinessException) {
			     BusinessException businessException= (BusinessException)exception;
			     logger.error("businessException, errocode : {} ,errorMessage: {} " ,businessException.getCode(),businessException.getMessage());
				 resutlMap.put("code", businessException.getCode());
				 resutlMap.put("message", businessException.getMessage());
				 jsonValue = mapper.writeValueAsString(resutlMap);
			}else if(exception  instanceof Exception ) {
				 logger.error("request uri {}",request.getRequestURI());
				 String requestString = getRequestJsonString(request);
				 logger.error("request content {}",requestString);
				 logger.error("exception: " ,exception);
				 resutlMap.put("code", ResultEnum.UNKOWN_ERROR.getCode());
				 resutlMap.put("message", ResultEnum.UNKOWN_ERROR.getMessage());
				 jsonValue = mapper.writeValueAsString(resutlMap);
			}
			response.getWriter().write(jsonValue);
			
		} catch (IOException ioException) {
			logger.error("ioexception: " ,ioException);
		}
		return new ModelAndView();
	}
	

	
	 public static String getRequestJsonString(HttpServletRequest request)
	            throws IOException {
	        String submitMehtod = request.getMethod();
	        // GET
	        if (submitMehtod.equals("GET")) {
	            return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
	        // POST
	        } else {
	            return getRequestPostStr(request);
	        }
	    }

	
	/**      
    * 描述:获取 post 请求的 byte[] 数组
    * <pre>
    * 举例：
    * </pre>
    * @param request
    * @return
    * @throws IOException      
    */
   public static byte[] getRequestPostBytes(HttpServletRequest request)
           throws IOException {
       int contentLength = request.getContentLength();
       if(contentLength<0){
           return null;
       }
       byte buffer[] = new byte[contentLength];
       for (int i = 0; i < contentLength;) {

           int readlen = request.getInputStream().read(buffer, i,
                   contentLength - i);
           if (readlen == -1) {
               break;
           }
           i += readlen;
       }
       return buffer;
   }
	
   public static String getRequestPostStr(HttpServletRequest request)
           throws IOException {
       byte buffer[] = getRequestPostBytes(request);
       String charEncoding = request.getCharacterEncoding();
       if (charEncoding == null) {
           charEncoding = "UTF-8";
       }
       return new String(buffer, charEncoding);
   }

}
