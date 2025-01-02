package be.petrucci.dao;

import java.util.ArrayList;

import org.json.JSONObject;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.ClientResponse;

import be.petrucci.javabeans.User;

public class UserDAO extends DAO<User>{

	public UserDAO() {
		super();
	}

	@Override
	public boolean create(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User find(User obj) {
		JSONObject json = new JSONObject();
	    json.put("matricule", obj.getMatricule());
	    json.put("password", obj.getPassword());

		try {
			 ClientResponse res = this.getResource()
	 	                .path("user")
	 	                .type(MediaType.APPLICATION_JSON)
	 	                .accept(MediaType.APPLICATION_JSON)
	 	                .post(ClientResponse.class, json.toString());
			
			if (res.getStatus() == 200) {
				String response = res.getEntity(String.class);
				JSONObject responseJson = new JSONObject(response);
				int id = responseJson.getInt("id");
				String firstname = responseJson.getString("firstname");
				String lastname = responseJson.getString("lastname");
				int age = responseJson.getInt("age");
				String address = responseJson.getString("address");
				User user = new User(id, lastname, firstname, age, address,
						obj.getMatricule(), obj.getPassword());
				return user;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
