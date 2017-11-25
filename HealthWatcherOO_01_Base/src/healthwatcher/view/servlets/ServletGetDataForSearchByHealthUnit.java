package healthwatcher.view.servlets;

import healthwatcher.Constants;
import healthwatcher.model.healthguide.HealthUnit;

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



public class ServletGetDataForSearchByHealthUnit extends HWServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out;

        response.setContentType("text/html");

        out = response.getWriter();

        out.println(HTMLCode.open("Queries - Specialties"));
        out.println("<body><h1>Queries:<br>Search Specialties of a Health unit</h1>");
        out.println("<p>Choose a health unit: </p>");
        out.println("<form method=\"POST\"action=\"http://"+Constants.SERVLET_SERVER_PATH+"ServletSearchSpecialtiesByHealthUnit\">");

        try {
            out.println("<div align=\"center\"><center><p><select name=\"codUnidadeSaude\" size=\"1\">");

            IteratorDsk repUS = facade.getPartialHealthUnitList();

            if (!repUS.hasNext()) {
                out.println("</select></p></center></div>");
                out.println("<P> There isn't registered health units.</P>");
            } else {
                HealthUnit us;
                do {
                    us = (HealthUnit) repUS.next();
                    out.println("<option value=\""+ us.getCode() + "\"> " + us.getDescription() + " </OPTION>");
                } while (repUS.hasNext());

                repUS.close();
                out.println("</select></p></center></div>");
                out.println("<div align=\"center\"><center><p><input type=\"submit\" value=\"Consultar\" name=\"B1\"></p></center></div></form>");
            }
            out.println(HTMLCode.closeQueries());
        }catch(RemoteException e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        } catch (ObjectNotFoundException e) {
            out.println("</select></p></center></div>");
            out.println("<P> " + e.getMessage() + " </P>");
            out.println("<P> Nenhuma unidade de saude foi cadastrada</P>");
        } catch (RepositoryException e) {
            out.println("</select></p></center></div>");
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (TransactionException e) {
            out.println("</select></p></center></div>");
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (CommunicationException e) {
            out.println("</select></p></center></div>");
            out.println("<P> " + e.getMessage() + " </P>");
        } catch(Exception e){        	
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        } finally {
        	out.close();
        }
    }
}