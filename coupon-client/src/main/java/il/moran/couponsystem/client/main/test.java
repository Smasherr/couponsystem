package il.moran.couponsystem.client.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import il.moran.couponsystem.client.client.AdminFacade;
import il.moran.couponsystem.client.client.CompanyFacade;
import il.moran.couponsystem.client.client.CouponClientFacade;
import il.moran.couponsystem.client.client.CustomerFacade;
import il.moran.couponsystem.client.connectpool.threadPool;
import il.moran.couponsystem.client.dal.Company;
import il.moran.couponsystem.client.dal.Coupon;
import il.moran.couponsystem.client.dal.CouponType;
import il.moran.couponsystem.client.dal.Customer;
import il.moran.couponsystem.client.dbdao.CouponDBDAO;
import il.moran.couponsystem.client.exceptions.CouponException;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;
import il.moran.couponsystem.client.exceptions.UserNotFoundException;



//Demo Test System
public class test {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// This function display a list of companies
	private static void printListCompanies(Collection<Company> list) {
		for (Company c : list) {
			System.out.println(c.toString());
		}
	}

	// This function display a list of companies
	private static void printListCustomers(Collection<Customer> list) {
		for (Customer c : list) {
			System.out.println(c.toString());
		}
	}

	// This function check AdminFacade functionality
	private static void checkAdminFacade() {
		try {
			CouponSystemSingleton sc = CouponSystemSingleton.getInstance();
			CouponClientFacade client = sc.login("admin", "1234", CouponSystemSingleton.ADMIN);
			AdminFacade admin = (AdminFacade) client;

			System.out.println("\n************ Start company check **************");
			printListCompanies(admin.getAllCompanies());

			// Check company functionality
			// Check create company
			Company company = new Company(0);
			company.setCompanyName("Google");
			company.setPassword("123456");
			company.setEmail("good@gmail.com");

			admin.createCompany(company);

			System.out.println("\n************ After create Company **************");
			printListCompanies(admin.getAllCompanies());

			// Check update company
			Collection<Company> companies = admin.getAllCompanies();
			// Get last company
			Iterator<Company> iterator = companies.iterator();
			while (iterator.hasNext())
				company = iterator.next();

			company.setEmail("success@test");
			admin.updateCompany(company);

			System.out.println("\n************ After update Company " + company.getId() + "**************");
			printListCompanies(admin.getAllCompanies());

			// Check remove company
			companies = admin.getAllCompanies();
			iterator = companies.iterator();
			while (iterator.hasNext())
				company = iterator.next();

			admin.removeCompany(company);
			System.out.println("\n************ After remove Company " + company.getId() + "**************");
			printListCompanies(admin.getAllCompanies());

			// Check company functionality
			System.out.println("\n************ Start customer check **************");
			printListCustomers(admin.getAllCustomer());

			// Check create customer
			Customer customer = new Customer(0);
			customer.setCustName("Oprah Winfrey");
			customer.setPassword("123456");
			admin.createCustomer(customer);

			System.out.println("\n************ After create Customer **************");
			printListCustomers(admin.getAllCustomer());

			// Check customer update
			Collection<Customer> customers = admin.getAllCustomer();
			Iterator<Customer> iteratorCust = customers.iterator();
			while (iteratorCust.hasNext())
				customer = iteratorCust.next();

			customer.setPassword("*******");
			admin.updateCustomer(customer);

			System.out.println("\n************ After update Customer **************");
			printListCustomers(admin.getAllCustomer());

			// Check remove customer
			customers = admin.getAllCustomer();
			iteratorCust = customers.iterator();
			while (iteratorCust.hasNext())
				customer = iteratorCust.next();

			admin.removeCustomer(customer);

			System.out.println("\n************ After remove Customer **************");
			printListCustomers(admin.getAllCustomer());

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// This function check CompanyFacade functionality
	private static void checkCompanyFacade() {
		try {
			CouponSystemSingleton sc = CouponSystemSingleton.getInstance();
			CouponClientFacade client = sc.login("WhatsApp", "WhatsApp", CouponSystemSingleton.COMPANY);
			CompanyFacade company = (CompanyFacade) client;

			System.out.println("\n************ Start Company Facade check **************");
			System.out.println(company.toString());

			// Check create a company
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			Date startDate = df.parse("2017-01-10 00:00:00");
			Date endDate = df.parse("2017-01-24 00:00:00");

			Coupon coupon = new Coupon(0);
			coupon.setTitle("The way to success, NIKE ");
			coupon.setAmount(10);
			coupon.setEndDate(endDate);
			coupon.setStartDate(startDate);
			coupon.setImage("smile.png");
			coupon.setMessage("YOUR WORKOUT YOUR STYLE ");
			coupon.setPrice(300.30);
			coupon.setType(CouponType.SPORTS);
			company.createCoupon(coupon);

			System.out.println("\n************ After create coupon **************");
			System.out.println(company.toString());

			// Check update a company
			Collection<Coupon> coupons = company.getAllCoupon();
			Iterator<Coupon> iterator = coupons.iterator();
			while (iterator.hasNext()) {
				coupon = iterator.next();
			}

			coupon.setPrice(900.00);
			company.updateCoupon(coupon);

			System.out.println("\n************ After update coupon " + coupon.getId() + "**************");
			System.out.println(company.toString());

			coupons = company.getAllCoupon();
			iterator = coupons.iterator();
			while (iterator.hasNext()) {
				coupon = iterator.next();
			}

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CouponException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// This function check CustomerFacade functionality
	private static void checkCustomerFacade() {

		try {
			CouponDBDAO couponDB = new CouponDBDAO();
			CouponSystemSingleton sc = CouponSystemSingleton.getInstance();
			CouponClientFacade client = sc.login("moran", "123456", CouponSystemSingleton.CUSTOMER);
			CustomerFacade customer = (CustomerFacade) client;

			System.out.println("\n************ Start Customer Facade check **************");
			System.out.println(customer.toString());

			Coupon coupon = new Coupon(0);
			Collection<Coupon> coupons = couponDB.getAllCoupons();
			Iterator<Coupon> iteratorComp = coupons.iterator();
			while (iteratorComp.hasNext())
				coupon = iteratorComp.next();

			customer.purchaseCoupon(coupon);

			System.out.println("\n************ After purchase coupon id ***************");
			System.out.println(customer.toString());

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CouponException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// This function check ExpiredCouponDeamon
	public static void checkThreadActivity() {
		try {
			CouponSystemSingleton sc = CouponSystemSingleton.getInstance();
			Thread.sleep(300000);
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testConnectionPoolThreading() {
		threadPool t1 = new threadPool();
		threadPool t2 = new threadPool();
		threadPool t3 = new threadPool();
		threadPool t4 = new threadPool();
		threadPool t5 = new threadPool();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

	}

	public static void main(String[] args) {

		 checkAdminFacade();
		 checkCompanyFacade();
		 checkCustomerFacade();
		 checkThreadActivity();
		 //testConnectionPoolThreading();

		
	}

}
