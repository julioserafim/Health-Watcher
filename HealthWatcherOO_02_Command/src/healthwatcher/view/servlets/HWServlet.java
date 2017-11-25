/**
 * Copyright (c) 2006 Macacos.org. All Rights Reserved
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software Foundation; either version 
 * 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See 
 * the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
 * MA  02110-1301  USA
 * 
 * You can find the license also here: http://www.gnu.org/copyleft/lesser.html
 * 
 * 
 * Created on Sep 16, 2006 by Thiago Tonelli Bartolomei
 * -------------------------------------------------
 *           \                                                                           
 *            \                                                                          
 *               __                                                                      
 *          w  c(..)o                                                                    
 *           \__(o)                                                                      
 *               /\                                                                      
 *            w_/(_)-~                                                                   
 *                /|                                                                     
 *               | \                                                                     
 *               m  m   
 */
package healthwatcher.view.servlets;


import healthwatcher.view.IFacade;
import healthwatcher.view.command.Command;
import healthwatcher.view.command.ConfigRMI;
import healthwatcher.view.command.GetDataForSearchByDiseaseType;
import healthwatcher.view.command.GetDataForSearchByHealthUnit;
import healthwatcher.view.command.GetDataForSearchBySpeciality;
import healthwatcher.view.command.InsertAnimalComplaint;
import healthwatcher.view.command.InsertEmployee;
import healthwatcher.view.command.InsertFoodComplaint;
import healthwatcher.view.command.InsertSpecialComplaint;
import healthwatcher.view.command.Login;
import healthwatcher.view.command.LoginMenu;
import healthwatcher.view.command.SearchComplaintData;
import healthwatcher.view.command.SearchDiseaseData;
import healthwatcher.view.command.SearchHealthUnitsBySpecialty;
import healthwatcher.view.command.SearchSpecialtiesByHealthUnit;
import healthwatcher.view.command.UpdateComplaintData;
import healthwatcher.view.command.UpdateComplaintList;
import healthwatcher.view.command.UpdateComplaintSearch;
import healthwatcher.view.command.UpdateEmployeeData;
import healthwatcher.view.command.UpdateEmployeeSearch;
import healthwatcher.view.command.UpdateHealthUnitData;
import healthwatcher.view.command.UpdateHealthUnitList;
import healthwatcher.view.command.UpdateHealthUnitSearch;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * TODO - describe this file
 * 
 * @author Thiago Tonelli Bartolomei <thiago.bartolomei@gmail.com>
 */
public class HWServlet extends HttpServlet {

	protected IFacade facade = null;
	private Hashtable commandTable;
    
	private static final String CommandConfigRMI		             = "ConfigRMI";
	private static final String CommandGetDataForSearchByDiseaseType = "SearchByDiseaseType";
	private static final String CommandGetDataForSearchByHealthUnit  = "SearchByHealthUnit";
	private static final String CommandGetDataForSearchBySpeciality  = "SearchBySpecialty";
	private static final String CommandInsertAnimalComplaint         = "InsertAnimalComplaint";
	private static final String CommandInsertEmployee                = "InsertEmployee";
	private static final String CommandInsertFoodComplaint           = "InsertFoodComplaint";
	private static final String CommandInsertSpecialComplaint        = "InsertSpecialComplaint";
	private static final String CommandLogin                         = "Login";
	private static final String CommandLoginMenu                     = "LoginMenu";
	private static final String CommandSearchComplaintData           = "SearchComplaintData";
	private static final String CommandSearchDiseaseData             = "SearchDiseaseData";
	private static final String CommandSearchHealthUnitsBySpecialty  = "SearchHealthUnitsBySpecialty";
	private static final String CommandSearchSpecialtiesByHealthUnit = "SearchSpecialtiesByHealthUnit";
	private static final String CommandUpdateComplaintData           = "UpdateComplaintData";
	private static final String CommandUpdateComplaintList           = "UpdateComplaintList";
	private static final String CommandUpdateComplaintSearch         = "UpdateComplaintSearch";
	private static final String CommandUpdateEmployeeData            = "UpdateEmployeeData";
	private static final String CommandUpdateEmployeeSearch          = "UpdateEmployeeSearch";
	private static final String CommandUpdateHealthUnitData          = "UpdateHealthUnitData";
	private static final String CommandUpdateHealthUnitSearch        = "UpdateHealthUnitSearch";
	private static final String CommandUpdateHealthUnitList          = "UpdateHealthUnitList";

    public void init(ServletConfig config) throws ServletException {

        try {
            System.out.println("About to lookup...");
            facade = (IFacade) java.rmi.Naming.lookup("//" + healthwatcher.Constants.SERVER_NAME + "/" + healthwatcher.Constants.SYSTEM_NAME);
            System.out.println("Remote DisqueSaude found");
            initCommands();
        } catch (java.rmi.RemoteException rmiEx) {
            rmiInitExceptionHandling(rmiEx);
        } catch (java.rmi.NotBoundException rmiEx) {
            rmiInitExceptionHandling(rmiEx);
        } catch (java.net.MalformedURLException rmiEx) {
            rmiInitExceptionHandling(rmiEx);
        }
    }

    protected void rmiInitExceptionHandling(Throwable exception) {
    	String error =  "<p>****************************************************<br>" +
                 "Error during servlet initialization!<br>" +
                 "The exception message is:<br><dd>" + exception.getMessage() +
                 "<p>You may have to restart the servlet container.<br>" +
                 "*******************************************************";
        System.out.println(error);
    }
    
    private void initCommands() {
    	commandTable = new Hashtable();
    	commandTable.put(HWServlet.CommandConfigRMI, new ConfigRMI(facade));
    	commandTable.put(HWServlet.CommandGetDataForSearchByDiseaseType, new GetDataForSearchByDiseaseType(facade));
    	commandTable.put(HWServlet.CommandGetDataForSearchByHealthUnit, new GetDataForSearchByHealthUnit(facade));
   	 	commandTable.put(HWServlet.CommandGetDataForSearchBySpeciality, new GetDataForSearchBySpeciality(facade));
    	commandTable.put(HWServlet.CommandInsertAnimalComplaint, new InsertAnimalComplaint(facade));
    	commandTable.put(HWServlet.CommandInsertEmployee, new InsertEmployee(facade));
    	commandTable.put(HWServlet.CommandInsertFoodComplaint, new InsertFoodComplaint(facade));
    	commandTable.put(HWServlet.CommandInsertSpecialComplaint, new InsertSpecialComplaint(facade));
    	commandTable.put(HWServlet.CommandLogin, new Login(facade));
    	commandTable.put(HWServlet.CommandLoginMenu, new LoginMenu(facade));
    	commandTable.put(HWServlet.CommandSearchComplaintData, new SearchComplaintData(facade));
    	commandTable.put(HWServlet.CommandSearchDiseaseData, new SearchDiseaseData(facade));
    	commandTable.put(HWServlet.CommandSearchHealthUnitsBySpecialty, new SearchHealthUnitsBySpecialty(facade));
    	commandTable.put(HWServlet.CommandSearchSpecialtiesByHealthUnit, new SearchSpecialtiesByHealthUnit(facade));
    	commandTable.put(HWServlet.CommandUpdateComplaintData, new UpdateComplaintData(facade));
    	commandTable.put(HWServlet.CommandUpdateComplaintList, new UpdateComplaintList(facade));
    	commandTable.put(HWServlet.CommandUpdateComplaintSearch, new UpdateComplaintSearch(facade));
    	commandTable.put(HWServlet.CommandUpdateEmployeeData, new UpdateEmployeeData(facade));
    	commandTable.put(HWServlet.CommandUpdateEmployeeSearch, new UpdateEmployeeSearch(facade));
    	commandTable.put(HWServlet.CommandUpdateHealthUnitData, new UpdateHealthUnitData(facade));
    	commandTable.put(HWServlet.CommandUpdateHealthUnitSearch, new UpdateHealthUnitSearch(facade));
    	commandTable.put(HWServlet.CommandUpdateHealthUnitList, new UpdateHealthUnitList(facade));		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	handleRequest(request,response);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	handleRequest(request,response);
    }
    
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
    	String operation = request.getParameter("operation");
    	Command command = (Command) commandTable.get(operation);
    	command.setRequest(request);
    	command.setReponse(response);
    	command.execute();
    }    
}
