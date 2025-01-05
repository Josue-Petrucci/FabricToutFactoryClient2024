package be.petrucci.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import be.petrucci.javabeans.User;

public class PuEmpFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = -9066166430889708263L;

	public PuEmpFilter() {
	}

	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);

		User user = (session != null) ? (User) session.getAttribute("user") : null;

		if (user != null && (user.isRole("PuEmp") || user.isRole("Admin"))) {
			chain.doFilter(request, response);
		} else {
			req.setAttribute("fail", "Access denied. PuEmp or Admin role required.");
			req.getRequestDispatcher("/WEB-INF/JSP/Home.jsp").forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
