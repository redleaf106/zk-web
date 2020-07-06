package cn.org.bjca.zk.platform.tools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @ClassName MyCode
 * @Author redleaf
 * @Date 2020/6/2 11:48
 **/
public class MyCode implements ServletContextListener {
    //当Tomcat启动时会执行contextInitialized（）
    public   void   contextInitialized(ServletContextEvent e) {
        new SocketThread().run();
    }
    public   void   contextDestroyed(ServletContextEvent e) {

    }

    class SocketThread extends Thread{
        public void run() {
            SocketServer socketServer = SocketServer.getInstance();
            socketServer.init();
        }
    }

}
