
package healthwatcher.view.servlets;

import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.AnimalComplaint;
import healthwatcher.model.complaint.Complaint;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.exceptions.CommunicationException;
import lib.exceptions.InvalidDateException;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.Date;
import lib.util.HTMLCode;




public class ServletInsertAnimalComplaint extends HWServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out;
        Complaint queixa;

        response.setContentType("text/html");

        out = response.getWriter();

        try {
            String descricaoQueixa = request.getParameter("descricaoQueixa");
            String observacaoQueixa = request.getParameter("observacaoQueixa");
            String nomeSolicitante = request.getParameter("nomeSolicitante");
            String ruaSolicitante = request.getParameter("ruaSolicitante");
            String compSolicitante = request.getParameter("compSolicitante");
            String bairroSolicitante = request.getParameter("bairroSolicitante");
            String cidadeSolicitante = request.getParameter("cidadeSolicitante");
            String ufSolicitante = request.getParameter("ufSolicitante");
            String cepSolicitante = request.getParameter("cepSolicitante ");
            String telefoneSolicitante = request.getParameter("telefoneSolicitante");
            Address endSolicitante = new Address(ruaSolicitante,
                                                 compSolicitante,
                                                 cepSolicitante,
                                                 ufSolicitante,
                                                 telefoneSolicitante,
                                                 cidadeSolicitante,
                                                 bairroSolicitante);
            
            String emailSolicitante = request.getParameter("emailSolicitante");

         
            String nomeAnimal = request.getParameter("nomeAnimal");
            short qtdeAnimal = Short.parseShort(request.getParameter("qtdeAnimal"));
            int diaIncomodo = Integer.parseInt(request.getParameter("diaIncomodo"));
            int mesIncomodo = Integer.parseInt(request.getParameter("mesIncomodo"));
            int anoIncomodo = Integer.parseInt(request.getParameter("anoIncomodo"));
            String ruaOcorrencia = request.getParameter("ruaOcorrencia");
            String compOcorrencia = request.getParameter("compOcorrencia");
            String bairroOcorrencia = request.getParameter("bairroOcorrencia");
            String cidadeOcorrencia = request.getParameter("cidadeOcorrencia");
            String ufOcorrencia = request.getParameter("ufOcorrencia");
            String cepOcorrencia = request.getParameter("cepOcorrencia ");
            String telefoneOcorrencia = request.getParameter("telefoneOcorrencia");
            
            Address endOcorrencia = new Address(ruaOcorrencia,
                                                compOcorrencia,
                                                cepOcorrencia,
                                                ufOcorrencia,
                                                telefoneOcorrencia,
                                                cidadeOcorrencia,
                                                bairroOcorrencia);
            
            Calendar agora              = Calendar.getInstance();

            queixa = new AnimalComplaint(nomeSolicitante, descricaoQueixa, observacaoQueixa,
            							 emailSolicitante, null, 1, null,
            							 new Date(agora.get(Calendar.DAY_OF_MONTH), agora.get(Calendar.MONTH),
            									  agora.get(Calendar.YEAR)), endSolicitante, qtdeAnimal,
                                         new Date(diaIncomodo, mesIncomodo,anoIncomodo), nomeAnimal,endOcorrencia);
            
            int codigo = facade.insertComplaint(queixa);

            out.println(HTMLCode.htmlPage("Complaint inserted", 
                    "<p> <h2> Animal Complaint inserted</h2> </p>" +
                    "<p> <h2> Save the complaint number: " + codigo + "</h2> </p>")); 

        } catch(RemoteException e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
        } catch (RepositoryException e) {
            out.println(HTMLCode.errorPage("Problemas com o banco de dados"));
        } catch (TransactionException e) {
            out.println(HTMLCode.errorPage("Erro no mecanismo de persist?ncia"));
        } catch (ObjectAlreadyInsertedException e) {
        	out.println(HTMLCode.errorPage("Complaint already inserted"));
        } catch (ObjectNotValidException e) {
        	out.println(HTMLCode.errorPage("Unespected error. Try to contact the support team.")); 
        } catch (CommunicationException e) {
            out.println(HTMLCode.errorPage("Erro ao inserir esta queixa"));
            e.printStackTrace(out);
        }catch(InvalidDateException e){
        	out.println(HTMLCode.errorPage("Invalid date."));
        }finally {
            out.println(HTMLCode.close());
            out.close();
        }
    }
}