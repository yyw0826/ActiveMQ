package com.yyw.jms.persistence;

import com.yyw.jms.comment.StaticName;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.IOException;
/**
 * topic持久化，默认是不持久
 */
public class JMSTopicConsument {
    public static void main(String[] args) throws JMSException, IOException {
        final Logger log = LoggerFactory.getLogger(JMSTopicConsument.class);
        log.info("我是张三....");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(StaticName.DEFAULT_NAME);
        Connection connection = factory.createConnection();
        connection.setClientID("zhangsan");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(StaticName.TOPICNAME);
        // 创建订阅者，实现持久化
        TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, "romber...");
        durableSubscriber.setMessageListener( e -> {
            if (null != e && e instanceof TextMessage) {
                TextMessage msg = (TextMessage) e;
                try {
                    System.out.println("收到的消息:" + msg.getText());
                } catch (JMSException jmsException) {
                    jmsException.printStackTrace();
                }
            }
        });

        System.in.read();
        durableSubscriber.close();
        session.close();
        connection.close();
    }
}
