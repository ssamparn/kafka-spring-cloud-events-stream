package com.explore.springcloudeventsstreamplayground.sec14.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumerConfig {

    @Bean
    public Consumer<List<String>> highThroughputConsumer() {
        var counter = new AtomicInteger(0);
        return list -> {
            log.info("Batch received: {}, Total processed: {}", list.size(), counter.addAndGet(list.size()));
        };
    }
}
