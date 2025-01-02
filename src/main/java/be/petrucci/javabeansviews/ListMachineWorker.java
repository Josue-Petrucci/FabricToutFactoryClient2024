package be.petrucci.javabeansviews;

import java.io.Serializable;
import java.util.ArrayList;

import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MaintenanceWorker;

public class ListMachineWorker implements Serializable {
	private static final long serialVersionUID = 9069598657528681293L;
	private ArrayList<Machine> listMachine = Machine.getAllMachine();
	private ArrayList<MaintenanceWorker> listWorker = MaintenanceWorker.getAllWorkers();
	
	public ArrayList<Machine> getListMachine() {
		return listMachine;
	}
	
	public void setListMachine(ArrayList<Machine> listMachine) {
		this.listMachine = listMachine;
	}
	
	public ArrayList<MaintenanceWorker> getListWorker() {
		return listWorker;
	}
	
	public void setListWorker(ArrayList<MaintenanceWorker> listWorker) {
		this.listWorker = listWorker;
	}

	public ListMachineWorker() {}
}
