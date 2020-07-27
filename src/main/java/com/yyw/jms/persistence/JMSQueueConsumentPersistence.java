package com.yyw.jms.persistence;

import com.yyw.jms.comment.StaticName;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * Queue 消费者消息持久化
 */
public class JMSQueueConsumentPersistence {
    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(StaticName.DEFAULT_NAME);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(StaticName.QUEUENAME);
        MessageConsumer consumer = session.createConsumer(queue);
        // 第二种方式，异步非阻塞
        consumer.setMessageListener( meg -> {
            if (null != meg && meg instanceof TextMessage) {
                TextMessage msg = (TextMessage) meg;
                try {
                    System.out.println("收到的消息:" + msg.getText());
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
