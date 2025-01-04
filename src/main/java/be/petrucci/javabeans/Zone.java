package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import be.petrucci.dao.ZoneDAO;

public class Zone implements Serializable {
	private static final long serialVersionUID = -7817333198883984982L;
	private int id;
	private char zoneLetter;
	private DangerLevel dangerLevel;
	private Site site;
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public char getZoneLetter() {
		return zoneLetter;
	}
	
	public void setZoneLetter(char zoneLetter) {
		this.zoneLetter = zoneLetter;
	}
	
	public DangerLevel getDangerLevel() {
		return dangerLevel;
	}
	
	public void setDangerLevel(DangerLevel dangerLevel) {
		this.dangerLevel = dangerLevel;
	}
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<Machine> machines) {
		this.machines = machines;
	}
	
	public Zone() {}
	
	public Zone(int id, char zoneLetter, DangerLevel dangerLevel, Site site) {
		this.id = id;
		this.zoneLetter = zoneLetter;
		this.dangerLevel = dangerLevel;
		if(site == null)
			throw new IllegalArgumentException("A zone must be linked to a site");
		else
			this.site = site;
	}
	
	//Methods
	public void addMachine(Machine machine) {
		if(!machines.contains(machine)) {
			machines.add(machine);
		}
	}

	public boolean isSameZoneSite(Zone otherZone) {
	    if (otherZone == null) {
	        return false;
	    }
	    return (this.site != null && this.site.equals(otherZone.getSite()));
	}
	
	public static boolean compareZoneListIfSameSite(ArrayList<Zone> zones) {
	    if (zones == null || zones.isEmpty()) {
	        return false;
	    }

	    for (int i = 0; i < zones.size(); i++) {
	        Zone currentZone = zones.get(i);
	        for (int j = 0; j < zones.size(); j++) {
	            if (i != j) {
	                Zone otherZone = zones.get(j);
	                if (!currentZone.isSameZoneSite(otherZone)) {
	                    return false;
	                }
	            }
	        }
	    }
	    return true;
	}
	
	public static ArrayList<Zone> giveSelectedZones(ArrayList<Zone> zoneList, String[] idsTab){
		ArrayList<Zone> selectedZones = new ArrayList<>();
	    for (String zoneId : idsTab) {
	        int id = Integer.parseInt(zoneId);
	        Zone matchingZone = zoneList.stream()
	                                    .filter(zone -> zone.getId() == id)
	                                    .findFirst()
	                                    .orElse(null);
	        if (matchingZone != null) {
	            selectedZones.add(matchingZone);
	        }
	    }
	    
	    if(!Zone.compareZoneListIfSameSite(selectedZones))
	    	return null;
	    
	    return selectedZones;
	}
	
	//DAO methods
	public static ArrayList<Zone> getAllZones() {
		ZoneDAO dao = new ZoneDAO();
		return dao.findAll();
	}
	
	@Override
	public boolean equals(Object obj) {
		Zone z = null;
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		z = (Zone)obj;
		return z.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return this.hashCode();
	}

	@Override
	public String toString() {
		return String.format(
		        "Zone [Zone Letter: %s, Danger Level: %s, %s]", 
		        zoneLetter, 
		        dangerLevel, 
		        site.toString()
		    );
	}
}
