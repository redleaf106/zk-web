package cn.org.bjca.zk.hikvision.ClientDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConvertVideo {

    private final static String PATH = "D:\\HIKVISION\\20200602200132.mp4";

    public static void main(String[] args) {
        if (!checkfile(PATH)) {
            System.out.println(PATH + " is not file");
            return;
        }
        if (process()) {
            System.out.println("ok");
        }
    }

    private static boolean process() {
        int type = checkContentType();
        boolean status = false;
        if (type == 0) {
            System.out.println("直接将文件转为flv文件");
            status = processFLV(PATH,"");// 直接将文件转为flv文件
        } else if (type == 1) {
            String avifilepath = processAVI(type);
            if (avifilepath == null)
                return false;// avi文件没有得到
            status = processFLV(avifilepath,"");// 将avi转为flv
        }
        return status;
    }

    public static boolean process(String oldpath,String newpath) {
        int type = checkContentType();
        boolean status = false;
        if (type == 0) {
            System.out.println("直接将文件转为flv文件");
            status = processFLV(oldpath,newpath);// 直接将文件转为flv文件
        } else if (type == 1) {
            String avifilepath = processAVI(type);
            if (avifilepath == null)
                return false;// avi文件没有得到
            status = processFLV(avifilepath,newpath);// 将avi转为flv
        }
        return status;
    }

    private static int checkContentType() {
        String type = PATH.substring(PATH.lastIndexOf(".") + 1, PATH.length())
                .toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }

    private static boolean checkfile(String path) {
        System.out.println("检查路径："+path);
        File file = new File(path);
        if (!file.isFile()) {
            return false;
        }
        return true;
    }

    // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
    private static String processAVI(int type) {
        List<String> commend = new ArrayList<String>();
        commend.add("D:\\Program Files (x86)\\ffmpeg\\ffmpeg-20191203-12bbfc4-win64-static\\bin");
        commend.add(PATH);
        commend.add("-oac");
        commend.add("lavc");
        commend.add("-lavcopts");
        commend.add("acodec=mp3:abitrate=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        commend.add("c:\\ffmpeg\\output\\a.avi");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            return "c:\\ffmpeg\\output\\a.avi";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
    private static boolean processFLV(String oldfilepath,String newfilepath) {

        if (!checkfile(oldfilepath)) {
            System.out.println(oldfilepath + " is not file");
            return false;
        }

        // 文件命名
        Calendar c = Calendar.getInstance();
        String savename = String.valueOf(c.getTimeInMillis()) + Math.round(Math.random() * 100000);
        List<String> commend = new ArrayList<String>();
        commend.add("D:\\Program Files (x86)\\ffmpeg\\ffmpeg-20191203-12bbfc4-win64-static\\bin");
        commend.add("-i");
        commend.add(oldfilepath);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-mbd");
        commend.add("0");
        commend.add("-c:a");
        commend.add("aac");
        commend.add("-strict");
        commend.add("-2");
        commend.add("-pix_fmt");
        commend.add("yuv420p");
        commend.add("-movflags");
        commend.add("faststart");
        commend.add("D:\\a26.mp4");
        System.out.println(commend.size());
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proce = null;
            String cmd = "";
            String cut = "ffmpeg -i "
                    + oldfilepath
                    + " -ab 56 -ar 22050 -qscale 8 -r 15 -s 800x500 "+ newfilepath;
            String cutCmd = cmd + cut;
            System.out.println(cut);
            proce = runtime.exec(cut);
//
//            ProcessBuilder builder = new ProcessBuilder(commend);
//            builder.command(commend);
//            builder.start();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean processFLV2(String oldfilepath) {

        Process process = null;
        String url = "zhangyifang";
        String[] cmd = {"cd D:\\Program Files (x86)\\ffmpeg\\ffmpeg-20191203-12bbfc4-win64-static\\bin\\ffmpeg.exe -i D:\\HIKVISION\\20200602200132.mp4 -ab 56 -ar 22050 -qscale 8 -r 15 -s 1600x1480 D:\\a26.mp4", url};  //写好shell命令
        try {
            process = Runtime.getRuntime().exec(cmd);  //用Runtime类实现
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            process.getInputStream()));
            String data = "";
            while ((data = reader.readLine()) != null) {
                System.out.println(data);
            }

            int exitValue = process.waitFor();

            if (exitValue != 0) {
                System.out.println("error");    //若出现异常，输出“error”
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}