package il.moran.couponsystem.client.threads;

import java.util.Date;

import il.moran.couponsystem.client.dbdao.CouponDBDAO;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

/*
 * CouponExpirationTask is a runnable class that run daily, check expiration date and remove if needed 
 */
public class CouponExpirationTask implements Runnable {
	private boolean quit;
	private static final long WAITING_TIME = 24 * 60 * 60 * 1000; // 24 hours

	/**
	 * This function start the thread and every WAITING_TIME will remove expired
	 * coupons
	 */
	@Override
	public void run() {
		quit = false;
		try {

			while (!quit) {
				DailyCouponExpirationTask();
				Thread.sleep(WAITING_TIME);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function will delete the coupons
	 * @throws ThreadException 
	 * @throws ManagerSQLException 
	 */
	public void DailyCouponExpirationTask() throws ManagerSQLException, ThreadException {

		CouponDBDAO couponDB = new CouponDBDAO();
		couponDB.removeExpiredCoupon(new Date());

	}

	/**
	 * This function will stop the remove of coupon but
	 * wouldn't stop the Thread
	 */
	public void stopTask() {
		quit = true;
	}

}
