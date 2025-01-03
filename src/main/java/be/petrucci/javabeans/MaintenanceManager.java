package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public MaintenanceManager(int id, String lastname, String firstname, int age, String address, String matricule, String password, Site site) {
		super(id, lastname, firstname, age, address, matricule, password);
		this.site = site;
	}
}
