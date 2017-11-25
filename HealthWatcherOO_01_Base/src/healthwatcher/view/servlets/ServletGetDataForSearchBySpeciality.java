
package healthwatcher.view.servlets;

import healthwatcher.Constants;
import healthwatcher.model.healthguide.MedicalSpeciality;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.exceptions.CommunicationException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;
import lib.util.IteratorDsk;



public class ServletGetDataForSearchBySpeciality extends HWServlet {


    public void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out;

        response.setContentType("text/html");

        out = response.getWriter();

        out.println(HTMLCode.open("Queries - Health Units"));
        out.println("<body><h1>Queries:<br>Search Health units by Medical specialty</h1>");
        out.println("<p>Choose a specialty: </p>");
        out.println("<form method=\"POST\"action=\"http://"+Constants.SERVLET_SERVER_PATH+"ServletSearchHealthUnitsBySpecialty\">");

        try {
            out.println("<div align=\"center\"><center><p><select name=\"codEspecialidade\" size=\"1\">");

            IteratorDsk repEsp = facade.getSpecialityList();

            if (!repEsp.hasNext()) {
                out.println("</select></p></center></div>");
                out.println("<P>There isn't registered specialties.</P>");
            } else {
                MedicalSpeciality esp;

                do {
                    esp = (MedicalSpeciality) repEsp.next();
                    out.println("<option value=\"" + esp.getCodigo() + "\"> "+ esp.getDescricao() + " </OPTION>");
                } while (repEsp.hasNext());

                repEsp.close();
                out.println("</select></p></center></div>");
                out.println(" <div align=\"center\"><center><p><input type=\"submit\" value=\"Consultar\" name=\"B1\"></p></center></div></form>");
            }
            out.println(HTMLCode.closeQueries());
        } catch(RemoteException e) {
        	out.println("Error!");
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        } catch (ObjectNotFoundException e) {
            out.println("</select></p></center></div>");
            out.println("<P> " + e.getMessage() + " </P>");
            out.println("<P> Nenhuma especialidade foi cadastrada</P>");
        } catch (RepositoryException e) {
            out.println("</select></p></center></div>");
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (TransactionException e) {
            out.println("</select></p></center></div>");
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (CommunicationException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        } catch(Exception e) {
        	out.println("Error!");
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        } finally {        	
            out.close();
        }
    }
}
