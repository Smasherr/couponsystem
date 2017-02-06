package il.moran.couponsystem.client.dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import il.moran.couponsystem.client.connectpool.ConnectionPool;
import il.moran.couponsystem.client.dal.Company;
import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.CouponType;
import il.moran.couponsystem.client.dal.Customer;
import il.moran.couponsystem.client.dao.CouponDAO;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

/*
 *  class define all DB requests for coupons in the system
 */
public class CouponDBDAO implements CouponDAO {

	private ConnectionPool pool;
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public CouponDBDAO() throws ManagerSQLException {
		pool = ConnectionPool.getInstance();
	}

	/**
	 * This functions add the given coupon to the database.
	 * 
	 * @param coupon
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public void createCoupon(Coupon coupon) throws ManagerSQLException, ThreadException {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String start_date = df.format(coupon.getStartDate());
		String end_date = df.format(coupon.getEndDate());

		String insertSQl = "insert into coupon(title, start_date, end_date, amount, type, message, price, image) "
				+ "values ('" + coupon.getTitle() + "', '" + start_date + "', '" + end_date + "', " + coupon.getAmount()
				+ ", '" + coupon.getType() + "', '" + coupon.getMessage() + "', " + coupon.getPrice() + ", '"
				+ coupon.getImage() + "')";
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
	 * This functions add the given coupon and associate to the given company in
	 * the database.
	 * 
	 * @param coupon
	 * @param company
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void createCoupon(Coupon coupon, Company company) throws ManagerSQLException, ThreadException {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String start_date = df.format(coupon.getStartDate());
		String end_date = df.format(coupon.getEndDate());
		String insertSQl = "insert into coupon(title, start_date, end_date, amount, type, message, price, image) "
				+ "values ('" + coupon.getTitle() + "', '" + start_date + "', '" + end_date + "', " + coupon.getAmount()
				+ ", '" + coupon.getType() + "', '" + coupon.getMessage() + "', " + coupon.getPrice() + ", '"
				+ coupon.getImage() + "')";
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			if (statement.executeUpdate(insertSQl, Statement.RETURN_GENERATED_KEYS) == 0) {
				throw new ManagerSQLException("Record wasn't inserted");
			} else {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					String insertSQLCompanyCoupon = "insert into company_coupon(comp_id, coupon_id) values("
							+ company.getId() + "," + resultSet.getLong(1) + ");";
					if (statement.executeUpdate(insertSQLCompanyCoupon) == 0) {
						throw new ManagerSQLException("Coupon wasn't inserted to company_coupon");
					}
				}
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This functions remove the coupon from the system with all connections to
	 * it. If no coupon was found there would be a ManagerSQLException.
	 * 
	 * @param coupon
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws ManagerSQLException, ThreadException {
		String deleteSQL = "delete from coupon where id=" + coupon.getId();
		String deleteCompanySQL = "delete from company_coupon where coupon_id=" + coupon.getId();
		String deleteCustomerSQL = "delete from customer_coupon where coupon_id=" + coupon.getId();
		Statement statement;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(deleteCompanySQL);
			statement.executeUpdate(deleteCustomerSQL);
			if (statement.executeUpdate(deleteSQL) == 0) {
				throw new ManagerSQLException("no records deleted.");
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This function updates the coupon end date and price. If no record was
	 * updated there would be a ManagerSQLException.
	 * 
	 * @param coupon
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws ManagerSQLException, ThreadException {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String end_date = df.format(coupon.getEndDate());
		String sqlUpdate = "update coupon set " + "end_date = '" + end_date + "', " + "price = '" + coupon.getPrice()
				+ "' " + "where id =" + coupon.getId();
		Connection conn = null;
		try {

			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			if (statement.executeUpdate(sqlUpdate) == 0) {
				throw new ManagerSQLException("No record was updated.");
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	public void updateAmountCoupon(Coupon coupon) throws ManagerSQLException, ThreadException {
		String sqlUpdate = "update coupon set " + "amount = " + coupon.getAmount() + " " 
				+ "where id =" + coupon.getId();
		Connection conn = null;
		try {

			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			if (statement.executeUpdate(sqlUpdate) == 0) {
				throw new ManagerSQLException("No record was updated.");
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}
	
	
	
	
	/**
	 * This function return the company that belong to the ID that was given if
	 * the id have no company associate, there would be a ManagerSQLException.
	 * 
	 * @param id
	 * @return Coupon
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public Coupon getCoupon(long id) throws ManagerSQLException, ThreadException {
		Coupon coupon;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from coupon where id=" + id);
			if (resultSet.next()) {
				coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				return coupon;
			} else {
				throw new ManagerSQLException("Coupon ID wasn't found in the SQL");
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This function return a collection with all the coupons in the system.
	 * 
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws ManagerSQLException, ThreadException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from coupon");
			while (resultSet.next()) {
				Coupon coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupons.add(coupon);
			}
		} catch (SQLException e) {
			throw new ManagerSQLException("Manager SQL error: " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	/**
	 * This function return a collection with all the coupons in the system with
	 * the given type.
	 * 
	 * @param type
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType type) throws ManagerSQLException, ThreadException {
		String sql = "select * from coupon where type='" + type.name() + "'";
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Coupon coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupons.add(coupon);
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	/**
	 * This function return a collection with all the coupons of the given
	 * company with the given type.
	 * 
	 * @param company
	 * @param type
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getCouponsByType(Company company, CouponType type)
			throws ManagerSQLException, ThreadException {
		String sql = "select * from coupon c right join company_coupon cc on c.id = cc.coupon_id "
				+ "where cc.comp_id = " + company.getId() + " and c.type='" + type.name() + "'";
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Coupon coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupons.add(coupon);
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	/**
	 * This function return a collection with all the coupons of the given
	 * customer with the given type.
	 * 
	 * @param customer
	 * @param type
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getCouponsByType(Customer customer, CouponType type)
			throws ManagerSQLException, ThreadException {
	String sql = "select * from coupon c right join customer_coupon cc on c.id = cc.coupon_id "
				+ "where cc.cust_id = " + customer.getId() + " and c.type='" + type.name() + "'";
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Coupon coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupons.add(coupon);
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	/**
	 * This function return a collection with all the coupons of the given
	 * company until the date given.
	 * 
	 * @param company
	 * @param date
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getCouponsOfCompanyUntilDate(Company company, Date date)
			throws ManagerSQLException, ThreadException {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String end_date = df.format(date);
		String sql = "select * from coupon c right join company_coupon cc on c.id = cc.coupon_id "
				+ "where cc.comp_id = " + company.getId() + " and c.end_date < '" + end_date + "';";
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Coupon coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupons.add(coupon);
			}
		} catch (SQLException e) {
			throw new ManagerSQLException("Manager SQL error: " + e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	/**
	 * This function return a collection with all the coupons of the given
	 * company until the price given.
	 * 
	 * @param company
	 * @param price
	 * @return
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getCouponsOfCompanyUnilPrice(Company company, float price)
			throws ManagerSQLException, ThreadException {
		String sql = "select * from coupon c right join company_coupon cc on c.id = cc.coupon_id "
				+ "where cc.comp_id = " + company.getId() + " and c.price < " + price + ";";
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Coupon coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupons.add(coupon);
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	
	
	
	/**
	 * This function return a collection with all the available coupons to purches for the customer of the given
	 * @param customer
	 * @return
	 * @throws ManagerThreadException
	 * @throws ManagerSQLException
	 */
	public Collection<Coupon> getAllAvailableCoupons(Customer customer) throws ThreadException, ManagerSQLException{
		String sql = "select * from coupon c"+
						" where c.amount > 0"+
						" and c.id not in (select coupon_id from customer_coupon cc" +
						" where cc.cust_id =" + customer.getId() + ")";
		
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Coupon coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupons.add(coupon);
			}
		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	
	
	
	/**
	 * This function return a collection with all the coupons of the given
	 * customer until the price given.
	 * 
	 * @param customer
	 * @param price
	 * @return
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Coupon> getCouponsOfCustomerUntilPrice(Customer customer, float price)
			throws ManagerSQLException, ThreadException {
		String sql = "select * from coupon c right join customer_coupon cc on c.id = cc.coupon_id "
				+ "where cc.cust_id = " + customer.getId() + " and c.price < " + price + ";";
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		;
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Coupon coupon = new Coupon(resultSet.getInt("id"));
				coupon.setAmount(resultSet.getInt("amount"));
				coupon.setEndDate(resultSet.getDate("end_date"));
				coupon.setStartDate(resultSet.getDate("start_date"));
				coupon.setImage(resultSet.getString("image"));
				coupon.setMessage(resultSet.getString("message"));
				coupon.setPrice(resultSet.getDouble("price"));
				coupon.setTitle(resultSet.getString("title"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupons.add(coupon);
			}

		} catch (SQLException e) {
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

		return coupons;
	}

	/**
	 * This function check if the given coupon title exist in the database.
	 * 
	 * @param coupon
	 * @return boolean
	 * @throws ThreadException
	 * @throws ManagerSQLException
	 */
	public boolean isCouponTitleExist(Coupon coupon) throws ThreadException, ManagerSQLException {
		String sql = "select * from coupon where title = '" + coupon.getTitle() + "';";
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
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}

	}

	/**
	 * This function check if the given coupon belong to the given customer.
	 * 
	 * @param customer
	 * @param coupon
	 * @return boolean
	 * @throws ThreadException
	 * @throws ManagerSQLException
	 */
	public boolean isCouponBelongToCustomer(Customer customer, Coupon coupon)
			throws ThreadException, ManagerSQLException {
		String sql = "select * from customer_coupon" + " where cust_id = " + customer.getId() + " and coupon_id = "
				+ coupon.getId() + ";";
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
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
	}

	/**
	 * This function check if the given coupon belong to the given company.
	 * 
	 * @param company
	 * @param coupon
	 * @return boolean
	 * @throws ThreadException
	 * @throws ManagerSQLException
	 */
	public boolean isCouponBelongToCompany(Company company, Coupon coupon)
			throws ThreadException, ManagerSQLException {
		String sql = "select * from company_coupon" + " where comp_id = " + company.getId() + " and coupon_id = "
				+ coupon.getId() + ";";
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
			throw new ManagerSQLException(e.getMessage());
		} finally {
			pool.returnConnection(conn);
		}
	}

	/**
	 * This function looking for coupons that end from the given date and delete
	 * them.
	 * 
	 * @param expirationDate
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void removeExpiredCoupon(Date expirationDate) throws ManagerSQLException, ThreadException {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String expirationString = df.format(expirationDate);
		String sql = "select * from coupon where end_date < '" + expirationString + "';";
		Connection conn = null;
		try {
			conn = pool.getConnection();
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			while (results.next()) {
				removeCoupon(new Coupon(results.getLong("id")));
				// Use the line before to get indication if the thread deleted
				// coupons
				// System.out.println("Coupon " + results.getLong("id") + " have
				// been deleted from coupons");
			}

			pool.returnConnection(conn);
		} catch (SQLException e) {
			throw new ManagerSQLException("Manager SQL error: " + e.getMessage());
		}

	}

}
