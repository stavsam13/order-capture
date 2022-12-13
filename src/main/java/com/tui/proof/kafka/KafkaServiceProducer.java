package com.tui.proof.kafka;

import com.tui.proof.model.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaServiceProducer.class);

    private KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public KafkaServiceProducer(KafkaTemplate<String, NotificationDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(NotificationDto notificationDto){
        LOGGER.info(String.format("Message JSON format sent: %s",notificationDto.toString()));
        Message<NotificationDto> message = MessageBuilder
                .withPayload(notificationDto)
                .setHeader(KafkaHeaders.TOPIC,"orderTopic")
                .build();

        kafkaTemplate.send(message);
    }
}
