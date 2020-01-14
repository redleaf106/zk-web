package cn.org.bjca.zk.platform.test;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JxlTest {

    public static void main(String[] args) {
        String outputUrl = "d://jxl.xls";
        String sheetName = "";
        //String[] titlesArray = new String[]{"部门","姓名","柜门号","存入时间","存入状态","取出时间","取出状态","紧急授权人","异常存取原因","备注"};
        String[] titlesArray = new String[]{"部门","姓名","柜门号"};
        ArrayList<String>[] arrayList = new ArrayList[3];
        for(int i=0;i<arrayList.length;i++){
            arrayList[i] = new ArrayList<>();
        }
        arrayList[0].add("zs");
        arrayList[0].add("ls");
        arrayList[1].add("18888888888888888888888888888888");
        arrayList[1].add("22222222222222222222222222222222");
        arrayList[2].add("man");
        arrayList[2].add("woman");
        System.out.println(outputUrl);

        try {
            JxlTest.writeExcel(outputUrl,sheetName,titlesArray,arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按列写入Excel并设置格式
     *
     * @param outputUrl
     *            输出路径
     * @param sheetName
     *            工作薄名称
     * @param titlesArray
     *            表头
     * @param arrayList
     *            表头数据
     * @throws IOException
     * @throws RowsExceededException
     * @throws WriteException
     */
    public static void writeExcel(String outputUrl, String sheetName, String[] titlesArray,
                                  ArrayList<String>... arrayList) throws IOException, RowsExceededException, WriteException {
        if (outputUrl == null || outputUrl.length() == 0) {
            System.out.println(
                    "Param(s) Error:outputUrl is required and the length of outputUrl is required greater than 0.");
            return;
        }
        int n = arrayList.length;
        if (titlesArray.length != n) {
            System.out.println("Param(s) Error:the titles' length is hoped to be equal to arrayList's length.");
            return;
        }
        //创建Excel文件
        System.out.println(outputUrl);
        File file = new File(outputUrl);
        file.createNewFile();
        //创建工作簿
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        //创建sheet，设置名称为sheetName，默认Sheet1
        if (sheetName == null || sheetName.length() == 0) {
            sheetName = "Sheet1";
        }
        WritableSheet sheet = workbook.createSheet(sheetName, 0);
        //设置titles
        String[] titles = titlesArray;
        //设置表头：列名和各种格式
        for (int i = 0; i < titles.length; i++) {
            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            CellView cellView = new CellView();
            cellView.setAutosize(true); //设置自动大小
            sheet.setColumnView(i, cellView); //根据内容自动设置列宽
            WritableCellFormat format = new WritableCellFormat(font);
            format.setAlignment(Alignment.CENTRE); //居中对齐
            //format.setBackground(Colour.YELLOW); //背景色
            format.setBorder(Border.ALL, BorderLineStyle.THICK, Colour.BLACK);//边框
            Label label = new Label(i, 0, titles[i], format);
            sheet.addCell(label);
        }
        //写入数据，并设置一些格式
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < arrayList[i].size(); j++) {
                //WritableFont.createFont("宋体")：设置字体为宋体
                //10：设置字体大小
                //WritableFont.BOLD:设置字体加粗（BOLD：加粗 NO_BOLD：不加粗）
                //false：设置非斜体
                //UnderlineStyle.NO_UNDERLINE：没有下划线
                WritableFont font = new WritableFont(WritableFont.createFont("Arial"), 10, WritableFont.NO_BOLD);
                WritableCellFormat format = new WritableCellFormat(font);
                Label label = new Label(i, j + 1, arrayList[i].get(j), format);
                sheet.addCell(label);
            }
        }
        //写入数据
        workbook.write();
        //关闭工作簿
        workbook.close();
    }
}
