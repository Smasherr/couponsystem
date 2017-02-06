package il.moran.couponsystem.client.web.webServices;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(description = "LoginFilter-check login & session still active", urlPatterns = { "/LoginFilter" })


public class LoginFilter implements Filter {
	private static final String FACADE_PARAMETER = "facade";
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String url = httpRequest.getRequestURL().toString();
		
		if( httpRequest.getSession().getAttribute(FACADE_PARAMETER) == null&& url.indexOf("/login")== -1  ){
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		else{
			chain.doFilter(request, response);
		}}
		// pass the request along the filter chain
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
