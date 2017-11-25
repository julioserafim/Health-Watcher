package healthwatcher.view.servlets;


import healthwatcher.Constants;
import healthwatcher.model.healthguide.HealthUnit;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lib.exceptions.CommunicationException;
import lib.exceptions.InvalidSessionException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;
import lib.util.IteratorDsk;



public class ServletUpdateHealthUnitSearch extends HWServlet {

    public static final String HEALTH_UNIT = "health unit";
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	PrintWriter out;
        HttpSession session = request.getSession(false);
        
        response.setContentType("text/html");

        out = response.getWriter();

        try {
            if (session == null) {
                throw new InvalidSessionException("Invalid Session! <br><a href=\""+Constants.SYSTEM_LOGIN+"\">Try again</a>");
            }
        
	        out.println("Queries - Complaint information");
	        out.println("<body><h1>Queries:<br>Querie about complaint</h1>");
	        out.println("<p>Choose a complaint: </p>");
	        out.println("<form method=\"POST\" action=\"http://"+Constants.SERVLET_SERVER_PATH+"ServletUpdateHealthUnitSearch\">");

            out.println("<div align=\"center\"><center><p><select name=\"numUS\" size=\"1\">");

           
            IteratorDsk repTP = facade.getHealthUnitList();

            if (repTP==null||!repTP.hasNext()) {
                out.println("</select></p></center></div>");
                out.println("<p><font color=\"red\"><b> There isn't any health units.</b></font></p>");
            } else {
                HealthUnit tp;
                do {
                    tp = (HealthUnit) repTP.next();
                    out.println("<option value=\""      +
                                tp.getCode() + "\"> " +
                                tp.getDescription()           +
                                " </OPTION>");
                } while (repTP.hasNext());
                repTP.close();
                
                out.println("</select></p></center></div>");
                out.println("  <div align=\"center\"><center><p><input type=\"submit\" value=\"Search\" name=\"B1\"></p></center></div></form>");
            }
            out.println(HTMLCode.closeAdministrator());
        } catch (ObjectNotFoundException e) {
            out.println("There isn't any health units");
        } catch (RemoteException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (CommunicationException e) {
			e.printStackTrace();
        } catch (InvalidSessionException e) {
            out.println(e.getMessage());			
		}finally {
            out.close();
        }

    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out;        
        HttpSession session = request.getSession(false);
        
        response.setContentType("text/html");

        out = response.getWriter();
        
        try {        
            if (session == null) {
                throw new InvalidSessionException();
            }        
        
            out.println("Update Health Unit");
            out.println("<script language=\"javascript\">");
            out.println("function submeterDados(modulo)");
            out.println("{");
    
            String a1 = "\"";
            String a2 = "\"";
    
            out.println("   var f = document.formAlterarUnidade2;");
            out.println("   if(f.descricao.value ==" + a1 + a2 + ")");
            out.println("   {");
            out.println("           alert(\"Digite a nova descrição da unidade!\");");
            out.println("           f.descricao.select();");
            out.println("           return;");
            out.println("   }");
            out.println("   f.submit();");
            out.println("}");
            out.println("//--></script>");
            out.println("<body><h1>Update Health unit:</h1>");
    
            int numUS = (new Integer(request.getParameter("numUS"))).intValue();
            
            HealthUnit unit = facade.searchHealthUnit(numUS);
            
            session.putValue(ServletUpdateHealthUnitSearch.HEALTH_UNIT, unit);
            
            out.println("<form method=\"POST\" name=\"formAlterarUnidade2\" action=\"http://"+Constants.SERVLET_SERVER_PATH+"ServletUpdateHealthUnitData\">");
            out.println("<div align=\"center\"><center><h4>Unit: " + numUS + "</h4></center></div>");                      
            out.println("<div align=\"center\"><center><p><strong>Name:</strong><br><input type=\"text\" name=\"descricao\" value=\"" + unit.getDescription() + "\" size=\"60\"></p></center></div>");
            out.println("<div align=\"center\"><center><h4><input type=\"button\" value=\"Update\" name=\"bt1\" onClick=\"javascript:submeterDados();\"><input type=\"reset\" value=\"Clear\" name=\"bt2\"></h4></center></div></form>");
            
            out.println(HTMLCode.closeAdministrator());                                
        } catch (ObjectNotFoundException e) {
            out.println("Health unit does not exist!");
        } catch (InvalidSessionException e) {
        	out.println(e.getMessage());
        }catch (RepositoryException e) {
			e.printStackTrace();
		}finally {
            out.close();
        }

    }

}
