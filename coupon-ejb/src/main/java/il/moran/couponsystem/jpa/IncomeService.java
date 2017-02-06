package il.moran.couponsystem.jpa;

import java.util.Collection;

import il.moran.couponsystem.entity.Income;

public interface IncomeService
{
    public void storeIncome(Income income);
    
    public Collection<Income> viewAllIncome();
    
    public Collection<Income> viewIncomeByCustomer(String customerName);
    
    public Collection<Income> viewIncomeByCompany(String companyName);
}
