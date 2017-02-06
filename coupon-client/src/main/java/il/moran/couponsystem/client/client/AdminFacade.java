package il.moran.couponsystem.client.client;

import java.util.Collection;

import il.moran.couponsystem.client.dal.Company;
import il.moran.couponsystem.client.dal.Customer;
import il.moran.couponsystem.client.dbdao.CompanyDBDAO;
import il.moran.couponsystem.client.dbdao.CustomerDBDAO;
import il.moran.couponsystem.client.delegate.BusinessDeleagte;
import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;
import il.moran.couponsystem.entity.Income;

/*
 *  Admin Facade responsible for the coupon system administrator requests in the system.
 * 
 */
public class AdminFacade implements CouponClientFacade  {

	private CompanyDBDAO companyDB;
	private CustomerDBDAO customerDB;
	
	public AdminFacade() throws ManagerSQLException {
		companyDB = new CompanyDBDAO();
		customerDB = new CustomerDBDAO();
		
	}
	
	
	public Collection<Income> viewAllIncome()
	{
	    return BusinessDeleagte.getInstance().viewAllIncome();
	}
	
    public Collection<Income> viewIncomeByCustomer(String customerName)
    {
        return BusinessDeleagte.getInstance().viewIncomeByCustomer(customerName);
    }
    
    public Collection<Income> viewIncomeByCompany(String companyName)
    {
        return BusinessDeleagte.getInstance().viewIncomeByCompany(companyName);
    }
	
	/**
	 * This function takes a company and add it to the database. 
	 * in case the company name already exist there would be an ManagerSQLException.
	 * @param company
	 * @throws ThreadException
	 * @throws ManagerSQLException
	 */
	public void createCompany(Company company) throws ThreadException, ManagerSQLException{
		
		if(!companyDB.isCompanyNameExist(company))
			companyDB.createCompany(company);
		else
			throw new ManagerSQLException("Company already exist");
	
	}
	
	/**
	 * This function remove the given company in the database.
	 * If the company doesn't exits there would be ManagerSQLException.
	 * @param company
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void removeCompany(Company company) throws ManagerSQLException, ThreadException{
		companyDB.removeCompany(company);
	}
	
	/**
	 * This company updates a given company's except company name   
	 * @param company
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void updateCompany(Company company) throws ManagerSQLException, ThreadException{
		companyDB.updateCompany(company);
	}
	
	/**
	 * This function return the company that belong to the ID that was given
	 * if the id have no company associate, there would be a   ManagerSQLException.
	 * @param id
	 * @return Company
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Company getCompany(long id) throws ManagerSQLException, ThreadException{
		return companyDB.getCompany(id);
	}
	
	/**
	 * This function return a collection with all the companies in the system. 
	 * @return Collection<Company> 
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Company> getAllCompanies() throws ManagerSQLException, ThreadException{
		return companyDB.getAllCompanies();
	}
	
	/**
	 * This function create a new customer in the system.
	 * If the customer name already exist there would be a  ManagerSQLException.
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void createCustomer(Customer customer) throws ManagerSQLException, ThreadException{
		if(!customerDB.isCustomerNameExist(customer))
			customerDB.createCustomer(customer);
		else
			throw new ManagerSQLException("Customer name already exist");
	}
	
	/**
	 * This function removes a customer from the system.
	 * If that user isn't exist there would be a ManagerSQLException
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void removeCustomer(Customer customer) throws ManagerSQLException, ThreadException{
		customerDB.removeCustomer(customer);
	}

	/**
	 * This function updates a customer password.
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public void updateCustomer(Customer customer) throws ManagerSQLException, ThreadException{
		customerDB.updateCustomer(customer);
	}
	
	/**
	 * This function return a customer that associated with the given ID.
	 * @param id
	 * @return Customer
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Customer getCustomer(long id) throws ManagerSQLException, ThreadException{
		return customerDB.getCustomer(id);
	}	
	
	/**
	 * This function returns a collection of Customer with all the customers in the system.
	 * @return Collection<Customer>
	 * @throws ManagerSQLException
	 * @throws ThreadException
	 */
	public Collection<Customer> getAllCustomer() throws ManagerSQLException, ThreadException{
		return customerDB.getAllCustomers();
	}	
}
