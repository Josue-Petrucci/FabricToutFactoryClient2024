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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = -4810983948034968675L;

	public LoginFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String loginURI = req.getContextPath();

		boolean login = session != null && session.getAttribute("user") != null;
		if (login) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(loginURI);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
