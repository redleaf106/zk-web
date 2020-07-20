package cn.org.bjca.zk.platform.tools;

import cn.org.bjca.zk.db.entity.CheckInfo;
import cn.org.bjca.zk.db.entity.CheckWMInfo;
import cn.org.bjca.zk.db.entity.HTFCheck;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CreateDayCheckUtils
 * @Author redleaf
 * @Date 2020/1/9 15:32
 **/
@Component
public class CreateDayCheckUtils {

    //个人PC路径
    //private final static String PATH = "d:\\WorkSpace/zk-web/out/artifacts/zk_web_war_exploded/checkList/";
   // private final static String PATH = "E:\\study/";
    //汇添富服务器路径
    //private final static String PATH = "/root/apache-tomcat-8.5.50/webapps/htf/checkList/";
    //万道pc服务器
    //private final static String PATH = "usr/share/apache-tomcat-9.0.29/webapps/jyjj/checkList";
    //金鹰服务器路径
    private final static String PATH = "/usr/local/tomcat/apache-tomcat-8.5.50/webapps/jyjj/checkList/";
    //嘉实服务器路径
    //private final static String PATH = "/home/admin/apache-tomcat-9.0.29/webapps/jsfund/checkList";



    public static HTFCheck checkDayCheck(List<CheckInfo> list, Date date){


        HSSFWorkbook mWorkbook = new HSSFWorkbook();

        HSSFSheet mSheet = mWorkbook.createSheet("日报表");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = sdf.format(date)+"-日报表";


        //背景色样式
        HSSFCellStyle hssfCellStyleBack =  mWorkbook.createCellStyle();
        hssfCellStyleBack.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        hssfCellStyleBack.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //背景色+居中样式
        HSSFCellStyle hssfCellStyleBackAndCenter =  mWorkbook.createCellStyle();
        hssfCellStyleBackAndCenter.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        hssfCellStyleBackAndCenter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        hssfCellStyleBackAndCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 创建Excel标题行，第一行。
        HSSFRow headRow = mSheet.createRow(0);
        HSSFCell cell0 = headRow.createCell(0);
        cell0.setCellValue("工号");
        cell0.setCellStyle(hssfCellStyleBackAndCenter);
        HSSFCell cell1 = headRow.createCell(1);
        cell1.setCellValue("部门");
        cell1.setCellStyle(hssfCellStyleBackAndCenter);
        HSSFCell cell2 = headRow.createCell(2);
        cell2.setCellValue("姓名");
        cell2.setCellStyle(hssfCellStyleBackAndCenter);
        HSSFCell cell3 = headRow.createCell(3);
        //居中样式
        HSSFCellStyle hssfCellStyle =  mWorkbook.createCellStyle();
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //字体设置
        HSSFFont font = mWorkbook.createFont();
        font.setFontName("华文细黑");
        font.setFontHeightInPoints((short) 10);//设置字体大小
        hssfCellStyle.setFont(font);
        //设置列样式居和字体
        mSheet.setDefaultColumnStyle(3,hssfCellStyle);
        mSheet.setDefaultColumnStyle(4,hssfCellStyle);
        mSheet.setDefaultColumnStyle(0,hssfCellStyle);
        mSheet.setDefaultColumnStyle(1,hssfCellStyle);
        mSheet.setDefaultColumnStyle(2,hssfCellStyle);
        mSheet.setDefaultColumnStyle(5,hssfCellStyle);
        mSheet.setColumnWidth(1,5000);
        cell3.setCellStyle(hssfCellStyleBackAndCenter);
        cell3.setCellValue("存入情况");
//        headRow.createCell(4).setCellValue("存入状态");
        HSSFCell cell4 = headRow.createCell(4);
        cell4.setCellStyle(hssfCellStyleBackAndCenter);
        cell4.setCellValue("取出情况");

//        headRow.createCell(6).setCellValue("取出状态");
//        headRow.createCell(7).setCellValue("异常存取原因");
        HSSFCell cell5 = headRow.createCell(5);
        cell5.setCellStyle(hssfCellStyleBackAndCenter);
        cell5.setCellValue("OA考勤");

        //单元格格式
        headRow.setHeightInPoints((short) 20);

        createCell(list,mSheet,mWorkbook);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(PATH+fileName+".xls");
            System.out.println(PATH+fileName);
            mWorkbook.write(output);
            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HTFCheck htfCheck = new HTFCheck();
        htfCheck.setFileName(fileName+".xls");
        htfCheck.setFilePath(PATH);
        return htfCheck;

    }

    // 创建Excel的一行数据。
    private static void createCell(List<CheckInfo> list, HSSFSheet sheet, HSSFWorkbook mWorkbook) {
        for(CheckInfo checkInfo:list){
            int index = 0;
            HSSFCellStyle hssfCellStyleBack =  mWorkbook.createCellStyle();
            if(index%2!=0){
                hssfCellStyleBack.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
                hssfCellStyleBack.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            }else{
                hssfCellStyleBack.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
                hssfCellStyleBack.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            }
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            System.out.println("执行写入"+checkInfo.getEmployeeName());
            HSSFCell cell0 = dataRow.createCell(0);
            cell0.setCellValue(checkInfo.getIcCardNumber());
            HSSFCell cell1 = dataRow.createCell(1);
            cell1.setCellValue(checkInfo.getDepartmentName());
            HSSFCell cell2 = dataRow.createCell(2);
            cell2.setCellValue(checkInfo.getEmployeeName());
            HSSFCell cell3 = dataRow.createCell(3);
            cell3.setCellValue(checkInfo.getPushTime());
//            dataRow.createCell(4).setCellValue(checkInfo.getPushStatus());
            HSSFCell cell4 = dataRow.createCell(4);
            //cell4.setCellStyle(hssfCellStyle);
            cell4.setCellValue(checkInfo.getPullTime());
//            dataRow.createCell(6).setCellValue(checkInfo.getPullStatus());
            //dataRow.createCell(7).setCellValue(checkInfo.getRemark());
            HSSFCell cell5 = dataRow.createCell(5);
            cell5.setCellValue(checkInfo.getOAInfo());

            //自适应列宽
            //sheet.autoSizeColumn(1, true);
            sheet.autoSizeColumn(3, true);
            sheet.autoSizeColumn(4, true);
            index++;
        }


    }

    public static HTFCheck checkWMCheck(List<CheckWMInfo> list, String date,String reportType) {
        HSSFWorkbook mWorkbook = new HSSFWorkbook();

        String sheetName = "";
        String fileName = "";
        if(reportType.equals("2")){
            sheetName="周报表";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;
            try {
                d = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(d);
            Integer weekNumbe = calendar.get(Calendar.WEEK_OF_YEAR);
            Integer year = calendar.get(Calendar.YEAR);
            fileName = year+"-"+weekNumbe+"-周报表";
        }
        if(reportType.equals("3")){
            sheetName="月报表";
            System.out.println(date);
            String dstr = date.substring(0,date.lastIndexOf("-"));
            fileName = dstr+"-月报表";
        }
        HSSFSheet mSheet = mWorkbook.createSheet(sheetName);

        // 创建Excel标题行，第一行。
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue("工号");
        headRow.createCell(1).setCellValue("柜门号");
        headRow.createCell(2).setCellValue("部门");
        headRow.createCell(3).setCellValue("姓名");
        headRow.createCell(4).setCellValue("正常存入次数");
        headRow.createCell(5).setCellValue("超时存入次数");
        headRow.createCell(6).setCellValue("超时存入日期");
        //headRow.createCell(7).setCellValue("未存次数");
        //headRow.createCell(8).setCellValue("未存日期");
        headRow.createCell(7).setCellValue("正常取出次数");
        headRow.createCell(8).setCellValue("应急取出次数");
        headRow.createCell(9).setCellValue("应急取出日期");
        headRow.createCell(10).setCellValue("多次存取次数");
        headRow.createCell(11).setCellValue("多次存取日期");
        if (reportType.equals(3)){
            headRow.createCell(12).setCellValue("本月汇总");
        }

        //单元格格式
        headRow.setHeightInPoints((short) 20);
        createWMCell(list,mSheet,reportType);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(PATH+fileName+".xls");
            System.out.println(PATH+fileName);
            mWorkbook.write(output);
            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HTFCheck htfCheck = new HTFCheck();
        htfCheck.setFileName(fileName+".xls");
        htfCheck.setFilePath(PATH);
        htfCheck.setReportType(reportType);
        return htfCheck;
    }

    private static void createWMCell(List<CheckWMInfo> list, HSSFSheet sheet, String reportType) {
        for(CheckWMInfo wmInfo:list){
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            System.out.println("执行写入");
            dataRow.createCell(0).setCellValue(wmInfo.getIcCardNumber());
            dataRow.createCell(1).setCellValue(wmInfo.getCabinetDoorNumber());
            dataRow.createCell(2).setCellValue(wmInfo.getDepartmentName());
            dataRow.createCell(3).setCellValue(wmInfo.getEmployeeName());
            dataRow.createCell(4).setCellValue(wmInfo.getZccTotal());
            dataRow.createCell(5).setCellValue(wmInfo.getCsTotal());
            dataRow.createCell(6).setCellValue(wmInfo.getCsDate());
            //dataRow.createCell(7).setCellValue(wmInfo.getWcTotal());
            //dataRow.createCell(8).setCellValue(wmInfo.getWcDate());
            dataRow.createCell(7).setCellValue(wmInfo.getZcqTotal());
            dataRow.createCell(8).setCellValue(wmInfo.getYjTotal());
            dataRow.createCell(9).setCellValue(wmInfo.getYjDate());
            dataRow.createCell(10).setCellValue(wmInfo.getDcTotal());
            dataRow.createCell(11).setCellValue(wmInfo.getDcDate());
            if (reportType.equals(3)){
                dataRow.createCell(12).setCellValue(wmInfo.getMonthTotal());
            }
        }
    }
}
