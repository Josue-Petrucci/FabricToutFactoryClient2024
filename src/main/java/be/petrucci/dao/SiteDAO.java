package be.petrucci.dao;

import java.io.IOException;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.petrucci.javabeans.Site;

public class SiteDAO extends DAO<Site> {

	public SiteDAO() {
		super();
	}

	public boolean create(Site obj) {
		return false;
	}

	public boolean delete(Site obj) {
		return false;
	}

	public boolean update(Site obj) {
		return false;
	}

	public Site find(Site obj) {
		return null;
	}

	public ArrayList<Site> findAll() {
		String responseJSON = this.getResource().path("site").accept(MediaType.APPLICATION_JSON).get(String.class);
		ArrayList<Site> siteList = new ArrayList<Site>();
		JSONArray array = new JSONArray(responseJSON);
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < array.length(); i++) {
			String siteJSON = array.get(i).toString();
			try {
				siteList.add(mapper.readValue(siteJSON, Site.class));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return siteList;
	}
}
