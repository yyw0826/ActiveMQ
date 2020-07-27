package com.yyw.jms.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
public class SpringMQ_Product {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Product product = (SpringMQ_Product)ctx.getBean("springMQ_Product");
        product.jmsTemplate.send(e -> e.createTextMessage("Spring-ActiveMQ topic 监听。。 生产消息成功1111...."));
        System.out.println("发送消息成功");
    }
}
