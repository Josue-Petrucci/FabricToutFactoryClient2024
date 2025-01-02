package be.petrucci.dao;

import java.io.IOException;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.petrucci.javabeans.Factory;

public class FactoryDAO extends DAO<Factory>{

	public FactoryDAO() {
		super();
	}

	@Override
	public boolean create(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Factory find(Factory obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Factory> findAll() {
		String responseJSON = this.getResource()
				.path("factory")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		ArrayList<Factory> factoryList = new ArrayList<Factory>();
		JSONArray array = new JSONArray(responseJSON);
		ObjectMapper mapper = new ObjectMapper();
		for(int i =0;i<array.length();i++) {
			String factoryJSON = array.get(i).toString();
			try {
				factoryList.add(mapper.readValue(factoryJSON,Factory.class));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return factoryList;
	}

}
