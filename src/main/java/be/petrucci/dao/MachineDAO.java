package be.petrucci.dao;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;

import be.petrucci.javabeans.Machine;

public class MachineDAO extends DAO<Machine>{

	public MachineDAO() {
		super();
	}

	@Override
	public boolean create(Machine obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(obj);

            ClientResponse res = this.getResource()
                    .path("machine")
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

	@Override
	public boolean delete(Machine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Machine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Machine find(Machine obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Machine> findAll() {
		ArrayList<Machine> machines = new ArrayList<Machine>();
		try {
			String response = getResource()
					.path("machine")
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			
			machines = getMapper().readValue(response, new TypeReference<ArrayList<Machine>>() {});
		}catch (Exception e) {
			e.printStackTrace();
		}
		return machines;
	}

}
