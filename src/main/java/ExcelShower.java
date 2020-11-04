import ir.pb.domains.Account;
import ir.pb.repositories.impl.AccountRepositoryImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.RequestDispatcher;
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

public class ExcelShower extends HttpServlet {
    protected static int counter;
    /*public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        HashMap<Integer, Method> getters = new HashMap<>();
        getters.put(0, Account.class.getMethod("getEmailAddress"));
        getters.put(1, Account.class.getMethod("getUserName"));
        getters.put(2, Account.class.getMethod("getStatus"));
        getters.put(3, Account.class.getMethod("getPhoneNumber"));
        List<Account> accounts = new AccountRepositoryImpl(Account.class).findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("First");
        Long numberOfRows = Long.valueOf((Long) new AccountRepositoryImpl(Account.class).countAll());
        for(int i = 0; i < numberOfRows; i++) {
            HSSFRow row = sheet.createRow(i);
            for(int j = 0; j < 4; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(getters.get(j).invoke(accounts.get(i))));
            }
            sheet.autoSizeColumn(i);
        }
        workbook.write(new FileOutputStream("test.xls"));
        //zip maker
        try {
            byte buffer[] = new byte[2048];

            FileOutputStream fileOutputStream = new FileOutputStream(
                    "E:\\maktab class\\tamrin\\HW16\\ZippedFileTest.zip");
            ZipOutputStream zipOutputStream = new ZipOutputStream(
                    new BufferedOutputStream(fileOutputStream));

            File inputDir = new File("E:\\maktab class\\tamrin\\HW16");
            String listOfFiles[] = inputDir.list();

            BufferedInputStream bufferedInputStream = null;

                System.out.println("Adding File to zip: " + "test.xls");
                FileInputStream fileInputStream = new FileInputStream(new File(
                        inputDir, "test.xls"));
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                ZipEntry entry = new ZipEntry("test.xls");
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
    }*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accounts = new AccountRepositoryImpl(Account.class).findAll();
        req.setAttribute("accounts", accounts);
        Long numberOfRows = Long.valueOf((Long) new AccountRepositoryImpl(Account.class).countAll());
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
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Table</title>\n" +
                "</head>\n" +
                "\n" +
                "<body style = \" background-color: aqua;\">\n" +
                "   <div>\n" +
                "    <table border=\"4\">\n" +
                "        <tbody>\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th>Email Address</th>\n" +
                "                    <th>User Name</th>\n" +
                "                    <th>Status</th>\n" +
                "                    <th>Phone Number</th>\n" +
                "                </tr>\n");
        for(int i = 0; i < numberOfRows; i++) {
            out.print("<tr>\n");
            for(int j = 0; j < 4; j++) {
                try {
                    out.print("<th> " + getters.get(j).invoke(accounts.get(i)) + "</th>\n");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            out.print("</tr>\n");
        }
        out.print("            </thead>\n" +
                "        </tbody>\n" +
                "       </table>\n" +
                "   </div>\n" +
                "        <br>\n" +
                "   <div>\n" +
                "       <form method = \"POST\" action = \"download\">\n" +
                "           <button type = \"submit\">download</button>\n" +
                "       </form>\n" +
                "   </div>\n" +
                "   <div>\n" +
                "       <form method = \"GET\" action = \"upload\">\n" +
                "           <button type = \"submit\">upload</button>\n" +
                "       </form>\n" +
                "   </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
        out.close();
    }
}
