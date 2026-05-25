package com.explore.springcloudeventsstreamplayground.sec06.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumerConfig {

    @Bean
    public Consumer<String> consumer(MessageCounter counter) {
        return msg -> {
            counter.increment();
            log.info("received: {}", msg);
        };
    }
}
