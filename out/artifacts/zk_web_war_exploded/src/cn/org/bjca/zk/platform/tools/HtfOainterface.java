package cn.org.bjca.zk.platform.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.xfire.client.Client;

import java.net.URL;

/**
 * @ClassName HtfOainterface
 * @Author redleaf
 * @Date 2020/1/8 12:13
 **/
public class HtfOainterface {

    public static String getHtfOAInfo(String startdate,String enddate,String objno){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("startdate",startdate);
        jsonObj.put("enddate",enddate);
        jsonObj.put("objno",objno);
        jsonObj.put("secret","4028818230db6dbd0130fe847d6742ba");
        String finalR = "";
        try {
            Client client = new Client(new URL("http://10.50.115.190:8288/services/checkLeaveByEMP?wsdl"));
            Object[] results = client.invoke("GetInfoByEmp", new Object[] { jsonObj.toJSONString() });
            for (Object o:results){
                System.out.println(o);
                finalR += o;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        JSONArray resultArray = JSONArray.parseArray(finalR);
        if(resultArray.size()>0){
            JSONObject resultJson = (JSONObject) resultArray.get(0);
            JSONArray data = resultJson.getJSONArray("data");
            if(data.size()>0){
                JSONObject dataBean = (JSONObject)data.get(0);
                String atttype = dataBean.getString("atttype");
                return atttype;
            }else {
                return "";
            }
        }else {
            return "";
        }

    }

    public static void main(String[] args){
        //String result = "[{\"status\":\"success\",\"message\":\"ok\",\"data\":[{\"offtime\":\" \",\"objno\":\"1091\",\"tel\":\"13500000000\",\"empname\":\"胡馨\",\"attdate\":\"2020-01-07\",\"atttype\":\"trip\"}]}]";
        String result = "[{\"status\":\"success\",\"message\":\"ok\",\"data\":[]}]";
        System.out.println(result);

        JSONArray resultArray = JSONArray.parseArray(result);
        if(resultArray.size()>0){
            JSONObject resultJson = (JSONObject) resultArray.get(0);
            JSONArray data = resultJson.getJSONArray("data");
            if(data.size()>0){
                JSONObject dataBean = (JSONObject)data.get(0);
                String atttype = dataBean.getString("atttype");
                System.out.println(atttype);
            }else {
                System.out.println("");
            }
        }else{
            System.out.println("");
        }






    }
}
