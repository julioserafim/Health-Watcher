package healthwatcher.business;


import healthwatcher.Constants;
import healthwatcher.model.complaint.Complaint;
import healthwatcher.model.complaint.DiseaseType;
import healthwatcher.model.employee.Employee;
import healthwatcher.model.healthguide.HealthUnit;
import healthwatcher.view.IFacade;

import java.io.IOException;

import lib.exceptions.InsertEntryException;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.PersistenceMechanismException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.exceptions.UpdateEntryException;
import lib.persistence.IPersistenceMechanism;
import lib.persistence.PersistenceMechanism;
import lib.util.IteratorDsk;




public class HealthWatcherFacade extends java.rmi.server.UnicastRemoteObject implements IFacade {

	private static HealthWatcherFacade singleton; //padrao singleton

	private HealthWatcherFacadeInit fCid;

	private static IPersistenceMechanism pm = null;

	private static boolean pmCreated = false;

	public static void main(String[] args) {
		try {
			new HealthWatcherFacade();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HealthWatcherFacade() throws PersistenceMechanismException, IOException {
		this.fCid = new HealthWatcherFacadeInit();
		try {
			System.out.println("Creating RMI server...");
			System.out.println("Object exported");
			System.out.println(healthwatcher.Constants.SYSTEM_NAME);
			java.rmi.Naming.rebind("//" + healthwatcher.Constants.SERVER_NAME + "/"
					+ healthwatcher.Constants.SYSTEM_NAME, this);
			System.out.println("Server created and ready.");
		} catch (java.rmi.RemoteException rmiEx) {
			rmiFacadeExceptionHandling(rmiEx);
		} catch (java.net.MalformedURLException rmiEx) {
			rmiFacadeExceptionHandling(rmiEx);
		}
	}

	private void rmiFacadeExceptionHandling(Throwable exception) {
		System.out.println("**************************************************");
		System.out.println("* Error during server initialization!            *");
		System.out.println("* The exception message is:                      *");
		System.out.println("      " + exception.getMessage());
		System.out.println("* You may have to restart the server or registry.*");
		System.out.println("**************************************************");
		exception.printStackTrace();
	}

	public void updateHealthUnit(HealthUnit hu) throws RepositoryException,
			ObjectNotFoundException, TransactionException, java.rmi.RemoteException {
		fCid.updateHealthUnit(hu);
	}

	public void updateComplaint(Complaint q) throws TransactionException, RepositoryException,
			ObjectNotFoundException, ObjectNotValidException {
		this.fCid.updateComplaint(q);
	}

	public IteratorDsk searchSpecialitiesByHealthUnit(int code) throws ObjectNotFoundException,
			RepositoryException, TransactionException, java.rmi.RemoteException {
		lib.distribution.rmi.IteratorRMISourceAdapter sa = null;
		lib.util.LocalIterator iterator = (lib.util.LocalIterator) fCid
				.searchSpecialitiesByHealthUnit(code);
		lib.distribution.rmi.IteratorRMITargetAdapter iteratorTA = new lib.distribution.rmi.IteratorRMITargetAdapter(
				iterator, 3);
		sa = new lib.distribution.rmi.IteratorRMISourceAdapter(iteratorTA, iterator, 3);
		return sa;
	}

	public Complaint searchComplaint(int code) throws RepositoryException, ObjectNotFoundException,
			TransactionException, java.rmi.RemoteException {
		return this.fCid.searchComplaint(code);
	}

	public DiseaseType searchDiseaseType(int code) throws RepositoryException,
			ObjectNotFoundException, TransactionException, java.rmi.RemoteException {
		return fCid.searchDiseaseType(code);
	}

	public IteratorDsk searchHealthUnitsBySpeciality(int code) throws ObjectNotFoundException,
			RepositoryException, TransactionException, java.rmi.RemoteException {
		lib.distribution.rmi.IteratorRMISourceAdapter sa = null;
		lib.util.LocalIterator iterator = (lib.util.LocalIterator) fCid.searchHealthUnitsBySpeciality(code);
		lib.distribution.rmi.IteratorRMITargetAdapter iteratorTA = new lib.distribution.rmi.IteratorRMITargetAdapter(
				iterator, 3);
		sa = new lib.distribution.rmi.IteratorRMISourceAdapter(iteratorTA, iterator, 3);
		return sa;
	}

	public static IPersistenceMechanism getPm() {
		if (!pmCreated) {
			pm = pmInit();
			if (pm != null) {
				pmCreated = true;
			}
		}
		return pm;
	}

	static boolean isPersistent() {
		return Constants.isPersistent();
	}

	static IPersistenceMechanism pmInit() {
		IPersistenceMechanism returnValue = null;
		if (isPersistent()) {
			try {
				returnValue = PersistenceMechanism.getInstance();
				// Persistence mechanism connection
				returnValue.connect();
			} catch (PersistenceMechanismException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// Persistence mechanism desconnection for resource release
				try {
					if (getPm() != null) {
						getPm().disconnect();
					}
				} catch (PersistenceMechanismException mpe) {
					mpe.printStackTrace();
				}
			}
		}
		return returnValue;
	}

	public synchronized static HealthWatcherFacade getInstance()
			throws PersistenceMechanismException, IOException, java.rmi.RemoteException {
		if (singleton == null) {
			// Obtain valid persistence mechanism instace
			getPm();
			singleton = new HealthWatcherFacade();
		}
		return singleton;
	}

	public IteratorDsk getSpecialityList() throws RepositoryException, ObjectNotFoundException,
			TransactionException, java.rmi.RemoteException {
		lib.distribution.rmi.IteratorRMISourceAdapter sa = null;
		lib.util.LocalIterator iterator = (lib.util.LocalIterator) fCid.getSpecialityList();
		lib.distribution.rmi.IteratorRMITargetAdapter iteratorTA = new lib.distribution.rmi.IteratorRMITargetAdapter(
				iterator, 3);
		sa = new lib.distribution.rmi.IteratorRMISourceAdapter(iteratorTA, iterator, 3);
		return sa;
	}

	public IteratorDsk getDiseaseTypeList() throws RepositoryException, ObjectNotFoundException,
			TransactionException, java.rmi.RemoteException {
		lib.distribution.rmi.IteratorRMISourceAdapter sa = null;
		lib.util.LocalIterator iterator = (lib.util.LocalIterator) fCid.getDiseaseTypeList();
		lib.distribution.rmi.IteratorRMITargetAdapter iteratorTA = new lib.distribution.rmi.IteratorRMITargetAdapter(
				iterator, 3);
		sa = new lib.distribution.rmi.IteratorRMISourceAdapter(iteratorTA, iterator, 3);
		return sa;
	}

	public HealthUnit searchHealthUnit(int healthUnitCode) throws ObjectNotFoundException,
			RepositoryException {
		return fCid.searchHealthUnit(healthUnitCode);
	}

	public IteratorDsk getHealthUnitList() throws RepositoryException, ObjectNotFoundException,
			TransactionException, java.rmi.RemoteException {
		lib.distribution.rmi.IteratorRMISourceAdapter sa = null;
		lib.util.LocalIterator iterator = (lib.util.LocalIterator) fCid.getHealthUnitList();
		lib.distribution.rmi.IteratorRMITargetAdapter iteratorTA = new lib.distribution.rmi.IteratorRMITargetAdapter(
				iterator, 3);
		sa = new lib.distribution.rmi.IteratorRMISourceAdapter(iteratorTA, iterator, 3);
		return sa;
	}

	public IteratorDsk getPartialHealthUnitList() throws RepositoryException,
			ObjectNotFoundException, TransactionException, java.rmi.RemoteException {
		lib.distribution.rmi.IteratorRMISourceAdapter sa = null;
		lib.util.LocalIterator iterator = (lib.util.LocalIterator) fCid.getPartialHealthUnitList();
		lib.distribution.rmi.IteratorRMITargetAdapter iteratorTA = new lib.distribution.rmi.IteratorRMITargetAdapter(
				iterator, 3);
		sa = new lib.distribution.rmi.IteratorRMISourceAdapter(iteratorTA, iterator, 3);
		return sa;
	}

	public int insertComplaint(Complaint complaint) throws RepositoryException,
			ObjectAlreadyInsertedException, ObjectNotValidException, TransactionException,
			java.rmi.RemoteException {
		return fCid.insertComplaint(complaint);
	}

	public Employee searchEmployee(String login) throws ObjectNotFoundException,
			TransactionException {
		return fCid.searchEmployee(login);
	}

	public IteratorDsk getComplaintList() throws ObjectNotFoundException, TransactionException {
		return fCid.getComplaintList();
	}

	public void insert(Employee employee) throws ObjectAlreadyInsertedException,
			ObjectNotValidException, InsertEntryException, TransactionException {
		fCid.insert(employee);
	}

	public void updateEmployee(Employee employee) throws TransactionException, RepositoryException,
			ObjectNotFoundException, ObjectNotValidException, UpdateEntryException {
		fCid.update(employee);
	}
}