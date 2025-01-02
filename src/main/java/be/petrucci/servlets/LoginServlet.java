package be.petrucci.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.petrucci.javabeans.User;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1123628689043456581L;

	public LoginServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("submit") != null) {
 			User user = User.login(new User(0,null,null,0,null,
 					request.getParameter("matricule"), 
 					request.getParameter("password")));
 			if(user != null) {
 				HttpSession session = request.getSession();
 				if(session.isNew() == false) {
 					session.invalidate();
                    session = request.getSession();
                    session.setAttribute("user", user);
                    request.setAttribute("success", "Welcome employee : " + user.getMatricule());
                    getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
 				}
 			}
 			else {
 				request.setAttribute("fail", "Matricule or password incorrect or not found!");
 				getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
 			}
		}
		else {
			request.setAttribute("fail", "Matricule or password incorrect or not found!");
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
		}
	}
}
