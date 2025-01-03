package be.petrucci.dao;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.fasterxml.jackson.core.type.TypeReference;

import be.petrucci.javabeans.Maintenance;

public class MaintenanceDAO extends DAO<Maintenance>{

	public MaintenanceDAO() {
		super();
	}

	@Override
	public boolean create(Maintenance obj) {
		try {
	        String json = getMapper().writeValueAsString(obj);
	       
	        ClientConfig config = new ClientConfig().register(JacksonFeature.class);
	        Client client = ClientBuilder.newClient(config);

	        WebTarget target = client.target(getResource().getURI()).path("maintenance");
	        Response response = target
	        		.request(MediaType.APPLICATION_JSON) 
	                .post(Entity.entity(json, MediaType.APPLICATION_JSON)); 

	        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
	            return true;
	        } else {
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean delete(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Maintenance find(Maintenance obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Maintenance> findAll() {
		ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
		try {
			String response = getResource()
					.path("maintenance")
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			
			maintenances = getMapper().readValue(response, new TypeReference<ArrayList<Maintenance>>() {});
		}catch (Exception e) {
			e.printStackTrace();
		}
		return maintenances;
	}

}
