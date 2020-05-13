package cn.org.bjca.zk.platform.tools;

import cn.org.bjca.zk.db.entity.CheckInfo;
import cn.org.bjca.zk.db.entity.HTFCheck;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    private final static String PATH = "d:\\WorkSpace/zk-web/out/artifacts/zk_web_war_exploded/checkList/";
    //汇添富服务器路径
    //private final static String PATH = "/root/apache-tomcat-8.5.50/webapps/htf/checkList/";
    //万道pc服务器
    //private final static String PATH = "usr/share/apache-tomcat-9.0.29/webapps/jyjj/checkList";
    //金鹰服务器路径
    //private final static String PATH = "/usr/local/tomcat/apache-tomcat-8.5.50/webapps/jyjj/checkList/";

    public static HTFCheck checkDayCheck(List<CheckInfo> list, Date date){


        HSSFWorkbook mWorkbook = new HSSFWorkbook();

        HSSFSheet mSheet = mWorkbook.createSheet("日报表");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = sdf.format(date)+"-日报表";

        // 创建Excel标题行，第一行。
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue("柜门号");
        headRow.createCell(1).setCellValue("部门");
        headRow.createCell(2).setCellValue("姓名");
        headRow.createCell(3).setCellValue("存入时间");
        headRow.createCell(4).setCellValue("存入状态");
        headRow.createCell(5).setCellValue("取出时间");
        headRow.createCell(6).setCellValue("取出状态");
        headRow.createCell(7).setCellValue("异常存取原因");
        headRow.createCell(8).setCellValue("OA考勤");

        //单元格格式
        headRow.setHeightInPoints((short) 20);
        createCell(list,mSheet);
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
    private static void createCell(List<CheckInfo> list, HSSFSheet sheet) {
        for(CheckInfo checkInfo:list){
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            System.out.println("执行写入");
            dataRow.createCell(0).setCellValue(checkInfo.getCabinetDoorNumber());
            dataRow.createCell(1).setCellValue(checkInfo.getDepartmentName());
            dataRow.createCell(2).setCellValue(checkInfo.getEmployeeName());
            dataRow.createCell(3).setCellValue(checkInfo.getPushTime());
            dataRow.createCell(4).setCellValue(checkInfo.getPushStatus());
            dataRow.createCell(5).setCellValue(checkInfo.getPullTime());
            dataRow.createCell(6).setCellValue(checkInfo.getPullStatus());
            dataRow.createCell(7).setCellValue(checkInfo.getRemark());
            dataRow.createCell(8).setCellValue(checkInfo.getOAInfo());

            //自适应列宽
//            sheet.autoSizeColumn(0, true);
        }


    }

}
