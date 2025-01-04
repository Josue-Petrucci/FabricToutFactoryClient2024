package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import be.petrucci.dao.MaintenanceManagerDAO;

public class MaintenanceManager extends User implements Serializable{
	private static final long serialVersionUID = -9163803205862632545L;
	private Site site;
	private ArrayList<Maintenance> maintenance = new ArrayList<Maintenance>();
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public ArrayList<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(ArrayList<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}

	public MaintenanceManager() {}
	
	public MaintenanceManager(int id, String lastname, String firstname, int age,
			String address, String matricule, String password, Site site) {
		super(id, lastname, firstname, age, address, matricule, password);
		this.site = site;
		this.maintenance = new ArrayList<Maintenance>();
	}
	
	public MaintenanceManager(int id, String lastname, String firstname, int age,
			String address, String matricule, String password, Site site,
			ArrayList<Maintenance> maintenanceList) {
		super(id, lastname, firstname, age, address, matricule, password);
		this.site = site;
		this.maintenance = maintenanceList;
	}
	
	//Methods
	public boolean addMaintenanceManager() {
		MaintenanceManagerDAO maintenanceManagerDAO = new MaintenanceManagerDAO();
		
		return paramsAreValid() && createMaintenanceManager(maintenanceManagerDAO);
	}
	
	private boolean paramsAreValid() {
	    if (this.getLastname() == null || this.getLastname().trim().length() < 3) {
	        return false;
	    }
	    if (this.getFirstname() == null || this.getFirstname().trim().length() < 3) {
	        return false;
	    }
	    if (this.getAge() < 18) {
	        return false;
	    }
	    if (this.getAddress() == null || this.getAddress().trim().length() < 3) {
	        return false;
	    }
	    if (this.getMatricule() == null || 
	        (!this.getMatricule().startsWith("Admin-") &&
	         !this.getMatricule().startsWith("MMana-") &&
	         !this.getMatricule().startsWith("MWork-") &&
	         !this.getMatricule().startsWith("PuEmp-"))) {
	        return false;
	    }
	    if (this.getPassword() == null || this.getPassword().length() < 5 || 
	        !this.getPassword().matches("^[A-Z]+$")) {
	        return false;
	    }
	    if (this.getSite() == null) {
	        return false;
	    }
	    return true;
	}

	
	//DAO methods
	public boolean createMaintenanceManager(MaintenanceManagerDAO dao) {
		return dao.create(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maintenance, site);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaintenanceManager other = (MaintenanceManager) obj;
		return Objects.equals(maintenance, other.maintenance) && Objects.equals(site, other.site);
	}	
}
