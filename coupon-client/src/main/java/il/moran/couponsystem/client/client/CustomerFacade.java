package il.moran.couponsystem.client.client;

import java.util.Collection;
import java.util.Date;

import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.CouponType;
import il.moran.couponsystem.client.dal.Customer;
import il.moran.couponsystem.client.dbdao.CouponDBDAO;
import il.moran.couponsystem.client.dbdao.CustomerDBDAO;
import il.moran.couponsystem.client.delegate.BusinessDeleagte;
import il.moran.couponsystem.client.exceptions.CouponException;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;
import il.moran.couponsystem.entity.Income;
import il.moran.couponsystem.entity.IncomeType;

/*
 *  Customer Facade manage requests of a customer in the system.
 *  The class need a customer to be initialized.
 * 
 */
public class CustomerFacade implements CouponClientFacade {
	private CustomerDBDAO customerDB;
	private CouponDBDAO couponDB;
	private final Customer customer;

	public CustomerFacade(Customer customer) throws ManagerSQLException {
		this.customer = customer;
		customerDB = new CustomerDBDAO();
		couponDB = new CouponDBDAO();

	}

	/**
	 * This functions associate the coupon to the customer logged in. if there
	 * no coupon available or the coupon already belong to the customer, there
	 * would be a CouponException.
	 * 
	 * @param coupon
	 * @throws ThreadException
	 * @throws ManagerSQLException
	 * @throws CouponException
	 */
	public void purchaseCoupon(Coupon coupon) throws ThreadException, ManagerSQLException, CouponException {

		if (coupon.getAmount() > 0 && !couponDB.isCouponBelongToCustomer(customer, coupon)) {
			customerDB.purchaseCoupon(customer, coupon);
			coupon.setAmount(coupon.getAmount() - 1);
			couponDB.updateCoupon(coupon);
			customer.setCoupons(customerDB.getCoupons(customer));
			
			Income incomeEntity = new Income(customer.getCustName(), new Date(), IncomeType.CUSTOMER_PURCHASE, coupon.getAmount());
            BusinessDeleagte.getInstance().storeIncome(incomeEntity);

		}else{
			throw new CouponException("Error purchusing coupon");
		}
		

	}

	/**
	 * This function returns all coupon the user logged in purchased.
	 * @return Collection<Coupon>
	 */
	public Collection<Coupon> getAllPurchasedCoupons() {
		return customer.getCoupons();

	}
	public Collection<Coupon> getAllAvailableCoupons() throws ThreadException, ManagerSQLException{
		return couponDB.getAllAvailableCoupons(customer);
	}
	/**
	 * This function returns all coupon the user logged in purchased of the type given.
	 * @param type
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type)
			throws ManagerSQLException, ThreadException {
		return couponDB.getCouponsByType(customer, type);

	}

	/**
	 * This function returns all coupon the user logged in purchased of the price given
	 * @param price
	 * @return
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getAllPurchasedCouponsUntilPrice(float price)
			throws ManagerSQLException, ThreadException {
		return couponDB.getCouponsOfCustomerUntilPrice(customer, price);

	}

	/**
	 * When toString is called, the details of the customer logged in will returns.
	 * other details aren't relevant. 
	 */
	@Override
	public String toString() {
		return customer.toString();
	}

	public Customer getCustomer() {
		return customer;
	}

}
