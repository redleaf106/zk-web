package cn.org.bjca.zk.platform.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

    private final static SocketServer INSTANCE = new SocketServer();
    private static ServerSocket ss;
    private List<Socket> socketList;
    private static final int PORT = 9998;

    public static void main(String[] args) {
        System.out.println("server start...");
//        SocketServer cabinetDoorServer = new SocketServer(ip,cabinetDoorNumber);
    }
    private SocketServer(){

    }

    public void init(){
        try {
            System.out.println("启动server");
            ss = new ServerSocket(PORT);
            socketList = new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        Socket s= null;//监听的意思
                        try {
                            s = ss.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int socketIndex = 0;
                        if(socketList.size()==0){
                            socketList.add(s);
                        }else{
                            for(Socket socket:socketList){
                                System.out.println(socket.getInetAddress());
                                if (s.getInetAddress().equals(socket.getInetAddress())){
                                    socketList.set(socketIndex,s);
                                    socketIndex++;
                                }else{
                                    socketList.add(s);
                                }
                            }
                        }
                        System.out.println(socketList.size()+"个设备正在接连接");
                    }
                }
            }

            ).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static SocketServer getInstance(){
        return INSTANCE;
    }

    public void sendDoorMessage(String code, String ip, String cabinetDoorNumber){
        System.out.println("open cabinetDoor");
        try {
            System.out.println("当前设备数："+socketList.size());
            for(int i=0;i<socketList.size();i++){
                if(ip.equals(quxiegang(socketList.get(i)))){
                    System.out.println("print start");
                    OutputStream out = socketList.get(i).getOutputStream();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("cabinetDoorNumber", cabinetDoorNumber);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(dataJson);
                    JSONObject jsonObject = responseJson(code,ip,jsonArray);
                    System.out.println(jsonObject.toJSONString());
                    out.write(jsonObject.toJSONString().getBytes());
                }else{
                    System.out.println("no client connecting");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除人员信息
    public void clearEmpToCabinet(String code, String ip, String employeeIcCardNumber){
        System.out.println("clearEmpToCabinet start");
        try {
            for (int i = 0; i < socketList.size(); i++) {
                System.out.println("print start");
                OutputStream out = socketList.get(i).getOutputStream();
                JSONObject dataJson = new JSONObject();
                dataJson.put("employeeIcCardNumber", employeeIcCardNumber);
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(dataJson);
                JSONObject jsonObject = responseJson(code,ip,jsonArray);
                jsonObject.put("DATA", jsonArray);
                System.out.println(jsonObject.toJSONString());
                out.write(jsonObject.toJSONString().getBytes());
            }
        }catch (Exception e){

        }
    }

    //解绑柜门
    public void removeCabinetOfEmp(String code, String ip, String employeeIcCardNumber){
        System.out.println("removeCabinetOfEmp start");
        try {
            System.out.println("当前设备数："+socketList.size());
            for(int i=0;i<socketList.size();i++){
                if(ip.equals(quxiegang(socketList.get(i)))) {
                    OutputStream out = socketList.get(i).getOutputStream();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("employeeIcCardNumber", employeeIcCardNumber);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(dataJson);
                    JSONObject jsonObject = responseJson(code, ip, jsonArray);
                    System.out.println(jsonObject.toJSONString());
                    out.write(jsonObject.toJSONString().getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    //修改部门时间
    public void updateDepartmentTime(String departmentName, List<String> startTime, List<String> endTime){
        System.out.println("updateDepartmentTime start");
        try {
            System.out.println("当前设备数："+socketList.size());
            for(int i=0;i<socketList.size();i++){
                OutputStream out = socketList.get(i).getOutputStream();
                JSONObject dataJson = new JSONObject();
                dataJson.put("departmentName", departmentName);
                dataJson.put("startTime",startTime);
                dataJson.put("endTime",endTime);
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(dataJson);
                JSONObject jsonObject = responseJson("5", "", jsonArray);
                System.out.println(jsonObject.toJSONString());
                out.write(jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    //修改时间阈值
    public void updateDepartmentTime(int timeIndex){
        System.out.println("updateDepartmentTime start");
        try {
            System.out.println("当前设备数："+socketList.size());
            for(int i=0;i<socketList.size();i++){
                OutputStream out = socketList.get(i).getOutputStream();
                JSONObject dataJson = new JSONObject();
                String status = "";
                if(timeIndex>0){
                    status = "1";
                }else {
                    status = "0";
                }
                dataJson.put("status", status);
                dataJson.put("minutes",Math.abs(timeIndex));
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(dataJson);
                JSONObject jsonObject = responseJson("4", "", jsonArray);
                System.out.println(jsonObject.toJSONString());
                out.write(jsonObject.toJSONString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public JSONObject responseJson(String code, String ip, JSONArray jsonArray){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CODE",code);
        jsonObject.put("IP", ip);
        jsonObject.put("DATA", jsonArray);
        return jsonObject;
    }
    //ip去斜杠
    public String quxiegang(Socket socket){
        return socket.getInetAddress().toString().split("/")[1];
    }




}
