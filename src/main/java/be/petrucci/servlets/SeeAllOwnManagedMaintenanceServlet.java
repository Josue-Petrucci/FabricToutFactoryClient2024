package be.petrucci.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.User;

public class SeeAllOwnManagedMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = -6775443268122539382L;

	public SeeAllOwnManagedMaintenanceServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
			if (!(isUserAdmin(request) || isUserMMana(request))) {
				forwardToLogin(request, response);
				return;
			}
		}

		HttpSession session = request.getSession();
		User managerUser = (User) session.getAttribute("user");
		MaintenanceManager manager = MaintenanceManager.getManagerDetail(new MaintenanceManager(managerUser.getId()));
		manager.getMyMaintenances(Maintenance.getAllMaintenance());

		request.setAttribute("manager", manager);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/SeeAllOwnManagedMaintenance.jsp");
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

	private boolean isUserMMana(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		User user = (User) session.getAttribute("user");
		return user.isRole("MMana");
	}

	private void forwardToLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
		return;
	}
}
