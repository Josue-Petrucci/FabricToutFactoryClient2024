package be.petrucci.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.MaintenanceWorker;
import be.petrucci.javabeansviews.ListMachineWorker;

/**
 * Servlet implementation class AddMaintenanceServlet
 */
public class AddMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ListMachineWorker list = new ListMachineWorker();
		request.setAttribute("model", list);
		getServletContext().getRequestDispatcher("/WEB-INF/Views/AddMaintenance.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
		try {
			MaintenanceManager manager = new MaintenanceManager();
		    manager.setId(1);
	        manager.setLastname("Doe");
	        manager.setFirstname("John");
	        manager.setAge(45);
	        manager.setAddress("123 Manager St");
	        manager.setMatricule("MMana-1");
	        manager.setPassword("XQLIKJAJGL");
	        
	        String machineIdParam = request.getParameter("machineId");
	        String[] workerIds = request.getParameterValues("workerIds");
	        String dateParam = request.getParameter("date");
	        String durationParam = request.getParameter("duration");
	        String instruction = request.getParameter("instruction");

	        if (machineIdParam == null || machineIdParam.trim().isEmpty()) {
	            errors.add("Machine ID is required.");
	        }

	        if (workerIds == null || workerIds.length == 0) {
	            errors.add("At least one worker is required.");
	        }

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

	        int duration = 0;
	        if (durationParam == null || durationParam.trim().isEmpty()) {
	            errors.add("Duration is required.");
	        } else {
	            try {
	                duration = Integer.parseInt(durationParam);
	                if (duration <= 0) {
	                    errors.add("Duration must be a positive number.");
	                }
	            } catch (NumberFormatException e) {
	                errors.add("Invalid duration. It must be a number.");
	            }
	        }

	        if (instruction == null || instruction.trim().isEmpty()) {
	            errors.add("Instructions are required.");
	        } else if (!instruction.matches("[a-zA-Z0-9\\s!@#$%^&*(),.?\":{}|<>]+")) {
	            errors.add("Instructions contain invalid characters.");
	        }

	        if (!errors.isEmpty()) {
	            request.setAttribute("errors", errors);
	            doGet(request, response);
	            return;
	        }

	        int machineId = Integer.parseInt(machineIdParam);

	        ListMachineWorker list = new ListMachineWorker();

	        Optional<Machine> machine = list.getListMachine().stream()
	                .filter(m -> m.getId() == machineId)
	                .findFirst();

	        ArrayList<MaintenanceWorker> workers = new ArrayList<>();
	        for (String workerId : workerIds) {
	            int id = Integer.parseInt(workerId);
	            list.getListWorker().stream()
	                    .filter(w -> w.getId() == id)
	                    .findFirst()
	                    .ifPresent(workers::add);
	        }

	        if (machine.isEmpty()) {
	            errors.add("Invalid machine selected.");
	        }
	        if (workers.isEmpty()) {
	            errors.add("Selected workers are invalid.");
	        }

	        if (!errors.isEmpty()) {
	            request.setAttribute("errors", errors);
	            doGet(request, response);
	            return;
	        }
	        
		    Maintenance maintenance = new Maintenance(date, duration, instruction, machine.get(), manager, workers);
		    if(maintenance.createMaintenance()) {
		    	
		    } else {
		    	
		    }
	    } catch (Exception e) {
	        e.printStackTrace();
	        errors.add("An unexpected error occurred.");
	        request.setAttribute("errors", errors);
	        doGet(request, response);
	    }
	}

}
