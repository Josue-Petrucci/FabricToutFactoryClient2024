package be.petrucci.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.MaintenanceStatus;
import be.petrucci.javabeans.User;

public class UpdateMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 9184600316851950282L;

	public UpdateMaintenanceServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!userHasAccess(request)) {
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
			return;
		}

		try {
			int id = Integer.parseInt(request.getParameter("id"));
			if (id == 0) {
				request.setAttribute("fail", "Invalid maintenance id!");
				sendHome(request, response);
				return;
			}
			var managerUser = (User) request.getSession().getAttribute("user");
			var manager = new MaintenanceManager();
			manager.setId(managerUser.getId());
			manager.getMyMaintenances(Maintenance.getAllMaintenance());
			var maintenance = Maintenance.giveSelectedMaintenance(manager.getMaintenance(), id);
			if (maintenance == null) {
				request.setAttribute("fail", "No such maintenance!");
				sendHome(request, response);
				return;
			}
			if (maintenance.getStatus() != MaintenanceStatus.Completed) {
				request.setAttribute("fail",
						"Cannot validate a maintenance with '" + String.valueOf(maintenance.getStatus()) + "' status!");
				sendHome(request, response);
				return;
			}
			String action = request.getParameter("action");
			if (action.equals("accept")) {
				maintenance.setStatus(MaintenanceStatus.Validated);
			} else if (action.equals("reject")) {
				maintenance.setStatus(MaintenanceStatus.Pending);
			} else {
				request.setAttribute("fail", "Unknown action!");
				sendHome(request, response);
				return;
			}
			if (maintenance.updateMaintenance()) {
				request.setAttribute("success", "Successfully update maintenance!");
			} else {
				request.setAttribute("fail", "Something went wrong while updating the maintenance!");
			}
			sendHome(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute("fail", "Invalid maintenance id!");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean userHasAccess(HttpServletRequest request) {
		var session = request.getSession(false);
		if (session == null) {
			return false;
		}
		var user = (User) session.getAttribute("user");
		return user != null && (user.isRole("MMana"));
	}

	private void sendHome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
	}
}
