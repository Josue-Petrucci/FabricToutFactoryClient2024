package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import be.petrucci.dao.MachineDAO;

public class Machine implements Serializable {
	private static final long serialVersionUID = -1046535624725789699L;
	private int id;
	private MachineType type;
	private double size;
	private MachineStatus status;
	private Site site;
	private ArrayList<Zone> zones;
	private ArrayList<Maintenance> maintenance = new ArrayList<Maintenance>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MachineType getType() {
		return type;
	}

	public void setType(MachineType type) {
		this.type = type;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public MachineStatus getStatus() {
		return status;
	}

	public void setStatus(MachineStatus status) {
		this.status = status;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public ArrayList<Zone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}

	public ArrayList<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(ArrayList<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}

	public Machine() {
	}

	public Machine(int id, MachineType type, double size, MachineStatus status, Site site, Zone zone) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.status = status;
		this.site = site;
		this.zones = new ArrayList<Zone>();
		addZone(zone);
	}

	public Machine(int id, MachineType type, double size, MachineStatus status, Site site, ArrayList<Zone> zones) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.status = status;
		this.site = site;
		if (zones.size() >= 1)
			this.zones = zones;
		else
			throw new IllegalArgumentException("Machine zone list must not be empty");
		this.maintenance = new ArrayList<Maintenance>();
	}

	public Machine(int id, MachineType type, double size, MachineStatus status, Site site, ArrayList<Zone> zones,
			ArrayList<Maintenance> maintenance) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.status = status;
		this.site = site;
		if (zones.size() >= 1)
			this.zones = zones;
		else
			throw new IllegalArgumentException("Machine zone list must not be empty");
		this.maintenance = maintenance;
	}

	// Methods
	public void addZone(Zone zone) {
		if (!zones.contains(zone)) {
			zones.add(zone);
		}
	}

	public boolean addMachine() {
		MachineDAO machineDAO = new MachineDAO();
		return this.size > 0 && createMachine(machineDAO);
	}

	public boolean deleteMachine() {
		MachineDAO machineDAO = new MachineDAO();
		return deleteMachine(machineDAO);
	}

	public boolean updateMachine() {
		MachineDAO machineDAO = new MachineDAO();
		return updateMachine(machineDAO);
	}

	public static Machine giveSelectedMachine(ArrayList<Machine> machineList, String id) {
		Optional<Machine> selectedMachine = machineList.stream().filter(m -> m.getId() == Integer.parseInt(id))
				.findFirst();
		return selectedMachine.get();
	}

	// DAO methods
	public boolean createMachine(MachineDAO dao) {
		return dao.create(this);
	}

	public boolean deleteMachine(MachineDAO dao) {
		return dao.delete(this);
	}

	public boolean updateMachine(MachineDAO dao) {
		return dao.update(this);
	}

	public static ArrayList<Machine> getAllMachine() {
		MachineDAO dao = new MachineDAO();
		return dao.findAll();
	}

	@Override
	public boolean equals(Object obj) {
		Machine m = null;
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		m = (Machine) obj;
		return m.getId() == this.getId() && m.getSite().getName().equals(this.getSite().getName());
	}

	@Override
	public int hashCode() {
		return this.hashCode();
	}

	@Override
	public String toString() {
		return "Machine [id=" + id + ", type=" + type + ", size=" + size + ", status=" + status + ", " + site + ", "
				+ this.zones + "]";
	}
}
