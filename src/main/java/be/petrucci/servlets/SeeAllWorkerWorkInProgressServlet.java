package be.petrucci.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.User;

public class SeeAllWorkerWorkInProgressServlet extends HttpServlet {
	private static final long serialVersionUID = -4098968193181966080L;
	
	public SeeAllWorkerWorkInProgressServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!userHasAccess(request)) {
			getServletContext()
				.getRequestDispatcher("/WEB-INF/JSP/Login.jsp")
				.forward(request, response);
			return;
		}
		var session = request.getSession();
		var user = (User)session.getAttribute("user");
		var workerMaintenances = Maintenance.getInProgressMaintenanceByWorker(
			Maintenance.getAllMaintenance(),
			user
		);
		session.setAttribute("maintenanceList", workerMaintenances);
		request.setAttribute("maintenanceList", workerMaintenances);
		getServletContext()
			.getRequestDispatcher("/WEB-INF/JSP/SeeAllWorkerWorkInProgress.jsp")
			.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean userHasAccess(HttpServletRequest request) {
		var session = request.getSession(false);
		if (session == null) {
			return false;
		}
		var user = (User)session.getAttribute("user");
		return user != null && (user.isRole("MWork") || user.isRole("Admin"));
	}
}
