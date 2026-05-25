package com.explore.springcloudeventsstreamplayground.sec03.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class MessageConsumerConfig {

    @Bean
    public Consumer<Message<String>> messageConsumer() {
        return this::handleMessage;
    }

    private void handleMessage(Message<String> message) {
        log.info("Message: {}", message);
        var key = message.getHeaders().get(KafkaHeaders.RECEIVED_KEY);
        var payload = message.getPayload();
        log.info("Key: {}, Payload: {}", key, payload);
    }
}
