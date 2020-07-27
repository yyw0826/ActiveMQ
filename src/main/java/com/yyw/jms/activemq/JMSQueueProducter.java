package com.yyw.jms.activemq;

import com.yyw.jms.comment.StaticName;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * 消息生产者
 */

public class JMSQueueProducter {
    public static void main(String[] args) throws JMSException {
        Logger log = LoggerFactory.getLogger(JMSQueueConsument.class);
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(StaticName.DEFAULT_NAME);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(StaticName.QUEUENAME);
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 6; i++) {
            producer.send(session.createTextMessage("msg--->" + (i+1)));
        }
        log.info("消息发送成功");
        producer.close();
        session.close();
        connection.close();
    }
}
