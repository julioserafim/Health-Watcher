package healthwatcher.view.command;

import healthwatcher.Constants;
import healthwatcher.model.employee.Employee;
import healthwatcher.view.IFacade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import lib.exceptions.InvalidSessionException;
import lib.util.HTMLCode;
import lib.util.Library;

public class UpdateEmployeeSearch extends Command {

	public UpdateEmployeeSearch(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void execute() {
		PrintWriter out = null;
		HttpSession session = request.getSession(false);

		response.setContentType("text/html");

		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (session == null) {
				throw new InvalidSessionException();
			}

			Employee employee = (Employee) session
					.getValue(Login.EMPLOYEE);

			String[] keywords = { "##LOGIN##", "##NAME##",
					"##SERVLET_SERVER_PATH##", "##CLOSE##" };

			String[] newWords = { employee.getLogin(), employee.getName(),
					Constants.SERVLET_SERVER_PATH,
					HTMLCode.closeAdministrator() };

			out.println(Library.getFileListReplace(keywords, newWords,
					Constants.FORM_PATH + "UpdateEmployee.html"));

		} catch (InvalidSessionException e) {
			out
					.println(HTMLCode
							.errorPageAdministrator("<p>Ivalid Session! <br>You must <a href=\""
									+ Constants.SYSTEM_LOGIN
									+ "\">login</a> again!"));
		} catch (FileNotFoundException e) {
			out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
		} finally {
			out.close();
		}
	}
}
