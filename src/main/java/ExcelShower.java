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

            getters.put(1, Account.class.getMethod("getUserName"));

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
