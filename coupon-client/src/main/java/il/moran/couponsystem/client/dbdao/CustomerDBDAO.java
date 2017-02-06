package il.moran.couponsystem.client.dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import il.moran.couponsystem.client.connectpool.ConnectionPool;
import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.Customer;
import il.moran.couponsystem.client.dao.CustomerDAO;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

/*
 *  class define all DB requests for customer in the system
 */
public class CustomerDBDAO implements CustomerDAO {

	private ConnectionPool pool;

	public CustomerDBDAO() throws ManagerSQLException {
		pool = ConnectionPool.getInstance();

	}

	/**
	 * This functions add the given customer to the database.
	 * 
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public void createCustomer(Customer customer) throws ManagerSQLException, ThreadException {
		String insertSQl = "insert into customer(cust_name, password) " + "values ('" + customer.getCustName() + "', '"
				+ customer.getPassword() + "')";
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			if (statement.executeUpdate(insertSQl) == 0) {
				throw new ManagerSQLException("Record wasn't inserted");

			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This functions remove the customer from the system with it coupons and
	 * all connections to it.
	 * 
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public void removeCustomer(Customer customer) throws ManagerSQLException, ThreadException {
		String deleteSQl = "delete from customer where id=" + customer.getId();
		String deleteCustomerCouponSQL = "delete from customer_coupon where cust_id = " + customer.getId();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate(deleteCustomerCouponSQL);
			if (statement.executeUpdate(deleteSQl) == 0) {
				throw new ManagerSQLException("no records deleted.");
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This function updates the customer password. If no record was updated
	 * there would be a ManagerSQLException.
	 * 
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public void updateCustomer(Customer customer) throws ManagerSQLException, ThreadException {
		String sqlUpdate = "update customer set " + "password = '" + customer.getPassword() + "' " + "where id ="
				+ customer.getId();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			if (statement.executeUpdate(sqlUpdate) == 0) {
				throw new ManagerSQLException("No record was updated.");
			}
			pool.returnConnection(conn);
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {

		}

	}

	/**
	 * This function return the customer that belong to the ID that was given.
	 * if the id have no company associate, there would be a
	 * ManagerSQLException.
	 * 
	 * @param id
	 * @return Customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public Customer getCustomer(long id) throws ManagerSQLException, ThreadException {
		String sql = "select * from customer where id=" + id;
		Customer customer = null;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				customer = new Customer(resultSet.getInt("id"));
				customer.setCustName(resultSet.getString("cust_name"));
				customer.setPassword(resultSet.getString("password"));
				customer.setCoupons(getCoupons(customer));
			} else {
				throw new ManagerSQLException("Customer not found.");
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
		return customer;
	}

	/**
	 * 
	 * This function return the customer that belong to the username and
	 * password that was given. if the username and password have no customer
	 * associate, there would be a ManagerSQLException.
	 * 
	 * @param username
	 * @param password
	 * @return Customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Customer getCustomer(String username, String password) throws ManagerSQLException, ThreadException {
		String sql = "select * from customer where cust_name='" + username + "' and password='" + password + "';";
		Customer customer = null;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				customer = new Customer(resultSet.getInt("id"));
				customer.setCustName(resultSet.getString("cust_name"));
				customer.setPassword(resultSet.getString("password"));
				customer.setCoupons(getCoupons(customer));
			} else {
				throw new ManagerSQLException("Customer not found.");
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
		return customer;
	}

	/**
	 * This function return a collection with all the customers in the system.
	 * 
	 * @return Collection<Customer>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws ManagerSQLException, ThreadException {
		Collection<Customer> customers = new ArrayList<Customer>();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from customer");
			while (resultSet.next()) {
				Customer customer = new Customer(resultSet.getInt("id"));
				customer.setCustName(resultSet.getString("cust_name"));
				customer.setPassword(resultSet.getString("password"));
				customer.setCoupons(getCoupons(customer));
				customers.add(customer);
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return customers;
	}

	/**
	 * This function return all coupons of the given customer.
	 * 
	 * @param customer
	 * @return
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws ManagerSQLException, ThreadException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			CouponDBDAO couponDB = new CouponDBDAO();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select * from customer_coupon where cust_id=" + customer.getId());
			while (resultSet.next()) {
				long couponId = resultSet.getLong("coupon_id");
				coupons.add(couponDB.getCoupon(couponId));
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;

	}

	/**
	 * This function authenticate the user and the password at the customer
	 * table.
	 * 
	 * @param custName
	 * @param password
	 * @return
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public boolean login(String custName, String password) throws ManagerSQLException, ThreadException {
		String sql = "select * from customer where cust_name='" + custName + "' and password='" + password + "';";
		boolean result = false;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				result = true;
			}

		} catch (SQLException e) {
			throw new ManagerSQLException("Manager SQL error: " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
		return result;
	}

	/**
	 * This function connect the given customer with the given coupon
	 * 
	 * @param customer
	 * @param coupon
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void purchaseCoupon(Customer customer, Coupon coupon) throws ManagerSQLException, ThreadException {
		String insertSQl = "INSERT INTO customer_coupon(cust_id,coupon_id)" + " VALUES(" + customer.getId() + ","
				+ coupon.getId() + ");";
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			if (statement.executeUpdate(insertSQl) == 0) {
				throw new ManagerSQLException("Record wasn't added");

			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
	}

	/**
	 * Check if the customer name exist in the database.
	 * 
	 * @param customer
	 * @return boolean
	 * @throws ThreadException
	 * @throws ManagerSQLException
	 */
	public boolean isCustomerNameExist(Customer customer) throws ThreadException, ManagerSQLException {
		String sql = "select * from customer where cust_name = '" + customer.getCustName() + "';";
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			throw new ManagerSQLException("SQL manager exception: " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

}
