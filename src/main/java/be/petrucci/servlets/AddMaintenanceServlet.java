package be.petrucci.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/AddMaintenance.jsp");
		dispatcher.forward(request, response);
		return;
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserMMana(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
		
		 new ArrayList<>();
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
	        
	        String dateParam = request.getParameter("date");
	        String durationParam = request.getParameter("duration");
	        String instruction = request.getParameter("instruction");

	        List<String> errors = Maintenance.validate(dateParam, durationParam, instruction);

	        if (!errors.isEmpty()) {
	            request.setAttribute("fail", errors);
	            doGet(request, response);
	            return;
	        }
	        
	        Machine machine = (Machine) session.getAttribute("machine");
	        ArrayList<MaintenanceWorker> workers = ( ArrayList<MaintenanceWorker>) session.getAttribute("listWorker");
	        
		    Maintenance maintenance = new Maintenance(Date.valueOf(LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
		    		Integer.parseInt(durationParam), instruction, machine, manager, workers);
		    
		    session.setAttribute("machine", null);
        	session.setAttribute("listWorker", null);
		    
		    if(maintenance.createMaintenance()) {
		    	request.setAttribute("success", "Maintenance successful create !!");
	        	getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
				return;
		    } else {
		    	request.setAttribute("fail", "Error with the connection of the data base !!");
	        	getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
				return;
		    }
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("fail", "Error for addition maintenance submition!");
	        getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			return;
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
