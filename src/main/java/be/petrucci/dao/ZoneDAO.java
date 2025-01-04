package be.petrucci.dao;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.petrucci.javabeans.Zone;

public class ZoneDAO extends DAO<Zone>{

	public ZoneDAO() {
		super();
	}

	public boolean create(Zone obj) {
		return false;
	}

	public boolean delete(Zone obj) {
		return false;
	}

	public boolean update(Zone obj) {
		return false;
	}

	public Zone find(Zone obj) {
		return null;
	}

	public ArrayList<Zone> findAll() {
		String responseJSON = this.getResource()
				.path("zone")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		ArrayList<Zone> zoneList = new ArrayList<Zone>();
		JSONArray array = new JSONArray(responseJSON);
		ObjectMapper mapper = new ObjectMapper();
		for(int i =0;i<array.length();i++) {
			String zoneJSON = array.get(i).toString();
			try {
				zoneList.add(mapper.readValue(zoneJSON,Zone.class));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return zoneList;
	}
}
