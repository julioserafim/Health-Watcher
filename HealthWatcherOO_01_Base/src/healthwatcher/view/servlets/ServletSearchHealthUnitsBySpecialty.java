package healthwatcher.view.servlets;


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



public class ServletSearchHealthUnitsBySpecialty extends HWServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        int codigoEsp =  Integer.parseInt(request.getParameter("codEspecialidade"));

        try {
        	IteratorDsk repUS = facade.searchHealthUnitsBySpeciality(codigoEsp);

        	out.println(HTMLCode.open("Queries - Health Unit"));
            out.println("<body><h1>Querie result<br>Health units</h1>");
            
            out.println("<P><h3>Medical specialty: " + codigoEsp + "</h3></P>");
            out.println("<h3>Health units:</h3>");

            while (repUS.hasNext()) {
                HealthUnit us = (HealthUnit) repUS.next();
                out.println("<dd><dd>" + us.getDescription());             
            }
            out.println(HTMLCode.closeQueries());
        } catch(RemoteException e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        }catch (ObjectNotFoundException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (RepositoryException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (TransactionException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (CommunicationException e) {
            out.println("<P> " + e.getMessage() + " </P>");            
        } catch(Exception e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        }finally {
            out.close();
        }
    }
}