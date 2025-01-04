package be.petrucci.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.petrucci.javabeans.User;
import be.petrucci.javabeans.Zone;

public class AddZoneToMachineServlet extends HttpServlet {
	private static final long serialVersionUID = 5361523981446935139L;

	public AddZoneToMachineServlet() {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserPuEmp(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
        ArrayList<Zone> zoneList = Zone.getAllZones();
        
        HttpSession session = request.getSession();
        session.setAttribute("zoneList", zoneList);
        
		request.setAttribute("Zones", zoneList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/AddZoneToMachine.jsp");
		dispatcher.forward(request, response);
    }

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request) || !isUserPuEmp(request)) {
                forwardToLogin(request, response);
                return;
        	}
        }
		
		if(request.getParameter("submit") != null) {
			String[] selectedZoneIds = request.getParameterValues("selectedZoneIds");
		    
		    if (selectedZoneIds == null || selectedZoneIds.length == 0) {
		    	request.setAttribute("fail", "No Zone chosen!");
					getServletContext()
					.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
					.forward(request, response);
					return;
		    }
		    
		    HttpSession session = request.getSession();
		    ArrayList<Zone> zoneList = (ArrayList<Zone>) session.getAttribute("zoneList");

		    ArrayList<Zone> selectedZoneList = Zone.giveSelectedZones(zoneList, selectedZoneIds);
		    
		    if(selectedZoneList == null) {
		    	request.setAttribute("fail", "Some zones are not in the same site!");
				getServletContext()
				.getRequestDispatcher("/WEB-INF/JSP/Home.jsp")
				.forward(request, response);
				return;
		    }
		    
		    session.setAttribute("zoneList", selectedZoneList);
		    
		    response.sendRedirect(request.getContextPath() + "/AddMachineServlet");
		    return;
		}
		else {
			request.setAttribute("fail", "Error for Zone selection submission!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
			return;
		}
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
