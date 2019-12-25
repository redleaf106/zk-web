package cn.org.bjca.zk.platform.test;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.awt.event.*;

public class L13_3_2 extends JFrame implements ActionListener {
    JTextArea jta=null;
    JButton jb=null;
    JTextField jtf=null;
    JPanel jp=null;
    JScrollPane jsp=null;
    PrintWriter pw=null;

    public static void main(String[] args) {
        L13_3_2 aa=new L13_3_2();
    }
    public L13_3_2() {
        jta=new JTextArea();
        jb=new JButton("发送");
        jb.addActionListener(this);
        jtf=new JTextField(20);//文本框
        jp=new JPanel(); jp.add(jtf); jp.add(jb);
        jsp=new JScrollPane(jta);
        this.add(jsp); this.add(jp,"South");

        this.setTitle("客户端");
        this.setSize(350,250);
        this.setLocation(500, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        try {
            Socket s=new Socket("127.0.0.1",9998);
            InputStreamReader isr=new InputStreamReader(s.getInputStream());
            //上一行是读取服务器里面发送过来的内容，s就是接受127.0.0.1这个网址和9999这个端口发送过来的内容
            BufferedReader br=new BufferedReader(isr);

            pw=new PrintWriter(s.getOutputStream(),true);//发送过去，我是服务器
            while(true) {
                String xinxi=br.readLine();
                jta.append("服务器对我说："+xinxi+"\r\n");
                System.out.println("服务器说："+xinxi+"\r\n");
            }
        }catch(Exception e) {}

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb) {
            String xinxi=jtf.getText();//因为说话的内容在文本框中，所以把文本框的内容得取出来
            jta.append("我对服务器说："+xinxi+"\r\n");//append是添加得意思，就是说了一句，上一句还保存在页面上
            pw.println(xinxi);
            jtf.setText("");//每次说完话之后让文本框清空
        }
        // TODO Auto-generated method stub
    }
}
