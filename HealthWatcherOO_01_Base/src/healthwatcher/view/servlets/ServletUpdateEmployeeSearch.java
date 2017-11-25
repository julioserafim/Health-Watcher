package healthwatcher.view.servlets;


import healthwatcher.Constants;
import healthwatcher.model.employee.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lib.exceptions.InvalidSessionException;
import lib.util.HTMLCode;
import lib.util.Library;




public class ServletUpdateEmployeeSearch extends HWServlet {  

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out;
        HttpSession session = request.getSession(false);
        
        response.setContentType("text/html");

        out = response.getWriter();
      
        try {
            if (session == null) {
                throw new InvalidSessionException();
            }            

            Employee employee = (Employee)session.getValue(ServletLogin.EMPLOYEE);
            
            String[] keywords = {"##LOGIN##",
                                 "##NAME##",
                                 "##SERVLET_SERVER_PATH##",
                                 "##CLOSE##"};
                                  
            String[] newWords = {employee.getLogin(),
                                 employee.getName(),
                                 Constants.SERVLET_SERVER_PATH,
                                 HTMLCode.closeAdministrator()};
                                           
            out.println(Library.getFileListReplace(keywords, newWords, Constants.FORM_PATH+"UpdateEmployee.html"));
                         
        } catch (InvalidSessionException e) {
            out.println(HTMLCode.errorPageAdministrator("<p>Ivalid Session! <br>You must <a href=\""+Constants.SYSTEM_LOGIN+"\">login</a> again!"));
        } catch (FileNotFoundException e) {
            out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
        } finally {
            out.close();
        }
    }
}
