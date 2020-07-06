package cn.org.bjca.zk.platform.service;

import cn.org.bjca.zk.db.entity.Employee;
import cn.org.bjca.zk.platform.tools.HtfOainterface;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.xfire.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName MessageService
 * @Author redleaf
 * @Date 2020/1/8 16:18
 **/
@Component
public class MessageService {

    @Value("#{MessageConfig['MESSAGEURL']}")
    private String MESSAGEURL;
    @Value("#{MessageConfig['OAURL']}")
    private String OAURL;
    @Value("#{MessageConfig['OAMETHOD']}")
    private String OAMETHOD;

    private final String SECRET = "4028818230db6dbd0130fe847d6742ba";




    public String sendMessage(List<String> empPhoneList){
        String content = "快到工作时间啦，手机再好玩也不要忘记上交哦";
        Map<String, String> param = new HashMap<>();
        param.put("content",content);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            param.put("tel",empPhoneList.get(0));
            String resultStr = httpRequest(MESSAGEURL,param);
            JSONObject jsonSeccess = JSONObject.parseObject(resultStr);
            return jsonSeccess.toJSONString();













//            URIBuilder builder = new URIBuilder(MESSAGEURL);
//            for(String string:empPhoneList){
//                param.put("tel", string);
//                for (String key : param.keySet()) {
//                    builder.addParameter(key, param.get(key));
//                }
//            }
//            URI uri = builder.build();
//            // 创建http GET请求
//            HttpGet httpGet = new HttpGet(uri);
//            // 执行请求
//            response = httpclient.execute(httpGet);
//            String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
//            System.out.println(resultString);
//            result = resultString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String getHtfOAInfo(String startdate,String enddate,String objno){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("startdate",startdate);
        jsonObj.put("enddate",enddate);
        jsonObj.put("objno",objno);
        jsonObj.put("secret",SECRET);
        String finalR = "";
        try {
            Client client = new Client(new URL(OAURL));
            Object[] results = client.invoke(OAMETHOD, new Object[] { jsonObj.toJSONString() });
            for (Object o:results){
                System.out.println(o);
                finalR += o;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return finalR;
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

    //调用第三方接口的方法
    private static String httpRequest(String requestUrl,Map params) {
        //buffer用于接受返回的字符
        StringBuffer buffer = new StringBuffer();
        try {
            //建立URL，把请求地址给补全，其中urlencode（）方法用于把params里的参数给取出来
            URL url = new URL(requestUrl+"?"+urlencode(params));
            //打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            //获得输入
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //将bufferReader的值给放到buffer里
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            //关闭bufferReader和输入流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            //断开连接
            httpUrlConn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回字符串
        return buffer.toString();
    }

}
