import ir.pb.domains.Account;
import ir.pb.repositories.impl.AccountRepositoryImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Upload extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>login</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<form name = \"uploadPage\" enctype = \"multipart/form-data\" method=\"POST\" action=\"upload\">\n" +
                    "    <div>\n" +
                    "        <h4><center>Upload File</center></h4>\n" +
                    "        select <input type=\"file\" name=\"file2\">\n" +
                    "        <br>\n" +
                    "        <br>\n" +
                    "        <button type=\"submit\">send</button>\n" +
                    "    </div>\n" +
                    "</form>\n" +
                    "</body>\n" +
                    "</html>");
        out.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(ServletFileUpload.isMultipartContent(req)){
            AccountRepositoryImpl repository = new AccountRepositoryImpl(Account.class);
            HashMap<Integer, Method> setters = new HashMap<>();
            try {
                setters.put(0, Account.class.getMethod("setEmailAddress", String.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                setters.put(1, Account.class.getMethod("setUserName", String.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                setters.put(2, Account.class.getMethod("setStatus", String.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                setters.put(3, Account.class.getMethod("setPhoneNumber", String.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            String name = "";
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        name = new File(item.getName()).getName();
                        item.write( new File("D:\\HW16\\data" + File.separator + name));
                    }
                }
                //File uploaded successfully
                resp.getWriter().print("<h1>File Uploaded Successfully</h1>");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try
            {
                FileInputStream file = new FileInputStream(new File("D:\\HW16\\data" + File.separator + name));

                //Create Workbook instance holding reference to .xlsx file
                HSSFWorkbook workbook = new HSSFWorkbook(file);

                //Get first/desired sheet from the workbook
                HSSFSheet sheet = workbook.getSheetAt(0);

                //Iterate through each rows one by one
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext())
                {
                    Row row = rowIterator.next();
                    //For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    int i = 0;
                    Account account = new Account();
                    Account account1 = null;
                    while (cellIterator.hasNext())
                    {
                        Cell cell = cellIterator.next();
                        //Check the cell type and format accordingly
                        if(i <= 2) {
                            Method method = setters.get(i);
                            method.invoke(account, cell.getStringCellValue());
                        }else{
                            setters.get(i).invoke(account, String.valueOf(cell.getNumericCellValue()));

                        }
                        i++;
                        account1 = repository.findByEmailAddress(account.getEmailAddress());
                        if(account1 != null){
                            continue;
                        }
                        repository.save(account);
                    }
                }
                file.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        }

}
