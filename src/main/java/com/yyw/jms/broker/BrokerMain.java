package com.yyw.jms.broker;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;

/**
 * 相当于一个ActiveMQ服务器实例。说白了，Broker其实就是实现了用代码的形式启动ActiveMQ将MQ嵌入到Java代码中，
 * 以便随时用随时启动，在用的时候再去启动这样能节省了资源，也保证了可用性。
 * 这种方式，我们实际开发中很少采用，因为他缺少太多了东西，如：日志，数据存储等等
 */
public class BrokerMain {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setPopulateJMSXUserID(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
