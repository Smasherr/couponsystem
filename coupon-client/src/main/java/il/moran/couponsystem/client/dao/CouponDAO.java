package il.moran.couponsystem.client.dao;

import java.util.Collection;

import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.CouponType;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

/*
 * Interface that define  basic functions of Coupon DAO
 */

public interface CouponDAO {

	public void createCoupon(Coupon coupon) throws ManagerSQLException, ThreadException;

	public void removeCoupon(Coupon coupon) throws ManagerSQLException, ThreadException;

	public void updateCoupon(Coupon coupon) throws ManagerSQLException, ThreadException;

	public Coupon getCoupon(long id) throws ManagerSQLException, ThreadException;

	public Collection<Coupon> getAllCoupons() throws ManagerSQLException, ThreadException;

	public Collection<Coupon> getCouponsByType(CouponType type) throws ManagerSQLException, ThreadException;
}
