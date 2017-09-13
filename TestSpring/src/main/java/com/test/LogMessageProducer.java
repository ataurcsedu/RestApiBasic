/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

/**
 *
 * @author Rezwan
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;

import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

/**
 *
 * @author Ataur Rahman
 */
public class LogMessageProducer  {
    
    public static String topicName = "topic";
    public static String clientId = "clientId";
    
    public static void sendMessage() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:2020");
        connectionFactory.setTrustAllPackages(true);
        Destination destination = new ActiveMQQueue("someQueue");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        Destination d = session.createTopic(topicName);
        try {
            String payload = "this is sent text..............................";
            
            MessageProducer producer = session.createProducer(d);
            MapMessage mapM = session.createMapMessage();
            mapM.setString("id", "123");
            mapM.setString("message", payload);
            producer.send(mapM);
            session.close();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
 
    }
    
    
}

