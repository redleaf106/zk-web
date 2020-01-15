package cn.org.bjca.zk.platform.web.controller;

import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.db.entity.HTFCheck;
import cn.org.bjca.zk.platform.service.CabinetDoorEventService;
import cn.org.bjca.zk.platform.service.CheckListService;
import cn.org.bjca.zk.platform.service.EmployeeService;
import cn.org.bjca.zk.platform.service.MessageService;
import cn.org.bjca.zk.platform.tools.CreateDayCheckUtils;
import cn.org.bjca.zk.platform.tools.HtfOainterface;
import cn.org.bjca.zk.db.entity.CheckInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName AutoRunTask
 * @Author redleaf
 * @Date 2019/12/31 14:06
 **/
@Controller
@EnableScheduling
public class AutoRunTask {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CabinetDoorEventService cabinetDoorEventService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CheckListService checkListService;


    private final static String MESSAGEURL = "http://10.50.115.157:10090/messagesend";//短信接口地址
    private final static String OAURL = "http://10.50.115.190:8288/services/checkLeaveByEMP?wsdl";//OA接口地址


//    @Scheduled(cron = "0/5 * * * * ? ") // 间隔5秒执行
//    public void taskCycle() {
//        System.out.println("使用SpringMVC框架配置定时任务");
//    }

    //@Scheduled(cron = "0 25 9 * * ?")//上午九点25进行手机检查
    public void checkPhone(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject json = new JSONObject();
        Date date = new Date();
        String str = sdf.format(date);
        Date dangtian = null;
        try {
            dangtian = sdf.parse(str);
        } catch (ParseException e) {

        }
        List<Employee> empList = employeeService.getAll();
        List<Employee> empResultList = new ArrayList<>();
        String icNoArray = "";
        for(int i=0;i<empList.size();i++){
            icNoArray+=empList.get(i).getIcCardNumber()+",";
        }
        List<String> empPhone = new ArrayList<>();
        for(Employee employee:empList){
            List eventList = cabinetDoorEventService.findOneEMPByOneDay(employee.getIcCardNumber(),dangtian);
            if(eventList==null||eventList.size()==0){
                //TODO 再加一层判断，判断是否通过OA系统进行过出差请假报备
                String result = HtfOainterface.getHtfOAInfo(sdf.format(dangtian),sdf.format(dangtian),employee.getIcCardNumber());
                JSONArray resultArray = JSONArray.parseArray(result);
                JSONObject jsonObject = (JSONObject) resultArray.get(0);
                JSONArray data = jsonObject.getJSONArray("data");
                JSONObject dataBean = (JSONObject) data.get(0);
                String atttype = dataBean.getString("atttype");
                empResultList.add(employee);
            }
        }
        String content = "您的手机还未上交，请您尽快上交，谢谢。";
        String templateId = "1";
        Map<String, String> param = new HashMap<>();
        param.put("content",content);
        param.put("templateId",templateId);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(MESSAGEURL);
            for(Employee employee:empResultList){
                param.put("tel", employee.getMobilePhone());
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }

            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpclient.execute(httpGet);

            String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("检查手机是否正常存储");

    }

    @Scheduled(cron = "0 25 9 * * ?")//上午九点25进行手机提醒
    public void morningCheck(){
        sendMessage();
    }

    @Scheduled(cron = "0 55 12 * * ?")//下午12点55进行手机提醒
    public void afternoonCheck(){
        sendMessage();;
    }

    @Scheduled(cron = "0 0 16 * * ?")//下午16点生成日报表
    public void createDayClock(){
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

        HTFCheck htfCheck = CreateDayCheckUtils.checkDayCheck(responseList);
        checkListService.add(htfCheck);

    }

    public void sendMessage(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject json = new JSONObject();
        Date date = new Date();
        String str = sdf.format(date);
        String objno = "";//员工工号参数
        Date dangtian = null;
        try {
            dangtian = sdf.parse(str);
        } catch (ParseException e) {

        }
        List<Employee> empList = employeeService.getAll();
        List<String> empPhoneList = new ArrayList<>();//员工手机号的集合
        for(Employee employee:empList){
            empPhoneList.add(employee.getMobilePhone());
            objno+=employee.getIcCardNumber()+",";
        }
        //调用汇添富OA接口
        String result = messageService.getHtfOAInfo(sdf.format(dangtian),sdf.format(dangtian),objno);
        JSONArray resultArray = JSONArray.parseArray(result);
        if(resultArray.size()>0){
            JSONObject jsonObject = (JSONObject) resultArray.get(0);
            JSONArray data = jsonObject.getJSONArray("data");
            //遍历data获取提前报备的员工考勤信息
            for(int i=0;i<data.size();i++){
                JSONObject dataBean = (JSONObject) data.get(i);
                String tel = dataBean.getString("tel");
                empPhoneList.remove(tel);
            }
        }
        //遍历手机号发送短信
        messageService.sendMessage(empPhoneList);
    }

}

