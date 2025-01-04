package be.petrucci.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import be.petrucci.javabeans.Site;
import be.petrucci.javabeans.User;

public class AddSiteToRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 5463397373344655314L;

	public AddSiteToRegisterServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request)) {
                forwardToLogin(request, response);
                return;
        	}
		}
    	ArrayList<Site> siteList = Site.getAllSites();
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("siteList", siteList);
    	
    	request.setAttribute("Sites", siteList);
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/AddSiteToRegister.jsp");
		dispatcher.forward(request, response);
		return;
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserLoggedIn(request)) {
        	if(!isUserAdmin(request)) {
                forwardToLogin(request, response);
                return;
        	}
		}
		if(request.getParameter("submit") != null) {
			String selectedSiteId = request.getParameter("selectedSiteId");
		    
		    if (selectedSiteId == null) {
		    	request.setAttribute("fail", "No Site chosen!");
				doGet(request, response);
				return;
		    }
		    
		    HttpSession session = request.getSession();
			ArrayList<Site> siteList = (ArrayList<Site>) session.getAttribute("siteList");
        	
        	Site site = Site.giveSelectedSite(siteList, selectedSiteId);
        	
        	session.setAttribute("siteList", null);
        	session.setAttribute("site", site);
		    
		    response.sendRedirect(request.getContextPath() + "/RegisterServlet");
		    return;
		} else {
			request.setAttribute("fail", "Error for site selection submission!");
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
	
	private void forwardToLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
        return;
    }
}
