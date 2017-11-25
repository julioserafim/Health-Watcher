package healthwatcher.view.servlets;


import healthwatcher.model.complaint.Complaint;
import healthwatcher.model.complaint.Situation;
import healthwatcher.model.employee.Employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lib.exceptions.InvalidSessionException;
import lib.util.HTMLCode;




public class ServletUpdateComplaintData extends HWServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        
        String      obsQueixa = request.getParameter("obsQueixa");
        Complaint     q         = null;

        response.setContentType("text/html");

        try {
            if (session == null) {
            	throw new InvalidSessionException();
            }
            
            q = (Complaint) session.getValue(ServletUpdateComplaintSearch.QUEIXA);
            q.setObservacao(obsQueixa);
            q.setSituacao(Situation.QUEIXA_FECHADA);
            Calendar agora = Calendar.getInstance();
            q.setDataParecer(new lib.util.Date(agora.get(Calendar.DAY_OF_MONTH),
                                      agora.get(Calendar.MONTH),
                                      agora.get(Calendar.YEAR)));
            Employee employee = (Employee) session.getValue(ServletLogin.EMPLOYEE);
            q.setAtendente(employee);
            facade.updateComplaint(q);
            
            out.println(HTMLCode.htmlPageAdministrator("Operation executed", "Complaint updated"+"<P>" + obsQueixa + "</P>"));
        } catch(Exception e){	
            out.println(lib.util.HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);
        } finally {
            out.close();
        }   
    }
}
