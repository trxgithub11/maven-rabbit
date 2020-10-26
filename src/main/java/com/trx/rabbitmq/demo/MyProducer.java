package com.trx.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MyProducer {

    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            //连接IP
            factory.setHost("192.168.124.44");
            //连接端口
            factory.setPort(5672);
            //虚拟机
            factory.setVirtualHost("/");
            //用户
            factory.setUsername("admin");
            factory.setPassword("admin");
            //建立连接
            Connection conn = factory.newConnection();
            //创建消息通道
            Channel channel = conn.createChannel();
            //发送消息
            String msg = "Hello world,Rabbit MQ";
            channel.basicPublish(EXCHANGE_NAME,"gupao.best",null,msg.getBytes());
            channel.close();
            conn.close();






        }catch(Exception e){

            e.printStackTrace();
        }
    }
}