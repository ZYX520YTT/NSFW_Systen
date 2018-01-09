package zhongfucheng.test.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ozc on 2017/5/25.
 */


/**
 * 使用POI1无非操作Excel无非就4个步骤：
 *
 * 创建/读取工作薄
 * 创建/读取工作表
 * 创建/读取行
 * 创建/读取单元格
 *
 *
 * */
public class TestPOI {
    @Test
    public void test() throws IOException {

        //创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建样式对象
        HSSFCellStyle style = workbook.createCellStyle();

        //创建合并单元格对象,从第六行开始到第十行，从第六列开始，到第十列
        CellRangeAddress cellRangeAddress = new CellRangeAddress(5, 9, 5, 9);

        //设置水平居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //创建font对象
        HSSFFont font = workbook.createFont();

        //设置字体为粗体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //字体为23字号
        font.setFontHeightInPoints((short) 23);
        //设置字体的颜色
        font.setColor(HSSFFont.COLOR_RED);

        //字体应用于样式
        style.setFont(font);

        //创建工作表
        HSSFSheet sheet = workbook.createSheet("我是新的工作表");

        sheet.addMergedRegion(cellRangeAddress);
        //创建行,坐标从0开始，我创建的是第六行
        HSSFRow row = sheet.createRow(5);

        //创建单元格，坐标也是从0开始，于是就是第六行第六列
        HSSFCell cell = row.createCell(5);

        //往单元格写数据
        cell.setCellValue("helloWorld");

        //设置单元格的样式
        cell.setCellStyle(style);

        //把工作薄写到硬盘中
        FileOutputStream outputStream = new FileOutputStream("C:\\工作薄.xls");
        workbook.write(outputStream);

        //关闭流
        workbook.close();
        outputStream.close();

    }

    @Test
    public void testRead() throws IOException {

        //获取输入流，读取Excel数据
        FileInputStream inputStream = new FileInputStream("C:\\工作薄.xls");

        //创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        //得到工作表
        HSSFSheet sheet = workbook.getSheetAt(0);

        //得到行
        HSSFRow row = sheet.getRow(2);

        //得到单元格
        HSSFCell cell = row.getCell(2);

        //得到单元格的数据
        String cellValue = cell.getStringCellValue();

        System.out.println(cellValue);

    }


}
