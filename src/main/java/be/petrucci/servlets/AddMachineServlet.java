package be.petrucci.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MachineStatus;
import be.petrucci.javabeans.MachineType;
import be.petrucci.javabeans.User;
import be.petrucci.javabeans.Zone;

public class AddMachineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddMachineServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserPuEmp(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/AddMachine.jsp");
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
		
		HttpSession session = request.getSession();
	    ArrayList<Zone> zoneList = (ArrayList<Zone>) session.getAttribute("zoneList");
	    
	    if(zoneList == null) {
	    	request.setAttribute("fail", "Selected Zones are not present!");
			getServletContext()
			.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
			.forward(request, response);
			return;
	    }
	    
		MachineType type = MachineType.valueOf(request.getParameter("type"));
		double size = Double.parseDouble(request.getParameter("size"));
		
		Machine machine = new Machine(0, type, size, MachineStatus.OnOrder,
				zoneList.get(0).getSite(), zoneList);
		
		System.out.println("joe");
		if(!machine.addMachine()) {
			request.setAttribute("fail", "Error purchasing a new machine!");
			getServletContext()
			.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
			.forward(request, response);
			return;
		}
		System.out.println("mama");
		
		session.setAttribute("zoneList", null);

		request.setAttribute("success", "Success purchasing a new machine! "
				+ "Wait for arrival!");
		getServletContext()
		.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
		.forward(request, response);
		return;
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
    }
}
