package cn.org.bjca.zk.hikvision.ClientDemo;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName ClientDemo
 * @Author redleaf
 * @Date 2020/6/2 19:35
 **/
public class ClientDemo {

    public static String DLL_PATH;
    static {
        String path = (ClientDemo.class.getResource("/").getPath()).replaceAll("%20", " ").substring(1).replace("/", "\\");
        try {
            DLL_PATH = java.net.URLDecoder.decode(path, "utf-8");
            System.out.println(DLL_PATH);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
