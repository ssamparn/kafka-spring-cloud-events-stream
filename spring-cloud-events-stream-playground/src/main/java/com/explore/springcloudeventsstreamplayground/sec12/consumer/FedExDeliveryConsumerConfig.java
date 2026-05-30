package com.explore.springcloudeventsstreamplayground.sec12.consumer;

import com.explore.springcloudeventsstreamplayground.sec12.dto.PhysicalDelivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class FedExDeliveryConsumerConfig {

    @Bean
    public Consumer<PhysicalDelivery> fedexPhysicalOrdersConsumer() {
        return physicalDelivery -> log.info("📦 Received physical order via FedEx → {}", physicalDelivery);
    }
}
