package il.moran.couponsystem.client.web.webServices;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import il.moran.couponsystem.client.client.CompanyFacade;
import il.moran.couponsystem.client.client.CouponClientFacade;
import il.moran.couponsystem.client.client.CustomerFacade;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;
import il.moran.couponsystem.client.exceptions.UserNotFoundException;
import il.moran.couponsystem.client.main.CouponSystemSingleton;

@Path("/login")
public class LoginService {

	private static final String FACADE_PARAMETER = "facade";

	@Context
	HttpServletRequest request;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ClientData login(@FormParam("username") String username, @FormParam("password") String password,
			@FormParam("role") String role) throws ManagerSQLException, UserNotFoundException, ThreadException {
		ClientData clientData = new ClientData();
		CouponClientFacade facade;
		CouponSystemSingleton couponSystem = CouponSystemSingleton.getInstance();
		switch (role) {
		case "admin":
			facade = couponSystem.login(username, password, CouponSystemSingleton.ADMIN);
			clientData.setUsername(username);
			clientData.setRole(role);
			break;
		case "company":
			facade = couponSystem.login(username, password, CouponSystemSingleton.COMPANY);
			CompanyFacade companyFacade = (CompanyFacade) facade;
			clientData.setUsername(companyFacade.getCompany().getCompanyName());
			break;
		case "customer":
			facade = couponSystem.login(username, password, CouponSystemSingleton.CUSTOMER);
			CustomerFacade customerFacade = (CustomerFacade) facade;
			clientData.setUsername(customerFacade.getCustomer().getCustName());
			break;
		default:
			throw new UserNotFoundException("No such type");
		}
		clientData.setRole(role);
		request.getSession().setAttribute(FACADE_PARAMETER, facade);
		return clientData;
	}

	@GET
	@Path("loginTest")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientData test() {
		ClientData data = new ClientData();
		data.setRole("admin");
		data.setUsername("Moran");
		return data;
	}

	@GET
	@Path("isConnected")
	public String isConnected() {
		return String.valueOf(request.getSession().getAttribute(FACADE_PARAMETER) != null);
	}

	@GET
	@Path("logout")
	public void logout() {
		request.getSession().invalidate();
	}
}
