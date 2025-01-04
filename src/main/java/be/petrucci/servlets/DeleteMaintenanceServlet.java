package be.petrucci.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.User;

public class DeleteMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1222210774647623591L;

	public DeleteMaintenanceServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserPuEmp(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
		int id = Integer.parseInt(request.getParameter("id"));
		if(id == 0) {
			request.setAttribute("fail", "Error no valid maintenance chosen for deletion!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			return;
		}
		Maintenance maintenance = new Maintenance();
		maintenance.setId(id);
		if(maintenance.deleteMaintenance()) {
			request.setAttribute("success", "Maintenance successfuly deleted!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			return;
		}
		else {
			request.setAttribute("fail", "Error about machine deletion!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			return;
		}
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
