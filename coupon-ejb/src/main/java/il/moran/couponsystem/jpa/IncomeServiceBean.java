package il.moran.couponsystem.jpa;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import il.moran.couponsystem.entity.Income;
import il.moran.couponsystem.entity.IncomeType;

@Stateless
public class IncomeServiceBean implements IncomeService
{
    @PersistenceContext(name = "couponSystem")
    private EntityManager em;
    
    @Override
    public void storeIncome(Income income)
    {
        em.persist(income);
        em.flush();
    }

    @Override
    public Collection<Income> viewAllIncome()
    {
        TypedQuery<Income> query = em.createQuery("Select i FROM income i", Income.class);
        return query.getResultList();
    }

    @Override
    public Collection<Income> viewIncomeByCustomer(String customerName)
    {
    
        return getIncomeByName(customerName)
            .parallelStream()
            .filter(i -> i.getDescription() == IncomeType.CUSTOMER_PURCHASE)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Income> viewIncomeByCompany(String companyName)
    {
        return getIncomeByName(companyName)
        .parallelStream()
        .filter(i -> i.getDescription() == IncomeType.COMPANY_NEW_COUPON
                  || i.getDescription() == IncomeType.COMPANY_UPDATE_COUPON)
        .collect(Collectors.toList());
    }
    
    private Collection<Income> getIncomeByName(String name)
    {
        TypedQuery<Income> query = em.createQuery("Select i FROM income i WHERE i.name = :name", Income.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

}
