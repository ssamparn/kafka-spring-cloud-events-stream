package com.explore.springcloudeventsstreamplayground.sec13.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ProducerConfig {

    @Bean
    public Supplier<String> simpleProducer() {
        var counter = new AtomicInteger(0);
        return () -> {
            var message = "message-" + counter.incrementAndGet();
            log.info("Producing: '{}'", message);
            return message;
        };
    }
}
