package healthwatcher.view.command;

import healthwatcher.view.IFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public abstract class Command {
	
	protected IFacade facade;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	public void setReponse(HttpServletResponse response)
	{
		this.response = response;
	}

	public Command(IFacade f){
		facade= f;
	}
	
	public abstract void execute();

}
