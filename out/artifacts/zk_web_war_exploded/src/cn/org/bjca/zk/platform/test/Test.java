package cn.org.bjca.zk.platform.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {
    private static ServerSocket ss;
    private static final int PORT = 1234;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        System.out.println("server start...");
        try {
            ss = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void heartbeatForAndroid(String robotId){

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Socket s = ss.accept();
                    byte[] recData = null;
                    InputStream in = s.getInputStream();
                    OutputStream out = s.getOutputStream();
                    while(true) {
                        recData = new byte[BUFFER_SIZE];
                        int r = in.read(recData);
                        if(r>-1) {
                            String data = new String(recData);
                            if(data.trim().equals("over")) {
                                s.close();
                            }
                            out.write(("havaConnect:"+data).getBytes());
                            //RobotController.heartForAndroidToStart(data);
                        }else {
                            System.out.println("数据读取完毕！");
                            s.close();
                            break;
                        }
                    }

                } catch (IOException e) {
                    //RobotController.heartForAndroidToStop(robotId);
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
