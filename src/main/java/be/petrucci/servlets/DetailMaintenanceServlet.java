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
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.User;

/**
 * Servlet implementation class DetailMaintenance
 */
public class DetailMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserPuEmp(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
		
		int id = Integer.parseInt(request.getParameter("id"));
		if(id == 0) {
			request.setAttribute("fail", "Error no valid machine chosen for deletion!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			return;
		}
		
		HttpSession session = request.getSession();
		ArrayList<Maintenance> maintenances = (ArrayList<Maintenance>) session.getAttribute("maintenanceList");
		Maintenance maintenance = Maintenance.giveSelectedMaintenance(maintenances, id);
		
		request.setAttribute("maintenance", maintenance);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/DetailMaintenance.jsp");
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
