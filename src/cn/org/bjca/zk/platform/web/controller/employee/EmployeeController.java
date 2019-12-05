/**
 * 
 */
package cn.org.bjca.zk.platform.web.controller.employee;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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

import cn.org.bjca.zk.db.entity.Department;
import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.db.entity.User;
import cn.org.bjca.zk.platform.PDFSealConstants;
import cn.org.bjca.zk.platform.bean.AttachmentMessage;
import cn.org.bjca.zk.platform.bean.Message;
import cn.org.bjca.zk.platform.service.DepartmentService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.utils.EssPdfUtil;
import cn.org.bjca.zk.platform.web.controller.BaseController;
import cn.org.bjca.zk.platform.web.page.EmployeePage;

/***************************************************************************

 * @文件名称: EmployeeController.java
 * @包   路   径： cn.org.bjca.zk.platform.web.controller.employee
 * @版权所有：北京数字认证股份有限公司 (C) 2019
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： gaozhijiang
 * @创建时间：2019年11月27日
 ***************************************************************************/
@Controller
@RequestMapping("/employee/employee")
public class EmployeeController extends BaseController{
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DepartmentService departmentService;
	@Value("#{sysConfig['picMaxSize']}")
	private String picMaxSizeStr ;
	
	
	
	@RequestMapping(value ="")
	public String list( ModelMap modelMap, HttpServletRequest request) {
		EmployeePage<Employee> employeePage = new EmployeePage<Employee>();
        Page page = new Pagination();
        String pageNum = request.getParameter("pageNum");//当前页码
		if(StringUtils.isNotBlank(pageNum)) {
			page.setCurrentPage(Integer.parseInt(pageNum));
		}
        String numPerPage = request.getParameter("numPerPage");//每页显示条数
        if(StringUtils.isNotBlank(numPerPage)) {
			page.setPageSize(Integer.parseInt(numPerPage));
		}
		
		String employeeName = request.getParameter("employeeName");//用户名称
		if(StringUtils.isNotBlank(employeeName)) {
			employeePage.setEmployeeName("%"+employeeName.trim()+"%");
		}
		
		employeePage.setPageVO(page);
		employeePage = employeeService.findPage(employeePage);
		employeePage.setEmployeeName(employeeName);
		
		modelMap.put("employeePage", employeePage);
		return "/employee/employee/employeeList";
	}
	
	/**
     * <p>指向查找印章附件页面</p>
     * 
     * @Description:
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "toAttachmentLookUpPage", method = RequestMethod.GET)
    public String toAttachmentLookUpPage() throws Exception {
        return "/employee/employee/attachmentLookup";
    }
	
	
	/**
	  * <p>指向员工表单页面</p>
	  * @Description:
	  * @return
	 */
	@RequestMapping(value = "toEditFormPage/{employeeId}", method = RequestMethod.GET)
	public String toEditFormPage( @PathVariable("employeeId") String employeeId, ModelMap modelMap){	
		Employee employee = null;
		if(StringUtils.isNotBlank(employeeId)&& !BLANK_PARAM_VALUE.equals(employeeId)){
			employee = employeeService.findUniqueById(employeeId);
		}else{
			employee = new Employee();
			employee.setEmployeeNumber(EssPdfUtil.genrRandomUUID());
		}
		modelMap.put("employee", employee);
		
		List<Department> departmentList = departmentService.getAll(); // 获取部门列表
		modelMap.put("departmentList", departmentList); // 角色列表
		return "/employee/employee/employeeForm";
	}
	
	/**
	  * <p>保存或更新表单信息</p>
	  * @Description:
	  * @param user
	  * @return
	 */
	
	
	@RequestMapping(value = "saveOrUpdate")
	public ModelAndView saveOrUpdate( Employee employee,HttpServletRequest request) throws Exception {
		Message message = new Message();
				
		boolean hasErrors = false; // 成功失败标识 ： true表示有错误
		Employee employeeExist = null;
		try {
			// 印章附件路径
			String attachmentPath = request.getParameter("attachment.attachmentPath");
			attachmentPath = new String(attachmentPath.getBytes("ISO-8859-1"), "UTF-8");
			if (StringUtils.isNotBlank(employee.getId())) {
				message.setContent(UPDATE); // 内容提示
				employeeExist = employeeService.findUniqueById(employee.getId());
			} else {
				message.setContent(SAVE); // 内容提示
				List<Employee> checkEmployeeExist = employeeService
						.findEmployeesByCardNumber(employee.getIcCardNumber());
				if (checkEmployeeExist != null && !checkEmployeeExist.isEmpty()) {
					message.setContent("员工编号已存在，请重新输入!"); // 内容提示
					hasErrors = true;
				}
				if (!hasErrors) {
					if (attachmentPath.equals("")) {
						message.setContent("员工图片不能为空，请选择!"); // 内容提示
						hasErrors = true;
					}
				}
			}

			if (!hasErrors) {
				if (!"".equals(attachmentPath)) {
					String filePath = EssPdfUtil.getCurrPath() + "upload/" + attachmentPath;
					byte[] picBty = FileUtils.readFileToByteArray(new File(filePath));
					employee.setPicFile(picBty);
				} else {
					employee.setPicFile(employeeExist.getPicFile());
				}
				if (!hasErrors) {
					User loginUser = (User) request.getSession().getAttribute(PDFSealConstants.SESSION_USER);
					if (null != loginUser) {
						employee.setUserId(loginUser.getId());
					}
					employeeService.saveOrUpdate(employee);
					if (!"".equals(attachmentPath)) { // 删除临时附件
						FileUtils.deleteQuietly(new File(attachmentPath));
					}
				}
			}
		} catch (Exception ex) {
			logger.error("saveOrUpdate employee info error: ", ex);
			if (StringUtils.isNotBlank(employee.getId())) {
				message.setContent(UPDATE_ERROR); // 内容提示
			} else {
				message.setContent(SAVE_ERROR); // 内容提示
			}
			hasErrors = true;
		}
		if (!hasErrors) {
			message.setStatusCode(SUCCESS);
		} else {
			message.setStatusCode(FAIL);
		}
		
		message.setCallbackType("closeCurrent");
		message.setNavTabId("employee");
		return this.ajaxDone(message);
	}
	
	
	/**
	  * <p>根据id删除记录</p>
	  * @Description:
	  * @param id ,id主键
	  * @param model
	  * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody String delete(@PathVariable("id") String id) throws Exception{
		employeeService.delEmployeeById(id);
		Message message = new Message();
		message.setStatusCode(this.SUCCESS);
		message.setContent(this.DELETE);//内容提示
		return this.toJsonString(message);
	}
	
	 /**
     * <p>上传员工图片</p>
     * 
     * @Description:
     * @param mFile 员工图片文件
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "uploadPicAttachment")
    public void uploadSealAttachment(@RequestParam("file") CommonsMultipartFile mFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AttachmentMessage attachMessage = new AttachmentMessage();
        byte[] imgBty = mFile.getBytes();
        String fileName = getXssFileName(mFile);
        String imgType = getXssContentType(mFile);        
        if (imgBty.length > 0 && StringUtils.isNotBlank(fileName)) {
            String imgExt = filterImgType(imgType);
            Long picSize = mFile.getSize();
            Double picMaxSize = 1.00;
            try {
            	picMaxSize = Double.parseDouble(picMaxSizeStr);
            } catch (Exception e) {
                logger.error("******picMaxSize error,can't convert Double:" + picMaxSizeStr, e);
            }
            if (imgExt != null) {
                if (picSize <= picMaxSize * PDFSealConstants.SIZE_KB) {
                	String  picfileName= EssPdfUtil.genrRandomUUID()+".pic";
                    String filePath = EssPdfUtil.getCurrPath()+"upload/"+picfileName;
                    FileUtils.writeByteArrayToFile(new File(filePath), imgBty);
                    attachMessage.setAttachmentPath(new String(picfileName.getBytes(PDFSealConstants.CODING), "iso-8859-1") );
                } else {
                    attachMessage.setAttachmentPath("");
                }
                attachMessage.setId(imgExt); // 图片类型
                attachMessage.setFileName(fileName);
                attachMessage.setAttachmentSize(picSize.toString());
                // 下划线之前是以byte单位，在页面上进行比较的作用 下划线之后是从配置文件中读取的数值，在页面上准确提示的作用
                attachMessage.setAttachmentMaxSize((picMaxSize * PDFSealConstants.SIZE_KB) + "_" + picMaxSize);
            } else {
                logger.error("******server seal type error:" + imgType);
            }
            PrintWriter out = null;
            try {
                out = response.getWriter();
                String jsonStr = toJsonString(attachMessage);
                out.println(jsonStr);
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
    }
	
	
	/**
     * <p>显示员工图片</p>
     * 
     * @Description:
     * @param id 员工id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "showPic")
    public void showPic(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        OutputStream os = null;
        try {
            id = this.filterSafeStringXSS(id);
            byte[] picBty = null;
            if (id.length() == 32) { // 表示从数据库获取印章
                Employee employee = employeeService.findUniqueById(id);
                picBty = employee.getPicFile();
            } else { // 从目录获取印章
            	String filePath = EssPdfUtil.getCurrPath()+"upload/"+id;
                filePath = new String(filePath.getBytes("iso-8859-1"), PDFSealConstants.CODING);
                logger.debug("**********server img path:" + filePath);
                picBty = FileUtils.readFileToByteArray(new File(filePath));
            }
            os = response.getOutputStream();
            os.write(picBty);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }
	
}
