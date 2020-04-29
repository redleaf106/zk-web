package cn.org.bjca.zk.platform.web.controller;

import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.platform.service.DepartmentService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.utils.MD5Util;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OAController
 * @Author redleaf
 * @Date 2020/4/2 16:06
 **/
@Controller
@RequestMapping("/OA")
public class OAController {

    String oaUrl = "https://crm.gefund.com.cn/gefundCRM/holiday/query";

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/getOAInfo", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getOAInfo(HttpServletRequest request) throws IOException {
        String requestUrl = "https://crm.gefund.com.cn/gefundCRM/holiday/query";
        String objno = request.getParameter("objno");
        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        String secret = objno+startdate+enddate+"gef";
        secret = MD5Util.MD5Encode(secret,"utf-8");
        JSONObject json = new JSONObject();
        json.put("startdate",startdate);
        json.put("enddate",enddate);
        json.put("objno",objno);
        json.put("secret",secret);
        System.out.println(json.toJSONString());
        String apiUrl = oaUrl;
        System.out.println(apiUrl);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiUrl);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(json.toJSONString(), charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        response = httpclient.execute(httpPost);
        StatusLine status = response.getStatusLine();
        int state = status.getStatusCode();
        if (state == HttpStatus.SC_OK) {
            HttpEntity responseEntity = response.getEntity();
            String jsonString = EntityUtils.toString(responseEntity);
            System.out.println(jsonString);
            JSONArray jsonArray = JSONObject.parseArray(jsonString);
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            System.out.println(jsonObject.getString("check_info"));
            return jsonString;
        }
        else{
            System.err.println("请求返回:"+state+"("+apiUrl+")");
        }
        return null;
    }

    //文件上传方法
    //上传员工信息
    @RequestMapping(value = "uploadfile", method = RequestMethod.POST)
    public void upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model, Model mod) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("upload");
        System.out.println("文件路径："+path);
        String originalFilename = file.getOriginalFilename();
        String type = file.getContentType();
        //originalFilename = UUID.randomUUID().toString()+originalFilename;
        System.out.println("目标文件名称："+originalFilename+",目标文件类型："+type);
        File targetFile = new File(path,originalFilename );
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }else if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 获得上传文件的文件扩展名
        String subname = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        System.out.println("文件的扩展名："+subname);

        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rootpath = path + File.separator + originalFilename;
        List<String[]> excellist = readExcel(rootpath);
        int len = excellist.size();
        System.out.println("集合的长度为："+len);
        int success = 0;
        int filed = 0;
        int sum = 0;
        for (int i = 1; i < len; i++) {
            String[] fields = excellist.get(i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//				String gh = fields[0];
//				System.out.println("工号----"+gh);
//
//				String name = fields[1];
//				System.out.println("姓名----"+name);
//
//				String xb = fields[2];
//				System.out.println("性别----"+xb);
//
//				String sfzh = fields[3];
//				System.out.println("身份证号----"+sfzh);
//
//				String bm_code = fields[4];
//				System.out.println("院系编号----"+bm_code);
//
//				String bm_name = fields[5];
//				System.out.println("院系名----"+bm_name);
//
//				Teaching_data td = new Teaching_data(gh, name, xb, sfzh, bm_code, bm_name);
//				int result = userService.insertTeachingData(td);

            //todo 金鹰信息录入
//            String gh = fields[0];
//            System.out.println("工号----"+gh);
//            String kh = fields[3];
//            String sfzh = fields[1];
//            System.out.println("姓名----"+sfzh);
//            String bumen = fields[2];
//            System.out.println("部门----"+bumen);
//            String pic = fields[4];
//            String departmentId = departmentService.findByDepartmentName(bumen).get(0).getId();

            //汇添富信息录入
            String gh = fields[0];
            String name = fields[1];
            String depar = fields[2];
            String phone = fields[3];
            String email = fields[4];
            String departmentId = departmentService.findByDepartmentName(depar).get(0).getId();
            Employee employee = new Employee();
            employee.setCheckPower(1);
//            employee.setIcCardNumber(kh);
//            employee.setDepartmentId(departmentId);
//            employee.setEmployeeName(sfzh);
//            employee.setEmployeeNumber(gh);
//            employee.setPicFile(pic.getBytes());
            employee.setEmployeeNumber(gh);
            employee.setIcCardNumber(gh);
            employee.setEmployeeName(name);
            employee.setDepartmentId(departmentId);
            employee.setMobilePhone(phone);
            employee.setEmail(email);
            employee.setPicFile("".getBytes());
            employeeService.saveOrUpdate(employee);
            //导入研究生
//				int result = userService.insertStudentDataYjs(new Student_data_yjs(xh, sfzh));
            //导入本科生
//            int result = userService.insertStudentDataBks(new Student_data_bks(xh,sfzh));
//            if(result>0){
//                success++;
//            }else{
//                filed++;
//            }
            sum++;

        }
        System.out.println("执行完毕，共"+sum+"成功"+success+"个，失败"+filed+"个。");

    }

    public List<String[]> readExcel(String path) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        List<String[]> list = null;
        try {
            //同时支持Excel 2003、2007
            File excelFile = new File(path); //创建文件对象
            FileInputStream is = new FileInputStream(excelFile); //文件流
            Workbook workbook = null; //这种方式 Excel 2003/2007/2010 都是可以处理的
            try {

                workbook = WorkbookFactory.create(is);
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
            //存储数据容器
            list = new ArrayList<String[]>();
            //遍历每个Sheet
            for (int s = 0; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
                //遍历每一行
                for (int r = 0; r < rowCount; r++) {
                    Row row = sheet.getRow(r);
                    int cellCount = row.getPhysicalNumberOfCells(); //获取总列数
                    //用来存储每行数据的容器
                    String[] model = new String[cellCount];
                    //遍历每一列
                    for (int c = 0; c < cellCount; c++) {
                        Cell cell = row.getCell(c);
                        int cellType = cell.getCellType();

                        //if(c == 0) continue;//第一列ID为标志列，不解析

                        String cellValue = null;
                        switch(cellType) {
                            case 1: //文本
                                cellValue = cell.getStringCellValue();
                                //model[c-1] = cellValue;
                                break;
                            case 0: //数字、日期
                                if(DateUtil.isCellDateFormatted(cell)) {
                                    cellValue = fmt.format(cell.getDateCellValue()); //日期型
                                    //model[c-1] = cellValue;
                                }
                                else {

                                    cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                                    //model[c-1] = cellValue;
                                }
                                break;
                            case 4: //布尔型
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case 3: //空白
                                cellValue = cell.getStringCellValue();
                                break;
                            case 5: //错误
                                cellValue = "错误";
                                break;
                            case 2: //公式
                                cellValue = "错误";
                                break;
                            default:
                                cellValue = "错误";

                        }
                        System.out.print(cellValue + "    ");

                        model[c] = cellValue;
                    }
                    //model放入list容器中
                    list.add(model);
                    System.out.println();
                }
            }
            is.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


}
