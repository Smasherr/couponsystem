package il.moran.couponsystem.client.dao;

import java.util.Collection;

import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.Customer;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

/*
 *Interface that define  basic functions of Customer DAO
 */
public interface CustomerDAO {

	public void createCustomer(Customer customer) throws ManagerSQLException, ThreadException;

	public void removeCustomer(Customer customer) throws ManagerSQLException, ThreadException;

	public void updateCustomer(Customer customer) throws ManagerSQLException, ThreadException;

	public Customer getCustomer(long id) throws ManagerSQLException, ThreadException;

	public Collection<Customer> getAllCustomers() throws ManagerSQLException, ThreadException;

	public Collection<Coupon> getCoupons(Customer customer) throws ManagerSQLException, ThreadException;

	public boolean login(String custName, String password) throws ManagerSQLException, ThreadException;

}
