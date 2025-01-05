package be.petrucci.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MachineStatus;
import be.petrucci.javabeans.User;

public class UpdateMachineServlet extends HttpServlet {
	private static final long serialVersionUID = -6320739258738142234L;

	public UpdateMachineServlet() {}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!userHasAccess(request)) {
			getServletContext()
				.getRequestDispatcher("/WEB-INF/JSP/Login.jsp")
				.forward(request, response);
			return;
		}
		var id = request.getParameter("id");
		if (id == null || Integer.parseInt(id) == 0) {
			request.setAttribute("fail", "Invalid machine id!");
			getServletContext()
				.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
				.forward(request, response);
			return;
		}
		var machine = Machine.giveSelectedMachine(
			(ArrayList<Machine>)request.getSession().getAttribute("machineList"),
			id
		);
		if (machine.getStatus() == MachineStatus.OnOrder) {
			machine.setStatus(MachineStatus.Working);
		} else {
			request.setAttribute("fail", "The machine is already on site!");
			getServletContext()
				.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
				.forward(request, response);
			return;
		}
		if (!machine.updateMachine()) {
			request.setAttribute("fail", "A problem occured while updating the machine!");
			getServletContext()
				.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
				.forward(request, response);
			return;
		}
		request.setAttribute("success", "Successfully updated machine status!");
		getServletContext()
			.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
			.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean userHasAccess(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		User user = (User)session.getAttribute("user");
		return user != null && (user.isRole("Admin") || user.isRole("PuEmp"));
	}
}
