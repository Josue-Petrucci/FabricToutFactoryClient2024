package be.petrucci.dao;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

import be.petrucci.javabeans.MaintenanceManager;

public class MaintenanceManagerDAO extends DAO<MaintenanceManager> {

	public MaintenanceManagerDAO() {}

	public boolean create(MaintenanceManager obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(obj);

            ClientResponse res = this.getResource()
                    .path("manager")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, json);
	        if (res.getStatus() == 201) {
	            return true;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	    }
	    return false;
	}

	public boolean delete(MaintenanceManager obj) {
		return false;
	}

	public boolean update(MaintenanceManager obj) {
		return false;
	}

	public MaintenanceManager find(MaintenanceManager obj) {
		return null;
	}

	public ArrayList<MaintenanceManager> findAll() {
		return null;
	}
}
