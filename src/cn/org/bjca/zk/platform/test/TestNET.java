package cn.org.bjca.zk.platform.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName TestNET
 * @Author redleaf
 * @Date 2020/1/20 14:30
 **/
public class TestNET {
    public static void main(String[] args) {
        postMethod();
    }
    public static void postMethod() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ByteArrayOutputStream bos;
                try {
                    String name = "tom";
                    String password = "123";
                    //    Log.d("MainActivity_log",Environment.getExternalStorageDirectory()+"");
                    File data=new File( "C:\\Users\\10615\\Desktop\\员工信息接口.txt");
                    //   File data=new File("app/src/main/assets/22.xls");
                    //      File data=getAssets().open("22.xls");
                    //            File data=new File(getAssets().open("22.xls"));

                    //   String data = "name="+name + "&password=" + password;
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://127.0.0.1:8080/zk_web_war_exploded/cabinet/cabinet/uploadDayCheck?cabinetIP=192.168.3.140").openConnection();
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    //          conn.setRequestProperty("Content-Type", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", data.length()+"");
                    conn.setConnectTimeout(5000);
                    conn.setRequestProperty("cabinetIP","192.168.3.140");
                    OutputStream out = conn.getOutputStream();
                    out.write(readFile(data));
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        bos = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        while((len = inputStream.read(buffer ))!=-1){
                            bos.write(buffer, 0, len);
                        }
                        bos.flush();
                        inputStream.close();
                        bos.close();

                    }
                } catch (Exception e) {
                    //         Log.d("MainActivity_log",e.toString());
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static byte[] readFile(File file) {
        // 需要读取的文件，参数是文件的路径名加文件名
        if (file.isFile()) {
            // 以字节流方法读取文件

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                // 设置一个，每次 装载信息的容器
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                // 开始读取数据
                int len = 0;// 每次读取到的数据的长度
                while ((len = fis.read(buffer)) != -1) {// len值为-1时，表示没有数据了
                    // append方法往sb对象里面添加数据
                    outputStream.write(buffer, 0, len);
                }
                // 输出字符串
                return outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件不存在！");
        }
        return null;
    }

}
