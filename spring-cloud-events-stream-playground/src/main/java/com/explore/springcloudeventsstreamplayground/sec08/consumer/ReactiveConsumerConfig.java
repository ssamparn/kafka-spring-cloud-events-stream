package com.explore.springcloudeventsstreamplayground.sec08.consumer;

import com.explore.springcloudeventsstreamplayground.sec08.dto.Notification;
import com.explore.springcloudeventsstreamplayground.sec08.dto.Payment;
import com.explore.springcloudeventsstreamplayground.sec08.dto.Shipment;
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
    public Function<Flux<Payment>, Mono<Void>> paymentConsumer() {
        return paymentFlux -> paymentFlux
                .doOnNext(this::logReceived)
                .then();
    }

    @Bean
    public Function<Flux<Shipment>, Mono<Void>> shipmentConsumer() {
        return shipmentFlux -> shipmentFlux
                .doOnNext(this::logReceived)
                .then();
    }

    @Bean
    public Function<Flux<Notification>, Mono<Void>> notificationConsumer() {
        return notificationFlux -> notificationFlux
                .doOnNext(this::logReceived)
                .then();
    }

    private void logReceived(Object payload) {
        log.info("received: {}", payload);
    }
}
