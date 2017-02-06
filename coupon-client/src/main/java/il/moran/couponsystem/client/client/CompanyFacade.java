package il.moran.couponsystem.client.client;
import java.util.Collection;
import java.util.Date;

import il.moran.couponsystem.client.dal.Company;
import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.CouponType;
import il.moran.couponsystem.client.dbdao.CompanyDBDAO;
import il.moran.couponsystem.client.dbdao.CouponDBDAO;
import il.moran.couponsystem.client.delegate.BusinessDeleagte;
import il.moran.couponsystem.client.exceptions.CouponException;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;
import il.moran.couponsystem.entity.Income;
import il.moran.couponsystem.entity.IncomeType;

public class CompanyFacade implements CouponClientFacade {

	private CompanyDBDAO companyDB;
	private CouponDBDAO couponDB;
	private final Company company;

	public CompanyFacade(Company company) throws ManagerSQLException {

		companyDB = new CompanyDBDAO();
		couponDB = new CouponDBDAO();
		this.company = company;

	}
    
    public Collection<Income> viewIncomeByCompany(String companyName)
    {
        return BusinessDeleagte.getInstance().viewIncomeByCompany(companyName);
    }

	/**
	 * This function creates a coupon and associate it to the company in the facade. 
	 * if the coupon title already exist in the system there would be a CouponException.
	 * @param coupon
	 * @throws CouponException
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void createCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ThreadException {

		if (!couponDB.isCouponTitleExist(coupon)) {
			couponDB.createCoupon(coupon, company);
			company.setCoupons(companyDB.getCoupons(company));
			
			Income incomeEntity = new Income(company.getCompanyName(), new Date(), IncomeType.COMPANY_NEW_COUPON, coupon.getAmount());
            BusinessDeleagte.getInstance().storeIncome(incomeEntity);
		} else
			throw new CouponException("Coupon already exist");

	}

	/**
	 * This function updates coupon end_date and price.
	 * If the coupon isn't belong to the company logged in there would be a CouponException.
	 * @param coupon
	 * @throws CouponException
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void updateCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ThreadException {
		if (couponDB.isCouponBelongToCompany(company, coupon)) {
			couponDB.updateCoupon(coupon);
			company.setCoupons(companyDB.getCoupons(company));
			
			Income incomeEntity = new Income(company.getCompanyName(), new Date(), IncomeType.COMPANY_UPDATE_COUPON, coupon.getAmount());
            BusinessDeleagte.getInstance().storeIncome(incomeEntity);
		} else {
			throw new CouponException("Coupon not exist in company list");
		}

	}

	/**
	 * This function return a Coupon of the given ID.
	 * if the Id isn't associate with any coupon there would be a ManagerSQLException.
	 * @param id
	 * @return Coupon
	 * @throws ThreadException
	 * @throws ManagerSQLException
	 */
	public Coupon getCoupon(long id) throws ThreadException,
			ManagerSQLException {
		return couponDB.getCoupon(id);
	}

	/**
	 * This function returns a collection of coupons of the company logged in.
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getAllCoupon() throws ManagerSQLException,
			ThreadException {
		return companyDB.getCoupons(company);
	}

	/**
	 * This function returns a collection of coupons of the company logged in of the type given.
	 * @param type
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getCouponByType(CouponType type)
			throws ManagerSQLException, ThreadException {

		return couponDB.getCouponsByType(company, type);

	}

	/**
	 * This function returns a collection of coupons of the company logged in 
	 * that less than the price given.
	 * @param price
	 * @return
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getCouponUntilPrice(float price)
			throws ManagerSQLException, ThreadException {

		return couponDB.getCouponsOfCompanyUnilPrice(company, price);

	}

	/**
	 * This function returns a collection of coupons of the company logged in 
	 * that before the date given.
	 * @param date
	 * @return
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getCouponUntilDate(Date date)
			throws ManagerSQLException, ThreadException {

		return couponDB.getCouponsOfCompanyUntilDate(company, date);

	}

	/**
	 * When toString is called, the details of the company logged in will returns.
	 * other details aren't relevant. 
	 */
	@Override
	public String toString() {
		return company.toString();
	}

	public Company getCompany() {
		// TODO Auto-generated method stub
		return company;
	}

}
