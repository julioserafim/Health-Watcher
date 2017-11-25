
package healthwatcher.view.servlets;

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




public class ServletSearchSpecialtiesByHealthUnit extends HWServlet {
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        int codigoUS = Integer.parseInt(request.getParameter("codUnidadeSaude"));

        try {

            IteratorDsk repEsp = facade.searchSpecialitiesByHealthUnit(codigoUS);
            
            out.println(HTMLCode.open("Queries - Especialties"));
            out.println("<body><h1>Querie result</h1>");
            
            out.println("<P><h3>Health unit: " + codigoUS+ " </h3></P>");
            out.println("<h3>Especialties :</h3>");

            while (repEsp.hasNext()) {
                MedicalSpeciality esp = (MedicalSpeciality) repEsp.next();
                out.println("<dd><dd>" + esp.getDescricao());
            
            }
            out.println(HTMLCode.closeQueries());            
        }catch(RemoteException e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        } catch (ObjectNotFoundException e) {
        	out.println(HTMLCode.errorPageQueries("This health unit does not have registered specialties."));
        } catch (RepositoryException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (TransactionException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (CommunicationException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        }catch(Exception e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        } finally {
            out.close();
        }
        
    }
}