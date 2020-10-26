package com.trx.rabbitmq.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
//消费者
public class MyConsumer {

    private final static String EXCHANGE_NAME="SIMPLE_EXCHANGE";
    private final static String QUEUE_NAME="SIMPLE_QUEUE";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            //连接IP
            factory.setHost("192.168.124.44");
            //默认监听端口
            factory.setPort(5672);
            //虚拟机
            factory.setVirtualHost("/");
            //设置访问的用户
            factory.setUsername("admin");
            factory.setPassword("admin");
            //建立连接
            Connection conn = factory.newConnection();
            //创建消息通道
            Channel channel = conn.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME,"direct",false,false,null);
            //声明队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            System.out.println("Waiting for message....");
            //绑定队列和交换机
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"gupao.best");

            //创建消费者
            Consumer consumer = new DefaultConsumer(channel){

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body,"UTF-8");
                    System.out.println("Received message:"+msg);
//                    System.out.println("Received message : '" + msg + "'");
                    System.out.println("consumerTag:"+consumerTag);
                    System.out.println("deliveryTag:"+envelope.getDeliveryTag());
                }
            };

            //开始获取消息
            channel.basicConsume(QUEUE_NAME,true,consumer);
        }catch(Exception e){

            e.printStackTrace();
        }

    }
}
