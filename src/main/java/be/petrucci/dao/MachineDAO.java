package be.petrucci.dao;

import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonParseException;
import be.petrucci.javabeans.Machine;

public class MachineDAO extends DAO<Machine>{

	public MachineDAO() {
		super();
	}

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
	        ex.printStackTrace();
	        return false;
	    }
	    return false;
	}

	public boolean delete(Machine obj) {
        try {
    		ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(obj);
            ClientResponse res = this.getResource()
                    .path("machine")
                    .accept(MediaType.APPLICATION_JSON)
                    .delete(ClientResponse.class,json);
            return res.getStatus() == 200;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
	}

	public boolean update(Machine obj) {
		try {
			String json = new ObjectMapper().writeValueAsString(obj);
			ClientResponse res = this
				.getResource()
				.path("machine")
				.type(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, json);
			return res.getStatus() == 200;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Machine find(Machine obj) {
		return null;
	}

	public ArrayList<Machine> findAll() {
		String responseJSON = this.getResource()
				.path("machine")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		ArrayList<Machine> machineList = new ArrayList<Machine>();
		JSONArray array = new JSONArray(responseJSON);
		ObjectMapper mapper = new ObjectMapper();
		for(int i = 0; i < array.length(); i++) {
			String machineJSON = array.get(i).toString();
			try {
				machineList.add(mapper.readValue(machineJSON,Machine.class));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return machineList;
	}

}
