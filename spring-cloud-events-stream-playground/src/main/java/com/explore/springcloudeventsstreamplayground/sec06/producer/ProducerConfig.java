package com.explore.springcloudeventsstreamplayground.sec06.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ProducerConfig {

    @Bean
    public Supplier<String> producer() {
        var counter = new AtomicLong(0);
        return () -> {
            var msg = "msg-" + counter.incrementAndGet();
            log.info("Produced: {}", msg);
            return msg;
        };
    }
}
