package com.explore.springcloudeventsstreamplayground.sec12.consumer;

import com.explore.springcloudeventsstreamplayground.sec12.dto.DigitalDelivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class DigitalDeliveryConsumerConfig {

    @Bean
    public Consumer<DigitalDelivery> digitalOrdersConsumer() {
        return digitalDelivery -> log.info("📧 Received digital order → {}", digitalDelivery);
    }
}
