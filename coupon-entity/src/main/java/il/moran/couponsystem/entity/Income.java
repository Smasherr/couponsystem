package il.moran.couponsystem.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Income implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    
    private String name;
    
    private Date date;
    
    @Enumerated(EnumType.STRING)
    private IncomeType description;
    
    private double amount;

    public Income()
    {
    }
    
    public Income(String name, Date date, IncomeType description, double amount)
    {
        this.name = name;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }
    
    

    public Income(Income otherIncome)
    {
        this.name = otherIncome.name;
        this.date = otherIncome.date;
        this.description = otherIncome.description;
        this.amount = otherIncome.amount;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public IncomeType getDescription()
    {
        return description;
    }

    public void setDescription(IncomeType description)
    {
        this.description = description;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    } 
}
