package il.moran.couponsystem.client.web.webServices;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import il.moran.couponsystem.client.client.AdminFacade;
import il.moran.couponsystem.client.client.CompanyFacade;
import il.moran.couponsystem.client.dal.Company;
import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.CouponType;
import il.moran.couponsystem.client.exceptions.CouponException;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;
import il.moran.couponsystem.entity.Income;

@Path("/company")
public class CompanyService {

	@Context
	HttpServletRequest request;

	private static final String FACADE_PARAMETER = "facade";
    
    @Path("/viewIncomeByCompany")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Income> viewIncomeByCompany(Company company) {
        AdminFacade admin = (AdminFacade) request.getSession().getAttribute(
        FACADE_PARAMETER);
        return admin.viewIncomeByCompany(company.getCompanyName());
    }

	@POST
	@Path("createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		facade.createCoupon(coupon);

	}

	@POST
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		facade.updateCoupon(coupon);
	}

	@GET
	@Path("getCouponById")
	public Coupon getCoupon(@QueryParam("id") long id)
			throws ThreadException, ManagerSQLException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		return facade.getCoupon(id);
	}

	@GET
	@Path("getAllCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupon() throws ManagerSQLException,
			ThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute("facade");
		return facade.getAllCoupon();
	}

	@GET
	@Path("getCouponByType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponByType(
			@QueryParam("type") CouponType type) throws ManagerSQLException,
			ThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute("facade");
		return facade.getCouponByType(type);
	}

	@GET
	@Path("getCouponUntilPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponUntilPrice(
			@QueryParam("price") float price) throws ManagerSQLException,
			ThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute("facade");
		return facade.getCouponUntilPrice(price);
	}

	@GET
	@Path("getCouponUntilDate")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponUntilDate(
			@QueryParam("date") String dateStr) throws ParseException,
			ManagerSQLException, ThreadException {
		//XXX: format the date format outputted by javascript in scriptAngular.js:272
		//see http://stackoverflow.com/a/1056730
		DateFormat df = new SimpleDateFormat("EEE MMM d yyyy", Locale.US);
		Date date = df.parse(dateStr);
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute("facade");
		return facade.getCouponUntilDate(date);
	}
}
