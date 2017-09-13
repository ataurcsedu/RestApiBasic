/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;

/**
 *
 * @author Ataur
 */
public class Listener extends Thread{

    @Override
    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:2020");
            connectionFactory.setTrustAllPackages(true);
            connectionFactory.setDispatchAsync(true);
            
            Destination destination = new ActiveMQQueue("someQueue");
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            try {
                MessageConsumer consumer = session.createConsumer(destination);
                connection.start();
                //consumer.setMessageListener(new Hello());
                while(true){
                    System.out.println("Waiting for message ==== ");
                    ActiveMQObjectMessage msg = (ActiveMQObjectMessage) consumer.receive();
                    System.out.println(msg);
                    LogMessage m = (LogMessage)msg.getObject();
                    System.out.println("Received: " + m.getMessage());
                }
                //session.close();
                
            } catch (JMSException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public static void main(String[] args) throws JMSException {
        Listener l = new Listener();
        //l.setDaemon(true);
        l.start();
    }
    
}
