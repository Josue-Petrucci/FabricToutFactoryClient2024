package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

import be.petrucci.dao.MaintenanceWorkerDAO;


public class MaintenanceWorker extends User implements Serializable{
	private static final long serialVersionUID = -4939100544238173021L;
	private Site site;
	private ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public ArrayList<Maintenance> getMaintenance() {
		return maintenances;
	}

	public void setMaintenances(ArrayList<Maintenance> maintenance) {
		this.maintenances = maintenance;
	}
	
	public MaintenanceWorker() {}
	
	public MaintenanceWorker(int id, String lastname, String firstname, int age, String address, String matricule, String password, Site site) {
		super(id, lastname, firstname, age, address, matricule, password);
		this.site = site;
	}
	
	//Methods
	public void addMaintenance(Maintenance maintenance) {
		if(!maintenances.contains(maintenance)) {
			maintenances.add(maintenance);
		}
	}
	
	public static ArrayList<MaintenanceWorker> giveSelectedWorkers(ArrayList<MaintenanceWorker> workerList, String[] ids){
		ArrayList<MaintenanceWorker> selectedWorker = new ArrayList<>();
	    for (String workerId : ids) {
	        int id = Integer.parseInt(workerId);
	        MaintenanceWorker matchingWorker = workerList.stream()
	                                    .filter(w -> w.getId() == id)
	                                    .findFirst()
	                                    .orElse(null);
	        if (matchingWorker != null) {
	        	selectedWorker.add(matchingWorker);
	        }
	    }
	    return selectedWorker;
	}

	public void getInProgressMaintenances(ArrayList<Maintenance> maintenanceList) {
		setMaintenances(new ArrayList<Maintenance>(maintenanceList
			.stream()
			.filter(m -> {
				if (m.getStatus() == MaintenanceStatus.Validated) {
					return false;
				}
				for (var w : m.getWorkers()) {
					if (w.getId() == this.getId()) {
						return true;
					}
				}
				return false;
			})
			.collect(Collectors.toList())));
	}

	//DAO methods
	public static ArrayList<MaintenanceWorker> getAllWorkers(){
		MaintenanceWorkerDAO dao = new MaintenanceWorkerDAO();
		return dao.findAll();
	}
	
	@Override
	public boolean equals(Object obj) {
		MaintenanceWorker w = null;
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		w = (MaintenanceWorker)obj;
		return w.getFirstname().equals(this.getFirstname()) && w.getLastname().equals(this.getLastname());
	}
	
	@Override
	public int hashCode() {
		return this.getFirstname().hashCode() + this.getLastname().hashCode();
	}
}
