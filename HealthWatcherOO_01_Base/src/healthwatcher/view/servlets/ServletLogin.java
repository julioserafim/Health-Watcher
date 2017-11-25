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
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;
import lib.util.Library;



public class ServletLogin extends HWServlet {
                       
	private String[] keywords = {"##SYSTEM_ROOT##",
			  			 		 "##SERVLET_SERVER_PATH##",
								 "##CLOSE##"};
                                  
	private String[] newWords = {Constants.SYSTEM_ROOT,
								 Constants.SERVLET_SERVER_PATH,
								 HTMLCode.closeAdministrator()};


    public static final String EMPLOYEE = "employee";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out;
        HttpSession session = request.getSession(false);
        
        response.setContentType("text/html");

        out = response.getWriter();

        try {
            if (session == null) {
                throw new InvalidSessionException("Invalid Session! <br><a href=\""+Constants.SYSTEM_LOGIN+"\">Try again</a>");
            }            
                                           
            out.println(Library.getFileListReplace(keywords, newWords, Constants.FORM_PATH+"MenuEmployee.html"));
        } catch (Exception e) {
            out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
        } finally {           
            out.close();
        }

    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
                    
        PrintWriter out;
        HttpSession session = request.getSession(true);
        
        response.setContentType("text/html");

        out = response.getWriter();

        String login = request.getParameter("login");
        String password = request.getParameter("password");        

        try {
            Employee employee = facade.searchEmployee(login);
            
            if (employee.validatePassword(password)) {
                session.putValue(ServletLogin.EMPLOYEE, employee);
                                                              
                out.println(Library.getFileListReplace(keywords, newWords, Constants.FORM_PATH+"MenuEmployee.html"));                
            } else {                                 
                out.println(HTMLCode.errorPage("Invalid password! <br><a href=\""+Constants.SYSTEM_LOGIN+"\">Try again</a>"));
            }
        } catch (ObjectNotFoundException e) {
            out.println(HTMLCode.errorPage("Invalid login! <br><a href=\""+Constants.SYSTEM_LOGIN+"\">Try again</a>"));
        } catch (FileNotFoundException e) {
            out.println(HTMLCode.errorPage(e.getMessage()));
        } catch (TransactionException e) {
        	out.println(HTMLCode.errorPage(e.getMessage()));
		} catch (Exception e) {
        	out.println(HTMLCode.errorPage(e.getMessage()));
		} finally {           
            out.close();
        }
    }
}