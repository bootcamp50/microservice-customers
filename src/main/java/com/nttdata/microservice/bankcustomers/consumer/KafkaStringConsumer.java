package com.nttdata.microservice.bankcustomers.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.nttdata.microservice.bankcustomers.services.IPersonService;

@Component
public class KafkaStringConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaStringConsumer.class);
    
    
    @Autowired
    private IPersonService service;
    

    @KafkaListener(topics = "TOPIC-DEMO" , groupId = "group_id")
    public void consume(String message) {
        logger.info("Consuming Message {}", message);
        String[] values = message.split(":");
        String personCode = values[0];
        String comment = values[1];
        service.updateComment(personCode, comment).subscribe();
    }

}
