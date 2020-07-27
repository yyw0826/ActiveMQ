package com.yyw.jms.tx;

import com.yyw.jms.comment.StaticName;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * Queue 消费者消息事务
 */
public class JMSQueueConsumentTX {
    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(StaticName.DEFAULT_NAME);
        Connection connection = factory.createConnection();
        connection.start();
        // 消费者这边开不开启事务意义不大，重要性偏签收
        // 如果开启了事务，必须执行commit操作，否则消息会被重复消费
        // 如果开启了事务，就算设置了手动签收，事务的优先级大于签收，就算签收了不提交事务，也会被重复消费，
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(StaticName.QUEUENAME);
        MessageConsumer consumer = session.createConsumer(queue);
        Message msg = consumer.receive();
        while (null != msg) {
            TextMessage message = (TextMessage) msg;
            System.out.println("收到的消息: " + message.getText());
            message.acknowledge();  // 如果开启了手动签收，就要手动签收下，（如果开启了事务，事务的优先级大于签收）
            msg = consumer.receive(1000);
        }
        //session.commit();
        consumer.close();
        session.close();
        connection.close();
    }
}
