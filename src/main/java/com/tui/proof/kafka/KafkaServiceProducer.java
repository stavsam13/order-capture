package com.tui.proof.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.model.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaServiceProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaServiceProducer.class);

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    public KafkaServiceProducer (KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(NotificationDto notificationDto) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(notificationDto);
        kafkaTemplate.send("orderTopic", orderAsMessage);

        LOGGER.info("food order produced {}", orderAsMessage);

    }
}
