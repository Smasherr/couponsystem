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

import il.moran.couponsystem.client.client.AdminFacade;
import il.moran.couponsystem.client.client.CustomerFacade;
import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.CouponType;
import il.moran.couponsystem.client.dal.Customer;
import il.moran.couponsystem.client.exceptions.CouponException;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

@Path("/customer")
public class CustomerService {

	@Context
	HttpServletRequest request;

	private static final String FACADE_PARAMETER = "facade";

	@GET
	@Path("getCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCoupons() {
		CustomerFacade customer = (CustomerFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		return customer.getAllPurchasedCoupons();
	}

	@GET
	@Path("getCouponsByType")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByType(
			@QueryParam("type") CouponType type) throws ManagerSQLException,
			ThreadException {
		CustomerFacade customer = (CustomerFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		return customer.getAllPurchasedCouponsByType(type);
	}

	@GET
	@Path("getCouponsByPrice")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByPrice(@QueryParam("price") float price)
			throws ManagerSQLException, ThreadException {
		CustomerFacade customer = (CustomerFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		return customer.getAllPurchasedCouponsUntilPrice(price);
	}

	@GET
	@Path("getAvailableCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAvailableCoupons()
			throws ThreadException, ManagerSQLException {
		CustomerFacade customer = (CustomerFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		return customer.getAllAvailableCoupons();
	}

	@POST
	@Path("purchaseCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void purchaseCoupon(Coupon coupon) throws ThreadException,
			ManagerSQLException, CouponException {
		CustomerFacade customer = (CustomerFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		customer.purchaseCoupon(coupon);
	}
}
