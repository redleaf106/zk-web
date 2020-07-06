package cn.org.bjca.zk.platform.test;

import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

//一般把我发送过去得话要写在按钮得监听里，把对方发送过来得话写到按钮监听里
public class L13_3_1 extends JFrame implements ActionListener{
    JTextArea jta=null;
    JButton jb=null;
    JTextField jtf=null;
    JPanel jp=null;
    JScrollPane jsp=null;
    PrintWriter pw=null;

    public static void main(String[] args) {
        L13_3_1 aa=new L13_3_1();
    }
    public L13_3_1() {
        jta=new JTextArea();
        jb=new JButton("发送");
        jb.addActionListener(this);
        jtf=new JTextField(20);//文本框
        jp=new JPanel(); jp.add(jtf); jp.add(jb);
        jsp=new JScrollPane(jta);
        this.add(jsp); this.add(jp,"South");

        this.setTitle("服务器端");
        this.setSize(350,250);
        this.setLocation(300, 200);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        try {
            ServerSocket ss=new ServerSocket(9998);

            Socket s=ss.accept();//监听的意思
            //只要没有客户端链接到服务器上来，它就一直监听，后面的22就不可能输出
            InputStreamReader isr=new InputStreamReader(s.getInputStream());
            //上一行是读取服务器里面发送过来的内容，s就是接受127.0.0.1这个网址和9999这个端口发送过来的内容
            BufferedReader br=new BufferedReader(isr);

            pw=new PrintWriter(s.getOutputStream(),true);//发送过去，我是服务器
            while(true) {
                String xinxi=br.readLine();
                jta.append("客户端对我说："+xinxi+"\r\n");
            }
        }catch(Exception e) {}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb) {
            String xinxi=jtf.getText();//因为说话的内容在文本框中，所以把文本框的内容得取出来
            Map map = new HashMap();
            map.put("ip", "192.168.3.140");
            map.put("port", "9998");
            map.put("cabinetDoorNumber", "001");
            JSONObject jsonObject = new JSONObject();
            jta.append(jsonObject.toJSONString(map));
            //jta.append("我对客户端说："+xinxi+"\r\n");//append是添加得意思，就是说了一句，上一句还保存在页面上
            //pw.println(xinxi);
            pw.println(jsonObject.toJSONString(map));


            jtf.setText("");//每次说完话之后让文本框清空
        }
        // TODO Auto-generated method stub
    }
}
