package com.trx.rabbitmq.demo.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class DlxProducer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        Connection   conn = factory.newConnection();
        Channel channel = conn.createChannel();
        String msg = "Hello world, Rabbit MQ,DLX MSG";
        //设置属性，消息十秒钟过期
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().deliveryMode(2).contentEncoding("UTF-8").expiration("10000").build();
        //发送消息
        channel.basicPublish("","GP_ORI_USE_QUEUE",properties,msg.getBytes());
        channel.close();
        conn.close();
    }
 ;

}
