package healthwatcher.view.servlets;

import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.Complaint;
import healthwatcher.model.complaint.FoodComplaint;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.exceptions.CommunicationException;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.Date;
import lib.util.HTMLCode;




public class ServletInsertFoodComplaint extends HWServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        response.setContentType("text/html");

        try {
            //Queixa Normal
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
            Address endSolicitante = new Address(ruaSolicitante, compSolicitante,cepSolicitante,ufSolicitante,
                                                 telefoneSolicitante,cidadeSolicitante, bairroSolicitante);
            String emailSolicitante = request.getParameter("emailSolicitante");

            //Queixa Alimentar
            // String nomeVitima = request.getParameter("nomeVitima");
            String ruaVitima = request.getParameter("ruaVitima");
            String compVitima = request.getParameter("compVitima");
            String bairroVitima = request.getParameter("bairroVitima");
            String cidadeVitima = request.getParameter("cidadeVitima");
            String ufVitima = request.getParameter("ufVitima");
            String cepVitima = request.getParameter("cepVitima ");
            String telefoneVitima = request.getParameter("telefoneVitima");
            Address endVitima = new Address(ruaVitima, compVitima,cepVitima, ufVitima,telefoneVitima,
                                            cidadeVitima, bairroVitima);

            short qtdeComensais = Short.parseShort(request.getParameter("qtdeComensais"));
            short qtdeDoentes = Short.parseShort(request.getParameter("qtdeDoentes"));
            short qtdeInternacoes = Short.parseShort(request.getParameter("qtdeInternacoes"));
            short qtdeObitos = Short.parseShort(request.getParameter("qtdeObitos"));

            String localAtendimento = request.getParameter("localAtendimento");
            String refeicaoSuspeita = request.getParameter("refeicaoSuspeita");
            Calendar agora = Calendar.getInstance();

            Complaint queixa = new FoodComplaint(nomeSolicitante, descricaoQueixa, observacaoQueixa,emailSolicitante,
            									  null, 1, null, new Date(agora.get(Calendar.DAY_OF_MONTH), 
            											 		          agora.get(Calendar.MONTH),
            											 		          agora.get(Calendar.YEAR)), 
                                                 endSolicitante, qtdeComensais, qtdeDoentes, qtdeInternacoes,
                                                 qtdeObitos, localAtendimento, refeicaoSuspeita, endVitima);
            
            int codigo = facade.insertComplaint(queixa);

            out.println(HTMLCode.htmlPage("Complaint inserted","<p> <h2> Food Complaint inserted</h2> </p>" +"<p> <h2> Save the complaint number: " + codigo + "</h2> </p>")); 
        } catch(RemoteException e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        } catch (RepositoryException e) {
            out.println(HTMLCode.errorPage("Problemas com o banco de dados"));
            e.printStackTrace(out);
        } catch (TransactionException e) {
            out.println(HTMLCode.errorPage("Erro no mecanismo de persist?ncia"));
            e.printStackTrace(out);
        } catch (ObjectAlreadyInsertedException e) {
            out.println(HTMLCode.errorPage("Esta queixa jah existe no BD"));
            e.printStackTrace(out);
        } catch (ObjectNotValidException e) {
            out.println(HTMLCode.errorPage("Erro ao inserir esta queixa"));
            e.printStackTrace(out);
        } catch (CommunicationException e) {
            out.println(HTMLCode.errorPage("Erro ao inserir esta queixa"));
            e.printStackTrace(out);
        }catch(Exception e){
        	e.printStackTrace();
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        } finally {
            out.println(HTMLCode.close());
            out.close();
        }
    }
}