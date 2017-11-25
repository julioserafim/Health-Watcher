package healthwatcher.view.command;

import healthwatcher.Constants;
import healthwatcher.model.employee.Employee;
import healthwatcher.view.IFacade;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;
import lib.util.Library;

public class Login extends Command {

	public Login(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	private String[] keywords = { "##SYSTEM_ROOT##", "##SERVLET_SERVER_PATH##",
			"##CLOSE##", "##SYSTEM_ACTION##" };

	private String[] newWords = { Constants.SYSTEM_ROOT,
			Constants.SERVLET_SERVER_PATH, HTMLCode.closeAdministrator(), Constants.SYSTEM_ACTION };

	public static final String EMPLOYEE = "employee";

	public void execute() {
		PrintWriter out = null;
        HttpSession session = request.getSession(true);
        
        response.setContentType("text/html");

        try {
        	out = response.getWriter();
        } catch (Exception e) {
        	e.printStackTrace();
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");        

        try {
            Employee employee = facade.searchEmployee(login);
            
            if (employee.validatePassword(password)) {
                session.putValue(Login.EMPLOYEE, employee);
                                                              
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