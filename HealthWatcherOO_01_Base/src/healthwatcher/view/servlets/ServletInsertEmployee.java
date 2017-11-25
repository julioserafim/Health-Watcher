package healthwatcher.view.servlets;


import healthwatcher.model.employee.Employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lib.exceptions.InsertEntryException;
import lib.exceptions.InvalidSessionException;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;



public class ServletInsertEmployee extends HWServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out;
        Employee employee;
        HttpSession session = request.getSession(false);
       
        response.setContentType("text/html");

        out = response.getWriter();

        try {
            if (session == null) {
                throw new InvalidSessionException();
            }            

            //Complaint Normal
            String name = request.getParameter("name");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            
            employee = new Employee(login, password, name);
            
            facade.insert(employee);
            
            out.println(HTMLCode.htmlPageAdministrator("Operation executed", "Employee inserted"));
        } catch (ObjectAlreadyInsertedException e) {
        	out.println(lib.util.HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);
        } catch (ObjectNotValidException e) {
        	out.println(lib.util.HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);            
        }catch (InvalidSessionException e) {
        	out.println(lib.util.HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);
        } catch(InsertEntryException e){ 
        	out.println(lib.util.HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);
        }catch(TransactionException e){
        	out.println(lib.util.HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);            
        }finally {
            out.close();
        }
    }
}