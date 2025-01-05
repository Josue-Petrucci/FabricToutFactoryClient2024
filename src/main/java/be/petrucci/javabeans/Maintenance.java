package be.petrucci.javabeans;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import be.petrucci.dao.MaintenanceDAO;

public class Maintenance implements Serializable {
	private static final long serialVersionUID = 6578454947386542915L;
	private int id;
	private Date date;
	private int duration;
	private String instructions;
	private String report;
	private MaintenanceStatus status;
	private MaintenanceManager manager;
	private ArrayList<MaintenanceWorker> workers;
	private Machine machine;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public MaintenanceStatus getStatus() {
		return status;
	}

	public void setStatus(MaintenanceStatus status) {
		this.status = status;
	}

	public MaintenanceManager getManager() {
		return manager;
	}

	public void setManager(MaintenanceManager manager) {
		this.manager = manager;
	}

	public ArrayList<MaintenanceWorker> getWorkers() {
		return workers;
	}

	public void setWorker(ArrayList<MaintenanceWorker> workers) {
		this.workers = workers;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public Maintenance() {
	}

	public Maintenance(int id, Date date, int duration, String instruction, String report, MaintenanceStatus status) {
		this.id = id;
		this.date = date;
		this.duration = duration;
		this.instructions = instruction;
		this.report = report;
		this.status = status;
	}

	public Maintenance(int id, Date date, int duration, String instructions, String report, MaintenanceStatus status,
			MaintenanceManager manager, MaintenanceWorker worker, Machine machine) {
		this(id, date, duration, instructions, report, status);
		this.manager = manager;
		this.machine = machine;
		addWorker(worker);
	}

	public Maintenance(Date date, int duration, String instruction, Machine machine, MaintenanceManager manager,
			ArrayList<MaintenanceWorker> workers) {
		this.date = date;
		this.duration = duration;
		this.instructions = instruction;
		this.status = MaintenanceStatus.Pending;
		this.machine = machine;
		this.manager = manager;
		this.workers = workers;
	}

	public Maintenance(Date date, int duration, String instruction, String report, MaintenanceStatus status,
			Machine machine, MaintenanceManager manager, ArrayList<MaintenanceWorker> workers, int id) {
		this.date = date;
		this.duration = duration;
		this.instructions = instruction;
		this.report = report;
		this.status = status;
		this.machine = machine;
		this.manager = manager;
		this.workers = workers;
		this.id = id;
	}

	// Methods
	public void addWorker(MaintenanceWorker worker) {
		if (!workers.contains(worker)) {
			workers.add(worker);
		}
	}

	public static List<String> validate(String dateParam, String durationParam, String instructionParam) {
		List<String> errors = new ArrayList<>();

		Date date = null;
		if (dateParam == null || dateParam.trim().isEmpty()) {
			errors.add("Date is required.");
		} else {
			try {
				date = Date.valueOf(LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				if (date.before(Date.valueOf(LocalDate.now()))) {
					errors.add("The date cannot be in the past.");
				}
			} catch (DateTimeParseException e) {
				errors.add("Invalid date format. Use yyyy-MM-dd.");
			}
		}

		if (durationParam == null || durationParam.trim().isEmpty()) {
			errors.add("Duration is required.");
		} else {
			try {
				int duration = Integer.parseInt(durationParam);
				if (duration <= 0) {
					errors.add("Duration must be a positive number.");
				}
			} catch (NumberFormatException e) {
				errors.add("Invalid duration. It must be a number.");
			}
		}

		if (instructionParam == null || instructionParam.trim().isEmpty()) {
			errors.add("Instructions are required.");
		} else if (!instructionParam.matches("[a-zA-Z0-9\\s!@#$%^&*(),.?\":{}|<>]+")) {
			errors.add("Instructions contain invalid characters.");
		}
		return errors;
	}

	public static Maintenance giveSelectedMaintenance(ArrayList<Maintenance> maintenanceList, int id) {
		Optional<Maintenance> selectedMaintenance = maintenanceList.stream().filter(m -> m.getId() == id).findFirst();
		return selectedMaintenance.get();
	}

	public boolean deleteMaintenance() {
		MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
		return deleteMaintenance(maintenanceDAO);
	}

	// DAO methods
	public boolean createMaintenance() {
		MaintenanceDAO dao = new MaintenanceDAO();
		return dao.create(this);
	}

	public boolean deleteMaintenance(MaintenanceDAO dao) {
		return dao.delete(this);
	}

	public static ArrayList<Maintenance> getAllMaintenance() {
		MaintenanceDAO dao = new MaintenanceDAO();
		return dao.findAll();
	}

	public boolean updateMaintenance() {
		var dao = new MaintenanceDAO();
		return updateMaintenance(dao);
	}

	public boolean updateMaintenance(MaintenanceDAO dao) {
		return dao.update(this);
	}

	@Override
	public boolean equals(Object obj) {
		Maintenance m = null;
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		m = (Maintenance) obj;
		return m.getId() == this.getId();
	}

	@Override
	public int hashCode() {
		return this.getInstructions().hashCode();
	}
}
