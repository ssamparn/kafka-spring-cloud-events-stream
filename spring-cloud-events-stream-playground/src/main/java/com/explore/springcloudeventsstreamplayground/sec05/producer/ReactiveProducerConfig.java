package com.explore.springcloudeventsstreamplayground.sec05.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ReactiveProducerConfig {

    @Bean
    public Supplier<Flux<String>> reactiveProducer() {
        return () -> Flux.interval(Duration.ofSeconds(1))
                .map(i -> "msg-" + i)
                .doOnNext(msg -> log.info("Sending message: {}", msg))
                .doOnError(err -> log.error("Producer stream error", err));
    }

}
