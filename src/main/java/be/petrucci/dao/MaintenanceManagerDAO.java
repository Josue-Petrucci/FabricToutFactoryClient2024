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
	        System.out.println(ex.getMessage());
	        return false;
	    }
	    return false;
	}

	public boolean delete(MaintenanceManager obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(MaintenanceManager obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public MaintenanceManager find(MaintenanceManager obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<MaintenanceManager> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
