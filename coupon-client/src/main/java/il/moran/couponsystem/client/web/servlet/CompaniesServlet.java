package il.moran.couponsystem.client.web.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.moran.couponsystem.client.dal.Company;
import il.moran.couponsystem.client.dbdao.CompanyDBDAO;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

/**
 * Servlet implementation class Companies
 */
@WebServlet("/CompaniesServlet")
public class CompaniesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompaniesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("<html>");
		response.getWriter().println("<body>");
		
		try {
			CompanyDBDAO companyDB = new CompanyDBDAO();
			Collection<Company> companies = companyDB.getAllCompanies();
			response.getWriter().println("<table border=\"1\">");
			response.getWriter().println("<tr>");
			response.getWriter().println("<th>ID </th>");
			response.getWriter().println("<th>Company Name</th>");
			response.getWriter().println("<th>Company Email</th>");
			response.getWriter().println("<th>Company Password</th>");
			response.getWriter().println("</tr>");
			for(Company company:companies){
				response.getWriter().println("<tr>");
				response.getWriter().println("<td>" + company.getId() + "</td>");
				response.getWriter().println("<td>" + company.getCompanyName() + "</td>");
				response.getWriter().println("<td>" + company.getEmail() + "</td>");
				response.getWriter().println("<td>" + company.getPassword() + "</td>");
				response.getWriter().println("</tr>");
			}
			response.getWriter().println("</table>");
			
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().println("<p>we have error: " + e.getMessage() + "</p>");
		} catch (ThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().println("</body>");
		response.getWriter().println("</html>");
	}

}
