package be.petrucci.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceStatus;
import be.petrucci.javabeans.User;

public class CompleteMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = -8433305872201971142L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!userHasAccess(request)) {
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
			return;
		}

		try {
			var id = Integer.parseInt(request.getParameter("id"));
			if (id == 0) {
				request.setAttribute("fail", "Invalid maintenance id!");
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
				return;
			}

			var maintenances = (ArrayList<Maintenance>) request.getSession().getAttribute("maintenanceList");
			var maintenance = Maintenance.giveSelectedMaintenance(maintenances, id);
			if (maintenance.getStatus() == MaintenanceStatus.Validated) {
				request.setAttribute("fail", "Maintenance was already validated!");
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
				return;
			}

			request.setAttribute("maintenance", maintenance);

			getServletContext().getRequestDispatcher("/WEB-INF/JSP/CompleteMaintenance.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("fail", "Invalid maintenance id!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!userHasAccess(request)) {
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
			return;
		}
		try {
			var id = Integer.parseInt(request.getParameter("id"));
			if (id == 0) {
				request.setAttribute("fail", "Invalid maintenance id!");
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
				return;
			}

			var maintenances = (ArrayList<Maintenance>) request.getSession().getAttribute("maintenanceList");
			var maintenance = Maintenance.giveSelectedMaintenance(maintenances, id);
			if (maintenance.getStatus() == MaintenanceStatus.Validated) {
				request.setAttribute("fail", "Maintenance was already validated!");
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
				return;
			}
			String report = request.getParameter("report");
			if (report == null || report.isBlank()) {
				request.setAttribute("fail", "Report cannot be empty!");
				request.setAttribute("maintenance", maintenance);
				doGet(request, response);
				return;
			}
			if (report.length() > 200) {
				request.setAttribute("fail", "Report is too big!");
				request.setAttribute("maintenance", maintenance);
				doGet(request, response);
				return;
			}
			maintenance.setReport(report);
			maintenance.setStatus(MaintenanceStatus.Completed);
			if (maintenance.updateMaintenance()) {
				request.setAttribute("success", "Maintenance report has been submitted!");
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			} else {
				request.setAttribute("fail", "Something went wrong while submitting the maintenance report!");
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("fail", "Invalid maintenance id!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
		}
	}

	private boolean userHasAccess(HttpServletRequest request) {
		var session = request.getSession(false);
		if (session == null) {
			return false;
		}
		var user = (User) session.getAttribute("user");
		return user != null && (user.isRole("MWork"));
	}
}
