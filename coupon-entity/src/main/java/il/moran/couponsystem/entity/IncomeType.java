package il.moran.couponsystem.entity;

public enum IncomeType
{
    CUSTOMER_PURCHASE("Customer - purchase Coupon"),
    COMPANY_NEW_COUPON("Company - create new coupon"),
    COMPANY_UPDATE_COUPON("Company - update coupon details");
    
    private final String description;
    
    private IncomeType(String description)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
}
