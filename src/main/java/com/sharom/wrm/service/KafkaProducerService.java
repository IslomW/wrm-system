package com.sharom.wrm.service;

import com.sharom.wrm.modules.user.model.dto.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(UserRegisteredEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("welcome-notification-topic", json);

            System.out.println("Kafka yuborildi: " + json);

        } catch (Exception e) {
            System.out.println("❌ Kafka xato: " + e.getMessage());
        }
    }
}