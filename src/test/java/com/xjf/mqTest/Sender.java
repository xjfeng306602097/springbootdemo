package com.xjf.mqTest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("xiaojunfeng");
        factory.setPassword("abcd1234");
        Connection conn = factory.newConnection();
        // 避免长连接，AMQP使用渠道这一概念避免长连接
        Channel channel = conn.createChannel();
        // 队列名，是否持久化，独占的queue，不使用时是否删除，其他参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "hello world";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] Sent '"+message+"'");
        // 回收资源
        channel.close();
        conn.close();
    }
}
