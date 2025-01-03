package be.petrucci.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.MaintenanceWorker;
import be.petrucci.javabeans.User;
import be.petrucci.javabeansviews.ListMachineWorker;

public class AddMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = -720626763990810887L;

	public AddMaintenanceServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserMMana(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
		ListMachineWorker list = new ListMachineWorker();
		request.setAttribute("model", list);
		getServletContext().getRequestDispatcher("/WEB-INF/Views/AddMaintenance.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserMMana(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
		List<String> errors = new ArrayList<>();
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			MaintenanceManager manager = new MaintenanceManager();
		    manager.setId(user.getId());
	        manager.setLastname(user.getLastname());
	        manager.setFirstname(user.getFirstname());
	        manager.setAge(user.getAge());
	        manager.setAddress(user.getAddress());
	        manager.setMatricule(user.getMatricule());
	        manager.setPassword(user.getPassword());
	        
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
	
	private boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        User user = (User) session.getAttribute("user");
        return user != null;
    }
	
	private boolean isUserAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        User user = (User) session.getAttribute("user");
        return user.isRole("Admin");
    }
	
	private boolean isUserMMana(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        User user = (User) session.getAttribute("user");
        return user.isRole("MMana");
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
    }

}
