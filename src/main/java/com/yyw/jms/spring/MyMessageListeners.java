package com.yyw.jms.spring;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class MyMessageListeners implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if (null != message && message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            try {
                System.out.println(msg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
