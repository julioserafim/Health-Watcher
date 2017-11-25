package healthwatcher.view.command;


import healthwatcher.model.healthguide.HealthUnit;
import healthwatcher.view.IFacade;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import lib.exceptions.InvalidSessionException;
import lib.util.HTMLCode;




public class UpdateHealthUnitData extends Command {

    public UpdateHealthUnitData(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void execute() {
        PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        HttpSession session = request.getSession(true);
        
        response.setContentType("text/html");

        HealthUnit unit;
        
        try {
        	if (session == null) {
                throw new InvalidSessionException();
            }
        	
        	unit = (HealthUnit) session.getValue(UpdateHealthUnitSearch.HEALTH_UNIT);                        
            
            String descricao = request.getParameter("descricao");            
            
            unit.setDescription(descricao);
        	
            out.println(HTMLCode.htmlPageAdministrator("Operation executed", "Health Unit updated"));
            
            facade.updateHealthUnit(unit);

        } catch(Exception e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
        } finally {
            out.close();
        }
	}
}
