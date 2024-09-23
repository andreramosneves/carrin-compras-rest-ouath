/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Component
public class NotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(String message) {
        kafkaTemplate.send("AppOrderTopic", message);
    }
    
    
    @KafkaListener(
            topics = "AppOrderTopic",
            groupId = "groupId"
    )
    public void listener(String data) {
        System.out.println("Listener received:" + data);
    }
    
    
    /*  Código para Consummir todos registro do Meu Broker
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        records.forEach(record -> {
            System.out.printf("Offset: %d, Key: %s, Value: %s%n", record.offset(), record.key(), record.value());
        });
    */
    
    
}
