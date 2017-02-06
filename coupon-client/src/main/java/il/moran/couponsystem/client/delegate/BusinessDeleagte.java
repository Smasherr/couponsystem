package il.moran.couponsystem.client.delegate;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import il.moran.couponsystem.entity.Income;
import il.moran.couponsystem.jpa.IncomeService;

public class BusinessDeleagte extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static String QUEUE_NAME = "jms/logging";
	
    @Resource(mappedName = QUEUE_NAME)
    private Queue loggingQueue;

    @Resource
    private ConnectionFactory connectionFactory;
    
    @EJB
    private IncomeService incomeService;
    
    private static BusinessDeleagte instance;
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
    	instance = this;
    	super.init(config);
    }
    
    public static BusinessDeleagte getInstance()
    {
    	return instance;
    }
	
	public void storeIncome(Income income)
	{
        try
        {
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(loggingQueue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a message
            ObjectMessage objMessage = session.createObjectMessage(income);
            
            // Tell the producer to send the message
            producer.send(objMessage);
        }
        catch (JMSException e)
        {
            //ignore
        }
	}
	
    public Collection<Income> viewAllIncome()
    {
        return incomeService.viewAllIncome();
    }
    
    public Collection<Income> viewIncomeByCustomer(String customerName)
    {
        return incomeService.viewIncomeByCustomer(customerName);
    }
    
    public Collection<Income> viewIncomeByCompany(String companyName)
    {
        return incomeService.viewIncomeByCompany(companyName);
    }
}
