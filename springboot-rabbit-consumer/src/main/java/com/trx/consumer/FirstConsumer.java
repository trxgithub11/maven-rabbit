package com.trx.consumer;

import com.trx.entity.Merchant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:trxmq.properties")
@RabbitListener(queues = "${com.trx.firstqueue}", containerFactory="rabbitListenerContainerFactory")
public class FirstConsumer {

    @RabbitHandler
    public void process(@Payload Merchant merchant){

        System.out.println("First Queue received msg : " + merchant.getName());
        
    }

}
