package be.petrucci.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MaintenanceWorker;
import be.petrucci.javabeans.User;

/**
 * Servlet implementation class AddMachineWorkersToMaintenainceServlet
 */
public class AddMachineWorkersToMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMachineWorkersToMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserPuEmp(request)) {
                forwardToLogin(request, response);
                return;
        	}
		}
    	ArrayList<Machine> machines = Machine.getAllMachine();
    	ArrayList<MaintenanceWorker> workers = MaintenanceWorker.getAllWorkers();
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("listMachine", machines);
    	session.setAttribute("listWorker", workers);
    	
    	request.setAttribute("Machines", machines);
    	request.setAttribute("Workers", workers);
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/AddMachineWorkersToMaintenance.jsp");
		dispatcher.forward(request, response);
		return;
	}

	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserPuEmp(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
		
		if(request.getParameter("submit") != null) {
			String selectedMachineId = request.getParameter("selectedMachineId");
			String[] selectedWorkerIds = request.getParameterValues("selectedWorkerIds");
		    
		    if (selectedMachineId == null) {
		    	request.setAttribute("fail", "No Machine chosen!");
				doGet(request, response);
				return;
		    }
				
		    if (selectedWorkerIds == null || selectedWorkerIds.length == 0) {
		    	request.setAttribute("fail", "No Worker chosen!");
		    	doGet(request, response);
				return;
		    }
		    
		    HttpSession session = request.getSession();
		    ArrayList<Machine> machineList = (ArrayList<Machine>) session.getAttribute("listMachine");
        	ArrayList<MaintenanceWorker> workerList = (ArrayList<MaintenanceWorker>) session.getAttribute("listWorker");
        	
        	Machine machine = Machine.giveSelectedMachine(machineList, selectedMachineId);
        	ArrayList<MaintenanceWorker> workers = MaintenanceWorker.giveSelectedWorkers(workerList, selectedWorkerIds);
        	
        	
        	session.setAttribute("listMachine", null);
        	session.setAttribute("machine", machine);
        	session.setAttribute("listWorker", workers);
		    
		    response.sendRedirect(request.getContextPath() + "/AddMaintenanceServlet");
		    return;
		} else {
			request.setAttribute("fail", "Error for machine and worker selection submition!");
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
	
	private boolean isUserPuEmp(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        User user = (User) session.getAttribute("user");
        return user.isRole("PuEmp");
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
        return;
    }
}
