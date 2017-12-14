package Messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
 
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
 
public class Producer {
	Destination destination;
	Session session;
	Connection connection;
	
	public Producer() {
		init();
	}
	
	public  void init() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616");
        destination = new ActiveMQQueue("someQueue");
      	try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false,
	                Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
        
	}
	
	public void broadcastMsg(String message) {
		Message msg;
		try {
			msg = session.createTextMessage(message);
			MessageProducer producer = session.createProducer(destination);
	        System.out.println("Send text '" + message + "'");
	        producer.send(msg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}