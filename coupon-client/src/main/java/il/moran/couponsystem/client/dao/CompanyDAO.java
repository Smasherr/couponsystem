package il.moran.couponsystem.client.dao;

import java.util.Collection;

import il.moran.couponsystem.client.dal.Company;
import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

/*
 * Interface that define  basic functions of Company DAO
 */

public interface CompanyDAO {

	public Company createCompany(Company company) throws ManagerSQLException, ThreadException;

	public void removeCompany(Company company) throws ManagerSQLException, ThreadException;

	public void updateCompany(Company company) throws ManagerSQLException, ThreadException;

	public Company getCompany(long id) throws ManagerSQLException, ThreadException;

	public Collection<Company> getAllCompanies() throws ManagerSQLException, ThreadException;

	public Collection<Coupon> getCoupons(Company company) throws ManagerSQLException, ThreadException;

	public boolean login(String companyName, String password) throws ManagerSQLException, ThreadException;

}
