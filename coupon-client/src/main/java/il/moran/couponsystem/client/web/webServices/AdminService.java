package il.moran.couponsystem.client.web.webServices;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import il.moran.couponsystem.client.client.AdminFacade;
import il.moran.couponsystem.client.dal.Company;
import il.moran.couponsystem.client.dal.Customer;
import il.moran.couponsystem.client.dbdao.CompanyDBDAO;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;
import il.moran.couponsystem.entity.Income;


@Path("/admin")
public class AdminService {

	@Context
	HttpServletRequest request;

	private static final String FACADE_PARAMETER = "facade";
	
	@Path("/viewAllIncome")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Income> viewAllIncome() {
	    AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
	    FACADE_PARAMETER);
	    return admin.viewAllIncome();
	    
	}

    @Path("/viewIncomeByCustomer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Income> viewIncomeByCustomer(Customer customer) {
        AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
                FACADE_PARAMETER);
        return admin.viewIncomeByCustomer(customer.getCustName());
    }
    
    @Path("/viewIncomeByCompany")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Income> viewIncomeByCompany(Company company) {
        AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
        FACADE_PARAMETER);
        return admin.viewIncomeByCompany(company.getCompanyName());
    }
	
	@Path("/createCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Company company) throws ThreadException,
			ManagerSQLException {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		admin.createCompany(company);

	}

	@Path("/removeCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCompany(Company company) throws ManagerSQLException,
			ThreadException {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		admin.removeCompany(company);
	}

	@Path("/updateCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany(Company company) throws ManagerSQLException,
			ThreadException {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		admin.updateCompany(company);
	}

	@Path("/getCompany")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@QueryParam("id") long id)
			throws ManagerSQLException, ThreadException {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		return admin.getCompany(id);
	}

	@Path("/getAllCompanies")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies() throws ManagerSQLException,
			ThreadException {
		AdminFacade facade = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		return facade.getAllCompanies();
	}

	@Path("/createCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCustomer(Customer customer) throws ManagerSQLException,
			ThreadException {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		admin.createCustomer(customer);
	}

	@Path("/removeCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCustomer(Customer customer) throws ManagerSQLException,
			ThreadException {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		admin.removeCustomer(customer);
	}

	@Path("/updateCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCustomer(Customer customer) throws ManagerSQLException,
			ThreadException {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		admin.updateCustomer(customer);
	}

	@Path("/getCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(long id) throws ManagerSQLException,
			ThreadException {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
				FACADE_PARAMETER);
		return admin.getCustomer(id);
	}

	@Path("/getAllCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomer() throws ManagerSQLException, ThreadException {
				AdminFacade facade = (AdminFacade) request.getSession()
						.getAttribute("facade");
				return facade.getAllCustomer();
	}

}
