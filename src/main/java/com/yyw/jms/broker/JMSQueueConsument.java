package com.yyw.jms.broker;

import com.yyw.jms.comment.StaticName;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消息消费者
 */
public class JMSQueueConsument {
    public static void main(String[] args) throws JMSException, IOException {
        final Logger logger = LoggerFactory.getLogger(JMSQueueConsument.class);
        logger.info("我是王五....");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(StaticName.QUEUENAME);
        MessageConsumer consumer = session.createConsumer(queue);
        // 第一种方式,阻塞式
        /*Message message = consumer.receive();
        while (null != message) {
            TextMessage msg = (TextMessage) message;
            logger.info("收到的消息:{}",msg.getText());
            message = consumer.receive(5000L);
        }*/
        // 第二种方式，异步非阻塞
        consumer.setMessageListener( meg -> {
            if (null != meg && meg instanceof TextMessage) {
                TextMessage msg = (TextMessage) meg;
                try {
                    logger.info("收到的消息:{}",msg.getText());
                } catch (JMSException jmsException) {
                    jmsException.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
