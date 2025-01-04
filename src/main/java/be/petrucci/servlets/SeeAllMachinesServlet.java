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
import be.petrucci.javabeans.User;

public class SeeAllMachinesServlet extends HttpServlet {
	private static final long serialVersionUID = -8416361672282156610L;

	public SeeAllMachinesServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserPuEmp(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
        ArrayList<Machine> machineList = Machine.getAllMachine();
        
        HttpSession session = request.getSession();
        session.setAttribute("machineList", machineList);
        
		request.setAttribute("Machines", machineList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/SeeAllMachines.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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

