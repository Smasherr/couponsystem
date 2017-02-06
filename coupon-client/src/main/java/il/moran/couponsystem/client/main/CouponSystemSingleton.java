package il.moran.couponsystem.client.main;

import il.moran.couponsystem.client.client.CouponClientFacade;
import il.moran.couponsystem.client.client.LoginManager;
import il.moran.couponsystem.client.connectpool.ConnectionPool;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;
import il.moran.couponsystem.client.exceptions.UserNotFoundException;
import il.moran.couponsystem.client.threads.CouponExpirationTask;

/*
 * CouponSystemSingleton is a singleton that manage the coupon system.
 */
public class CouponSystemSingleton {

	private static CouponSystemSingleton instance;
	private CouponExpirationTask expiredCouponsRunnable;
	private Thread deamon;
	private ConnectionPool pool;
	public static final String ADMIN = "admin";
	public static final String CUSTOMER = "customer";
	public static final String COMPANY = "company";

	/**
	 * This method return the only instance of the CouponSystemSingleton
	 * 
	 * @return CouponSystem
	 * @throws ManagerSQLException
	 */
	public static CouponSystemSingleton getInstance() throws ManagerSQLException {
		if (instance == null) {
			instance = new CouponSystemSingleton();
		}

		return instance;
	}

	/*
	 * At the contractor of the CouponSystemSingleton the ConnectionPool is been
	 * initialized and the thread who check the expiration of the coupons
	 */
	private CouponSystemSingleton() throws ManagerSQLException {
		expiredCouponsRunnable = new CouponExpirationTask();
		deamon = new Thread(expiredCouponsRunnable);
		deamon.start();
		pool = ConnectionPool.getInstance();

	}

	/**
	 * This function authenticate the user and the password according to the
	 * type.
	 * 
	 * @param name
	 * @param password
	 * @param clientType
	 * @return CouponClientFacade
	 * @throws UserNotFoundException
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public CouponClientFacade login(String name, String password, String clientType)
			throws UserNotFoundException, ManagerSQLException, ThreadException {
		return LoginManager.login(name, password, clientType);
	}

	/**
	 * This function close all the open connections and stops the thread who
	 * check the expirations
	 * 
	 * @throws ManagerSQLException
	 */
	public void shutdown() throws ManagerSQLException {
		expiredCouponsRunnable.stopTask();
		pool.closeAllConnections();
	}

}
