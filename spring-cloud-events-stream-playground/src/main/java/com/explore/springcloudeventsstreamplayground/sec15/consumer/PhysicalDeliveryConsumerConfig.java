package com.explore.springcloudeventsstreamplayground.sec15.consumer;

import com.explore.springcloudeventsstreamplayground.sec15.dto.PhysicalDelivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class PhysicalDeliveryConsumerConfig {

    @Bean
    public Consumer<PhysicalDelivery> physicalOrdersConsumer() {
        return physicalDelivery -> log.info("📦 Received physical order → {}", physicalDelivery);
    }
}
