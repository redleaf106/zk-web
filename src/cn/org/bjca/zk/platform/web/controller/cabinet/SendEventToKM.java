package cn.org.bjca.zk.platform.web.controller.cabinet;

import cn.org.bjca.zk.db.entity.CabinetDoorEvent;
import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.db.entity.UrgentEvent;
import cn.org.bjca.zk.hikvision.ClientDemo.ConvertVideo;
import cn.org.bjca.zk.hikvision.ClientDemo.GrabHIKPicAndVideoUtils;
import cn.org.bjca.zk.platform.service.MonitorService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * 给km接口发送事件
 * @ClassName SendEventToKM
 * @Author redleaf
 * @Date 2020/6/3 13:08
 **/
@Service
public class SendEventToKM {

    private static final String KMEvent = "http://10.0.185.119:9500/BoxEventCtrl/receiveBoxEventCtrl";

    @Autowired
    private MonitorService monitorService;

    //录像机ip
    String lxjIp = "10.11.28.128";
    String shlxjIp = "10.17.36.90";
    //String lxjIp = "172.16.1.108";
    //摄像头ip
    String sxtIp = "10.11.28.55";

    @Async
    public void sendEventMessageToKM(CabinetDoorEvent cabinetDoorEvent,String employeeNumber) throws Exception{
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("actionCardNumber", "");
        jsonObject.put("cabinetDoorNumber", cabinetDoorEvent.getCabinetDoorNumber());
        jsonObject.put("cabinetIp", cabinetDoorEvent.getCabinetIp());
        jsonObject.put("cabinetNumber", cabinetDoorEvent.getCabinetNumber());
        jsonObject.put("doorOptTime", cabinetDoorEvent.getDoorOptTime().getTime());
        jsonObject.put("employeeCardNumber", employeeNumber);
        jsonObject.put("id", "");
        jsonObject.put("optTime", "");
        jsonObject.put("remark", cabinetDoorEvent.getRemark());
        jsonObject.put("status", cabinetDoorEvent.getStatus());
        jsonArray.add(jsonObject);
        String jsonStr = jsonArray.toJSONString();
        JSONObject params = new JSONObject();
        params.put("jsonStr",jsonStr);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        jsonStr = URLEncoder. encode(jsonArray.toJSONString(),"utf-8");
        HttpPost httpPost = new HttpPost(KMEvent+"?jsonStr="+jsonStr);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        //StringEntity entity = new StringEntity(jsonObject.toJSONString(), charSet);
        System.out.println(params.toJSONString());
        //  httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = null;
                try {
                    jsonString = EntityUtils.toString(responseEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(jsonString);
                JSONObject jsonResult = JSONObject.parseObject(jsonString);
                System.out.println("返回结果：" + jsonResult.getString("statusCode"));
            }
            else{
                System.err.println("请求返回:"+state+"("+KMEvent+")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        System.out.println(jsonArray.toJSONString());
        System.out.println(jsonArray.size());
    }

    @Async
    public void createVideos(String cabinetNumber,String cabinetDoorEventId, String startTime){
        //录像机相关参数
        String m_sDeviceIP = lxjIp;//录像机ip
        //String m_sDeviceIP = "10.11.28.28";//录像机ip
        int iPort = 8000;//录像机端口
        String userName = "admin";//录像机用户名
        String pwd = "1234abcde";//录像机密码
//        String pwd = "wd12345678";
        int m_iChanShowNum = 0; // 摄像头通道39是16层,40是12层，36是17层，34是18层，33是20层
        if(cabinetNumber.equals("120")){
            m_iChanShowNum = 40;
        }else if(cabinetNumber.equals("200")){
            m_iChanShowNum = 33;
        }else if(cabinetNumber.equals("161")||cabinetNumber.equals("162")){
            m_iChanShowNum = 39;
        }else if(cabinetNumber.equals("171")||cabinetNumber.equals("172")){
            m_iChanShowNum = 36;
        }else if(cabinetNumber.equals("181")||cabinetNumber.equals("182")){
            m_iChanShowNum = 34;
        }else if(cabinetNumber.equals("270")){
            m_iChanShowNum = 33;
            m_sDeviceIP = shlxjIp;
            pwd = "1234abcd";
        }
        System.out.println(startTime);
        String videoResult = GrabHIKPicAndVideoUtils.createVideo(m_sDeviceIP,iPort,userName,pwd,m_iChanShowNum,startTime,cabinetDoorEventId);
        System.out.println("录像路径为："+videoResult);
        // createPic(startTime);
    }

    @Async
    public void createSHANGHAIVideos(String cabinetNumber,String cabinetDoorEventId, String startTime){
        //录像机相关参数
        String m_sDeviceIP = shlxjIp;//录像机ip
        int iPort = 8000;//录像机端口
        String userName = "admin";//录像机用户名
        String pwd = "1234abcd";//录像机密码
        int m_iChanShowNum = 33; // 摄像头通道39是16层,40是12层，36是17层，34是18层，33是20层
        if(cabinetNumber.equals("270")){
            m_iChanShowNum = 33;
            m_sDeviceIP = shlxjIp;
        }else{
            System.out.println("不是上海");
            return;
        }
        System.out.println(startTime);
        String videoResult = GrabHIKPicAndVideoUtils.createVideo(m_sDeviceIP,iPort,userName,pwd,m_iChanShowNum,startTime,cabinetDoorEventId);
        System.out.println("录像路径为："+videoResult);
        // createPic(startTime);
    }


    public void clearlUserID(){
        String m_sDeviceIP = lxjIp;//录像机ip
        //String m_sDeviceIP = "10.11.28.28";//录像机ip
        int iPort = 8000;//录像机端口
        String userName = "admin";//录像机用户名
        String pwd = "1234abcde";//录像机密码
        new GrabHIKPicAndVideoUtils().lUserIDLogOut(m_sDeviceIP,iPort,userName,pwd);
    }

    public void conVideo(String oldpath,String newpath){
        ConvertVideo.process(oldpath,newpath);
    }

    @Async
    public void createPic(CabinetDoorEvent cabinetEvent,String startTime){

        String cabinetNumber = cabinetEvent.getCabinetNumber();
        String m_sDeviceIP = "";
        if(cabinetNumber.equals("161")||cabinetNumber.equals("162")){
            m_sDeviceIP = "10.11.28.55";
        }else if(cabinetNumber.equals("171")||cabinetNumber.equals("172")){
            m_sDeviceIP = "10.11.28.53";
        }else if(cabinetNumber.equals("181")||cabinetNumber.equals("182")){
            m_sDeviceIP = "10.11.28.51";
        }else if(cabinetNumber.equals("120")){
            m_sDeviceIP = "10.11.28.57";
        }else if(cabinetNumber.equals("200")){
            m_sDeviceIP = "10.11.28.50";
        }else if(cabinetNumber.equals("270")){
            m_sDeviceIP = "10.17.36.91";
        }
        //录像机相关参数
//        String m_sDeviceIP = sxtIp;//录像机ip
        //String m_sDeviceIP = "10.11.28.28";//录像机ip
        if(m_sDeviceIP.length()>0){
            int iPort = 8000;//录像机端口
            String userName = "admin";//录像机用户名
            String pwd = "1234abcd";//录像机密码
            int m_iChanShowNum = 1; // 摄像头通道39是16层
            System.out.println(startTime);
            String picResult = GrabHIKPicAndVideoUtils.createPic(m_sDeviceIP,iPort,userName,pwd,m_iChanShowNum,startTime);
            monitorService.addPic(cabinetEvent.getId(),picResult);
            System.out.println("截图路径为："+picResult);
        }else{
            System.out.println("没有配置摄像头ip");
        }

    }


    //金鹰晚存紧急处理
    @Async
    public UrgentEvent createUrgent(CabinetDoorEvent cabinetDoorEvent,Employee employee){
        UrgentEvent urgentEvent = new UrgentEvent();
        if(Integer.parseInt(cabinetDoorEvent.getStatus())==4){
            urgentEvent.setStatus(0);//紧急事件
        }else if(Integer.parseInt(cabinetDoorEvent.getStatus())==2){
            urgentEvent.setStatus(1);//晚存事件
        }
        urgentEvent.setEmployeeCardNumber(employee.getEmployeeNumber());
        urgentEvent.setRemark(cabinetDoorEvent.getRemark());
        urgentEvent.setEmployeeName(employee.getEmployeeName());
        return urgentEvent;
    }

    //用于把params里的参数给取出来
    public static String urlencode(Map<String,Object>data) {
        //将map里的参数变成像 showapi_appid=###&showapi_sign=###&的样子
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
