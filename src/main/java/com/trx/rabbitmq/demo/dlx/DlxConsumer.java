package com.trx.rabbitmq.demo.dlx;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class DlxConsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        //建立连接
        Connection conn = factory.newConnection();
        //创建消息通道
        Channel channel = conn.createChannel();
        //指定队列的死信交换机
        Map<String,Object> arguments = new HashMap<String,Object>();
        arguments.put("x-dead-letter-exchange","GP_DEAD_LETTER_EXCHANGE");
        //声明队列
        channel.queueDeclare("GP_ORI_USE_QUEUE",false,false,false,arguments);
        //声明死信交换机
        channel.exchangeDeclare("GP_DEAD_LETTER_EXCHANGE","topic",false,false,false,null);
        //声明死信队列
        channel.queueDeclare("GP_DEAD_LETTER_QUEUE",false,false,false,null);
        channel.queueBind("GP_DEAD_LETTER_QUEUE","GP_DEAD_LETTER_EXCHANGE","#");
        System.out.println("Waiting for message....");
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println("Received message:"+msg);
            }
        };
        channel.basicConsume("GP_DEAD_LETTER_QUEUE",true,consumer);
    }
}
