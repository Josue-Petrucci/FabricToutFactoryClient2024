package be.petrucci.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;

import be.petrucci.javabeans.MaintenanceWorker;

public class MaintenanceWorkerDAO extends DAO<MaintenanceWorker>{

	public MaintenanceWorkerDAO() {
		super();
	}

	@Override
	public boolean create(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MaintenanceWorker find(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MaintenanceWorker> findAll() {
		ArrayList<MaintenanceWorker> workers = new ArrayList<MaintenanceWorker>();
		try {
			String response = getResource()
					.path("worker")
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			
			workers = getMapper().readValue(response, new TypeReference<ArrayList<MaintenanceWorker>>() {});
		}catch (Exception e) {
			e.printStackTrace();
		}
		return workers;
	}
}
