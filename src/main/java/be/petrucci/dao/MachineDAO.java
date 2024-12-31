package be.petrucci.dao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.sun.jersey.api.client.ClientResponse;
import javax.ws.rs.core.MediaType;

import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.Zone;

public class MachineDAO extends DAO<Machine>{

	public MachineDAO() {
		super();
	}

	@Override
	public boolean create(Machine obj) {
		try {
	        JSONArray machinesArray = new JSONArray();

	        JSONObject machineJson = new JSONObject();
	        machineJson.put("id", obj.getId());
	        machineJson.put("type", obj.getType().toString());
	        machineJson.put("size", obj.getSize());
	        machineJson.put("status", obj.getStatus().toString());
	        
	        JSONObject siteJson = new JSONObject();
	        siteJson.put("id", obj.getSite().getId());
	        siteJson.put("name", obj.getSite().getName());
	        siteJson.put("city", obj.getSite().getCity());

	        JSONObject factoryJson = new JSONObject();
	        factoryJson.put("id", obj.getSite().getFactory().getId());
	        factoryJson.put("name", obj.getSite().getFactory().getName());
	        factoryJson.put("sites", JSONObject.NULL);
	        siteJson.put("factory", factoryJson);

	        siteJson.put("zones", new JSONArray());
	        siteJson.put("machines", new JSONArray());
	        siteJson.put("listMaintenanceManagers", new JSONArray());
	        siteJson.put("listMaintenanceWorkers", new JSONArray());

	        machineJson.put("site", siteJson);

	        JSONArray zonesArray = new JSONArray();
	        for (Zone zone : obj.getZones()) {
	            JSONObject zoneJson = new JSONObject();
	            zoneJson.put("id", zone.getId());
	            zoneJson.put("zoneLetter", zone.getZoneLetter());
	            zoneJson.put("dangerLevel", zone.getDangerLevel());

	            JSONObject zoneSiteJson = new JSONObject(siteJson.toString());
	            zoneJson.put("site", zoneSiteJson);

	            zoneJson.put("machines", new JSONArray());
	            zonesArray.put(zoneJson);
	        }
	        machineJson.put("zones", zonesArray);
	        machinesArray.put(machineJson);

	        ClientResponse res = this.getResource()
	                .path("machine")
	                .type(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .post(ClientResponse.class, machinesArray.toString());

	        if (res.getStatus() == 201) {
	            String response = res.getEntity(String.class);
	            JSONObject jsonResponse = new JSONObject(response);
	            obj.setId(jsonResponse.getInt("id"));
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
		// TODO Auto-generated method stub
		return null;
	}

}
