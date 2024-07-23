import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    // exportToExcel(List<Account> accounts, String filePath)方法用于将账户信息导出到Excel文件中。它接收一个List<Account>类型的accounts和一个String类型的filePath作为参数，并将账户信息写入到指定的Excel文件中。
    public static void exportToExcel(List<Account> accounts, String filePath) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(true); // true for .xlsx format
             FileOutputStream fileOut = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("Accounts");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("userId");
            headerRow.createCell(1).setCellValue("username");
            headerRow.createCell(2).setCellValue("password");
            headerRow.createCell(3).setCellValue("idNumber");
            headerRow.createCell(4).setCellValue("phoneNumber");
            headerRow.createCell(5).setCellValue("gender");
            headerRow.createCell(6).setCellValue("birthDate");
            headerRow.createCell(7).setCellValue("balance");

            int rowNum = 1;
            for (Account account : accounts) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(account.getUserId());
                row.createCell(1).setCellValue(account.getUsername());
                row.createCell(2).setCellValue(account.getPassword());
                row.createCell(3).setCellValue(account.getIdNumber());
                row.createCell(4).setCellValue(account.getPhoneNumber());
                row.createCell(5).setCellValue(account.getGender());
                row.createCell(6).setCellValue(account.getBirthDate());
                row.createCell(7).setCellValue(account.getBalance());
            }

            workbook.write(fileOut);
        } // try-with-resources will automatically close the resources
    }
    // importFromExcel(String filePath)方法用于从Excel文件中导入账户信息。它接收一个String类型的filePath作为参数，并返回一个List<Account>类型的accounts。
    public static List<Account> importFromExcel(String filePath) throws IOException, InvalidFormatException {
        List<Account> accounts = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileIn)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Account account = new Account();
                account.setUserId(row.getCell(0).getStringCellValue());
                account.setUsername(row.getCell(1).getStringCellValue());
                account.setPassword(row.getCell(2).getStringCellValue());
                account.setIdNumber(row.getCell(3).getStringCellValue());
                account.setPhoneNumber(row.getCell(4).getStringCellValue());
                account.setGender(row.getCell(5).getStringCellValue());
                account.setBirthDate(row.getCell(6).getStringCellValue());
                account.setBalance(row.getCell(7).getNumericCellValue());
                accounts.add(account);
            }
        } // try-with-resources will automatically close the resources
        return accounts;
    }
}