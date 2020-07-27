package com.yyw.jms.persistence;

import com.yyw.jms.activemq.JMSQueueConsument;
import com.yyw.jms.comment.StaticName;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Queue 消息生产者持久化
 */

public class JMSQueueProducterPersistence {
    public static void main(String[] args) throws JMSException {
        Logger log = LoggerFactory.getLogger(JMSQueueProducterPersistence.class);
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(StaticName.DEFAULT_NAME);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(StaticName.QUEUENAME);
        MessageProducer producer = session.createProducer(queue);
        // producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);  // 不持久化，默认时持久化
        for (int i = 0; i < 6; i++) {
            producer.send(session.createTextMessage("msg--->" + (i+1)));
        }
        log.info("消息发送成功");
        producer.close();
        session.close();
        connection.close();
    }
}
