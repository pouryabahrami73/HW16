import ir.pb.domains.Account;
import ir.pb.repositories.impl.AccountRepositoryImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelMaker extends HttpServlet {
    /*public static void main(String[] args) throws IOException {
        HashMap<Integer, Method> getters = new HashMap<>();
        try {
            getters.put(0, Account.class.getMethod("getEmailAddress"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            getters.put(1, Account.class.getMethod("getUserName"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            getters.put(2, Account.class.getMethod("getStatus"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            getters.put(3, Account.class.getMethod("getPhoneNumber"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        List<Account> accounts = new AccountRepositoryImpl(Account.class).findAll();


        Long numberOfRows = Long.valueOf((Long) new AccountRepositoryImpl(Account.class).countAll());
        int i = 0;
        int x = 0;
        int sheetNumber = 0;
        while(i < numberOfRows) {
            x = i;
            sheetNumber += 1;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("First");
            lable1:
            for (i = 0 + x; i < 10 + x; i++) {
                HSSFRow row = sheet.createRow(i);
                for (int j = 0; j < 4; j++) {
                    HSSFCell cell = row.createCell(j);
                    try {
                        if (i > numberOfRows - 1) {
                            break lable1;
                        }
                        cell.setCellValue(String.valueOf(getters.get(j).invoke(accounts.get(i))));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                sheet.autoSizeColumn(i);
            }
            String fileName = String.format("test%d.xls", sheetNumber);
            workbook.write(new FileOutputStream(fileName));
            String zipPath = String.format("D:\\HW16\\ZippedFileTest%d.zip", sheetNumber);
            //zip maker
            try {
                byte buffer[] = new byte[2048];

                FileOutputStream fileOutputStream = new FileOutputStream(
                        zipPath);
                ZipOutputStream zipOutputStream = new ZipOutputStream(
                        new BufferedOutputStream(fileOutputStream));

                File inputDir = new File("E:\\maktab class\\tamrin\\HW16");
                String listOfFiles[] = inputDir.list();

                BufferedInputStream bufferedInputStream = null;

                System.out.println("Adding File to zip: " + "test.xls");
                FileInputStream fileInputStream = new FileInputStream(new File(
                        inputDir, fileName));
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                ZipEntry entry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(entry);
                int count;
                while ((count = bufferedInputStream.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, count);
                }
                bufferedInputStream.close();

                zipOutputStream.close();

                System.out.println("File Zipped!!!!!");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        HashMap<Integer, Method> getters = new HashMap<>();
        try {
            getters.put(0, Account.class.getMethod("getEmailAddress"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            getters.put(1, Account.class.getMethod("getUserName"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            getters.put(2, Account.class.getMethod("getStatus"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            getters.put(3, Account.class.getMethod("getPhoneNumber"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        List<Account> accounts = new AccountRepositoryImpl(Account.class).findAll();
        Long numberOfRows = Long.valueOf((Long) new AccountRepositoryImpl(Account.class).countAll());
        int i = 0;
        int x = 0;
        int sheetNumber = 0;
        while(i < numberOfRows) {
            x = i;
            sheetNumber += 1;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(String.valueOf("First"));
            lable1:
            for (int y = 0; y < 10; y++) {
                i = x + y;
                HSSFRow row = sheet.createRow(i);
                for (int j = 0; j < 4; j++) {
                    HSSFCell cell = row.createCell(j);
                    try {
                        if (i > numberOfRows - 1) {
                            break lable1;
                        }
                        cell.setCellValue(String.valueOf(getters.get(j).invoke(accounts.get(i))));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                sheet.autoSizeColumn(i);
            }
            String fileName = String.format("test%d.xls", sheetNumber);
            workbook.write(new FileOutputStream(fileName));
            String zipPath = String.format("D:\\HW16\\ZippedFileTest%d.zip", sheetNumber);
            //zip maker
            try {
                byte buffer[] = new byte[2048];

                FileOutputStream fileOutputStream = new FileOutputStream(
                        zipPath);
                ZipOutputStream zipOutputStream = new ZipOutputStream(
                        new BufferedOutputStream(fileOutputStream));

                File inputDir = new File("E:\\maktab class\\tamrin\\HW16");
                String listOfFiles[] = inputDir.list();

                BufferedInputStream bufferedInputStream = null;

                System.out.println("Adding File to zip: " + "test.xls");
                FileInputStream fileInputStream = new FileInputStream(new File(
                        inputDir, fileName));
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                ZipEntry entry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(entry);
                int count;
                while ((count = bufferedInputStream.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, count);
                }
                bufferedInputStream.close();

                zipOutputStream.close();

                System.out.println("File Zipped!!!!!");

            } catch (Exception e) {
                e.printStackTrace();
            }
            out.print(String.format("<a href = \"%s\">" +
                    "page" + sheetNumber + "</a><br>", fileName));
        }
        out.close();
    }
}
