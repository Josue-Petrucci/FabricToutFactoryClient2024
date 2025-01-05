package be.petrucci.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.Site;
import be.petrucci.javabeans.User;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = -3721229763804389146L;

	public RegisterServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
			if (!isUserAdmin(request)) {
				forwardToLogin(request, response);
				return;
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/Register.jsp");
		dispatcher.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
			if (!isUserAdmin(request)) {
				forwardToLogin(request, response);
				return;
			}
		}
		HttpSession session = request.getSession();
		Site site = (Site) session.getAttribute("site");

		if (site == null) {
			request.setAttribute("fail", "Selected site is not present!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			return;
		}

		MaintenanceManager manager = new MaintenanceManager(0, request.getParameter("lastname"),
				request.getParameter("firstname"), Integer.parseInt(request.getParameter("age")),
				request.getParameter("address"), request.getParameter("matricule"), request.getParameter("password"),
				site);

		if (!manager.addMaintenanceManager()) {
			request.setAttribute("fail", "Registration error for a new user!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			return;
		}

		session.setAttribute("site", null);

		request.setAttribute("success", "Registration successful for the new user! ");
		getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
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

	private void forwardToLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
		return;
	}
}
