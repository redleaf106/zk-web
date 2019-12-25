package cn.org.bjca.zk.platform.tools;

import cn.org.bjca.zk.platform.service.CabinetDoorService;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CabinetDoorServer {

    private final static CabinetDoorServer INSTANCE = new CabinetDoorServer();
    private static ServerSocket ss;
    private List<Socket> socketList;
    private static final int PORT = 9998;

    public static void main(String[] args) {
        System.out.println("server start...");
//        CabinetDoorServer cabinetDoorServer = new CabinetDoorServer(ip,cabinetDoorNumber);
    }
    private CabinetDoorServer(){
        try {
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
                        socketList.add(s);
                    }
                }
            }

            ).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static CabinetDoorServer getInstance(){
        return INSTANCE;
    }

    public void openDoor(String ip, String cabinetDoorNumber){
        System.out.println("open cabinetDoor");
        try {
            for(int i=0;i<socketList.size();i++){
                System.out.println("print start");
                //PrintWriter pw = new PrintWriter(ss.accept().getOutputStream(),true);
                OutputStream out = socketList.get(i).getOutputStream();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ip", ip);
                jsonObject.put("port", PORT);
                jsonObject.put("cabinetDoorNumber", cabinetDoorNumber);
                System.out.println("print ok");
                out.write(jsonObject.toJSONString().getBytes());
                System.out.println("door open success!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
