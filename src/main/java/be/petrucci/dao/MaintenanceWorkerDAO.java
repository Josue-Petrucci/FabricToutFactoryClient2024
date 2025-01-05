package be.petrucci.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;

import be.petrucci.javabeans.MaintenanceWorker;

public class MaintenanceWorkerDAO extends DAO<MaintenanceWorker> {

	public MaintenanceWorkerDAO() {
		super();
	}

	public boolean create(MaintenanceWorker obj) {
		return false;
	}

	public boolean delete(MaintenanceWorker obj) {
		return false;
	}

	public boolean update(MaintenanceWorker obj) {
		return false;
	}

	public MaintenanceWorker find(MaintenanceWorker obj) {
		return null;
	}

	public ArrayList<MaintenanceWorker> findAll() {
		ArrayList<MaintenanceWorker> workers = new ArrayList<MaintenanceWorker>();
		try {
			String response = getResource().path("worker").accept(MediaType.APPLICATION_JSON).get(String.class);

			workers = getMapper().readValue(response, new TypeReference<ArrayList<MaintenanceWorker>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workers;
	}
}
