package cn.org.bjca.zk.platform.test;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import jxl.CellView;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JsonToExcel {
    public static void main(String[] args) throws Exception {
        HSSFWorkbook mWorkbook = new HSSFWorkbook();

        HSSFSheet mSheet = mWorkbook.createSheet("日报表");

        // 创建Excel标题行，第一行。
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue("部门");
        headRow.createCell(1).setCellValue("姓名");
        headRow.createCell(2).setCellValue("柜门号");
        headRow.createCell(3).setCellValue("存入时间");
        headRow.createCell(4).setCellValue("存入状态");
        headRow.createCell(5).setCellValue("取出时间");
        headRow.createCell(6).setCellValue("取出状态");
        headRow.createCell(7).setCellValue("紧急授权人");
        headRow.createCell(8).setCellValue("异常存取原因");
        headRow.createCell(9).setCellValue("备注");

        //单元格格式
        headRow.setHeightInPoints((short) 20);
        List<String> startTime = new ArrayList<>();
        startTime.add("12月05日 09时28分09秒");
        startTime.add("12月05日 12时58分23秒");
        List<String> pushStatus = new ArrayList<>();
        pushStatus.add("正常");
        pushStatus.add("正常");
        List<String> endTime = new ArrayList<>();
        endTime.add("12月05日 11时36分28秒");
        endTime.add("12月05日 15时06分28秒");
        List<String> pullStatus = new ArrayList<>();
        pullStatus.add("正常");
        pullStatus.add("正常");
        createCell( "指数量化部","吴振翔","1",startTime,pushStatus,endTime,pullStatus,"null","null","考勤：出差", mSheet);
        createCell( "指数量化部","吴振翔","1",startTime,pushStatus,endTime,pullStatus,"null","null","考勤：出差", mSheet);
        createCell( "指数量化部","吴振翔","1",startTime,pushStatus,endTime,pullStatus,"null","null","考勤：出差", mSheet);
        createCell( "指数量化部","吴振翔","1",startTime,pushStatus,endTime,pullStatus,"null","null","考勤：出差", mSheet);
        FileOutputStream output = new FileOutputStream("d://target.xls");
        mWorkbook.write(output);
        output.close();
    }

    // 创建Excel的一行数据。
    private static void createCell(String department, String name, String doorNumber, List<String> startTime, List<String> pushStatus, List<String> endTime,
                                   List<String> pullStatus, String authorizer, String reason, String remake, HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(department);
        dataRow.createCell(1).setCellValue(name);
        dataRow.createCell(2).setCellValue(doorNumber);
        String startT = "";
        for(String s:startTime){
            startT += s+"  ";
        }
        dataRow.createCell(3).setCellValue(startT);
        String pushS = "";
        for(String s:pushStatus){
            pushS += s+"  ";
        }
        dataRow.createCell(4).setCellValue(pushS);
        String endT = "";
        for(String s:endTime){
            endT += s+"  ";
        }
        dataRow.createCell(5).setCellValue(endT);
        String pullS = "";
        for(String s:pullStatus){
            pullS += s+"  ";
        }
        dataRow.createCell(6).setCellValue(pullS);
        dataRow.createCell(7).setCellValue(authorizer);
        dataRow.createCell(8).setCellValue(reason);
        dataRow.createCell(9).setCellValue(remake);
    }
}
