package com.explore.springcloudeventsstreamplayground.sec02.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Configuration
public class ReactiveConsumerConfig {

    @Bean
    public Function<Flux<String>, Mono<Void>> reactiveConsumer() {
        return stringFlux -> stringFlux.doOnNext(message -> log.info("received: {}", message))
                .then();
    }
}