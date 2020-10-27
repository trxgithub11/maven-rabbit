package com.trx.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trx.entity.Merchant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:trxmq.properties")
public class RabbitSender {

    @Value("${com.trx.directexchange}")
    private String directExchange;

    @Value("${com.trx.topicexchange}")
    private String topicExchange;

    @Value("${com.trx.fanoutexchange}")
    private String fanoutExchange;

    @Value("${com.trx.directroutingkey}")
    private String directRoutingKey;

    @Value("${com.trx.topicroutingkey1}")
    private String topicRoutingKey1;

    @Value("${com.trx.topicroutingkey2}")
    private String topicRoutingKey2;


    // 自定义的模板，所有的消息都会转换成JSON发送
    @Autowired
    AmqpTemplate trxTemplate;

    public void send() throws JsonProcessingException {
        Merchant merchant =  new Merchant(1001,"a direct msg : 中原镖局","汉中省解放路266号");
        trxTemplate.convertAndSend(directExchange,directRoutingKey, merchant);

        trxTemplate.convertAndSend(topicExchange,topicRoutingKey1, "a topic msg : shanghai.trx.teacher");
        trxTemplate.convertAndSend(topicExchange,topicRoutingKey2, "a topic msg : changsha.trx.student");

        // 发送JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(merchant);
        System.out.println(json);
        trxTemplate.convertAndSend(fanoutExchange,"", json);
    }


}
