package com.scb.assignmentone.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final Logger logger = LogManager.getFormatterLogger(Producer.class);
    private static String TOPIC = "kafka";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produce(String data) {
        logger.info("Produce Topic: %s - Message: %s", TOPIC, data);
        this.kafkaTemplate.send(TOPIC, data);
    }

}
