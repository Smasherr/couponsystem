package il.moran.couponsystem.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import il.moran.couponsystem.entity.Income;
import il.moran.couponsystem.jpa.IncomeService;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName  = "destinationType", 
                                  propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName  = "connectionFactoryJndiName",
                                  propertyValue = "jms/storeIncome")
    }
)
public class IncomeConsumerBean implements MessageListener {

    @EJB
    IncomeService incomeService;
    
    @Override
    public void onMessage(Message message)
    {
        try
        {
            ObjectMessage incomeMessage = (ObjectMessage) message;
            Income incomeEntity = new Income((Income) incomeMessage.getObject());
            incomeService.storeIncome(incomeEntity);
        }
        catch (JMSException e)
        {
            throw new IllegalStateException(e);
        }
    }
}