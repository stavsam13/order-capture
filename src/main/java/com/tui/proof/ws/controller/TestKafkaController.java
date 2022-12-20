package com.tui.proof.ws.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tui.proof.kafka.KafkaServiceProducer;
import com.tui.proof.model.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "v1")
public class TestKafkaController {

    @Autowired
    KafkaServiceProducer kafkaServiceProducer;

    @PostMapping(value="/kafka")
    public ResponseEntity<String> publish(@RequestBody NotificationDto notificationDto) throws JsonProcessingException {
        kafkaServiceProducer.sendMessage(notificationDto);
        return ResponseEntity.ok("Message Json sent to kafka topic");
    }
}
