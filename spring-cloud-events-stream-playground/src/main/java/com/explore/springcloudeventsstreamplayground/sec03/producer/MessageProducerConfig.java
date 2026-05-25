package com.explore.springcloudeventsstreamplayground.sec03.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class MessageProducerConfig {

    @Bean
    public Supplier<Message<String>> messageProducer() {
        var counter = new AtomicInteger(0);
        return () -> {
            var msg = this.buildMessage(counter.incrementAndGet());
            log.info("produced: {}", msg);
            return msg;
        };
    }

    private Message<String> buildMessage(Integer input) {
        return MessageBuilder.withPayload("msg-" + input)
                .setHeader(KafkaHeaders.KEY, "key-" + input)
                .setHeader("trace-id", "trace-" + input)
                .build();
    }
}
