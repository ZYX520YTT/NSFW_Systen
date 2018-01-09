package zhongfucheng.core.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import zhongfucheng.user.entity.User;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozc on 2017/5/25.
 */
public class ExcelUtils {

    /**
     * 第一行写死，字体大小11，居中，粗体,合并单元格
     * 第二行写死，粗体
     * 第三行开始，是数据库列表的数据
     */
    public static void exportExcel(List<User> list, ServletOutputStream outputStream) {

        /***********创建工作薄---样式---字体--单元格*************/
        HSSFWorkbook workbook = new HSSFWorkbook();

        //第一行的合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);

        HSSFCellStyle cellStyle = createStyle(workbook, (short) 24);
        HSSFCellStyle cellStyle2 = createStyle(workbook, (short) 13);


        /***********创建工作表*************/
        HSSFSheet sheet = workbook.createSheet("用户列表");

        //第一行单元格应用于工作表
        sheet.addMergedRegion(cellRangeAddress);

        //设置默认列宽
        sheet.setDefaultColumnWidth(25);

        /***********创建行*************/
        //第一行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("用户列表");

        //第二行数据也是写死的，我们用数组遍历即可
        String[] data = {"用户名", "帐号", "所属部门", "性别", "电子邮箱"};
        HSSFRow row1 = sheet.createRow(1);
        for (int i = 0; i < data.length; i++) {
            HSSFCell cell1 = row1.createCell(i);
            cell1.setCellValue(data[i]);

            //加载第二行样式
            cell1.setCellStyle(cellStyle2);

        }

        /***************行和列在循环的时候，不要重复了。不然会报错的！！！！*****************/
        //第三行数据就是我们数据库保存的数据

        if (list != null) {
            int i = 2;
            for (User user : list) {

                //从第三行开始
                HSSFRow row2 = sheet.createRow(i);

                HSSFCell row2Cel0 = row2.createCell(0);
                row2Cel0.setCellValue(user.getName());

                HSSFCell row2Cell = row2.createCell(1);
                row2Cell.setCellValue(user.getAccount());

                HSSFCell row2Cel2 = row2.createCell(2);
                row2Cel2.setCellValue(user.getDept());

                HSSFCell row2Cel3 = row2.createCell(3);
                row2Cel3.setCellValue(user.isGender() ? "男" : "女");

                HSSFCell row2Cel4 = row2.createCell(4);
                row2Cel4.setCellValue(user.getEmail());

                i++;
            }
        }
        try {
            //写到outputSteam上
            workbook.write(outputStream);

            workbook.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param workbook 当前使用工作薄
     * @param fontSize 字体大小
     */
    public static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize) {

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        return cellStyle;
    }

    public static List<User> importExcel(File userExcel, String userExcelFileName) {

        try {
            FileInputStream fileInputStream = new FileInputStream(userExcel);
            boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
            //1、读取工作簿
            Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream) : new XSSFWorkbook(fileInputStream);
            //2、读取工作表
            Sheet sheet = workbook.getSheetAt(0);
            //3、读取行
            List<User> users = new ArrayList<>();
            if (sheet.getPhysicalNumberOfRows() > 2) {
                User user = null;
                for (int k = 2; k < sheet.getPhysicalNumberOfRows(); k++) {
                    //4、读取单元格
                    Row row = sheet.getRow(k);
                    user = new User();
                    //用户名
                    Cell cell0 = row.getCell(0);
                    user.setName(cell0.getStringCellValue());
                    //帐号
                    Cell cell1 = row.getCell(1);
                    user.setAccount(cell1.getStringCellValue());
                    //所属部门
                    Cell cell2 = row.getCell(2);
                    user.setDept(cell2.getStringCellValue());
                    //性别
                    Cell cell3 = row.getCell(3);
                    user.setGender(cell3.getStringCellValue().equals("男"));
                    //手机号
                    String mobile = "";
                    Cell cell4 = row.getCell(4);
                    try {
                        mobile = cell4.getStringCellValue();
                    } catch (Exception e) {
                        double dMobile = cell4.getNumericCellValue();
                        mobile = BigDecimal.valueOf(dMobile).toString();
                    }
                    user.setMobile(mobile);

                    //电子邮箱
                    Cell cell5 = row.getCell(5);
                    user.setEmail(cell5.getStringCellValue());
                    //生日
                    Cell cell6 = row.getCell(6);
                    if (cell6.getDateCellValue() != null) {
                        user.setBirthday(cell6.getDateCellValue());
                    }
                    //默认用户密码为 123456
                    user.setPassword("123456");
                    //默认用户状态为 有效
                    user.setState(User.USER_STATE_VALID);

                    users.add(user);
                }
            }

            workbook.close();
            fileInputStream.close();
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
