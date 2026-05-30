package com.explore.springcloudeventsstreamplayground.sec11.processor;

import com.explore.springcloudeventsstreamplayground.sec11.dto.DigitalDelivery;
import com.explore.springcloudeventsstreamplayground.sec11.dto.Order;
import com.explore.springcloudeventsstreamplayground.sec11.dto.PhysicalDelivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ProcessorConfig {

    private final StreamBridge streamBridge;

    public ProcessorConfig(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    private static final String DIGITAL_DELIVERY_OUTPUT_BINDING = "digital-delivery-out-0";
    private static final String PHYSICAL_DELIVERY_OUTPUT_BINDING = "physical-delivery-out-0";

    @Bean
    public Consumer<Order> orderProcessor() {
        return this::dispatch;
    }

    private void dispatch(Order order) {
        log.info("dispatching order: {}", order);
        switch (order.productType()) {
            case DIGITAL -> this.streamBridge.send(DIGITAL_DELIVERY_OUTPUT_BINDING, this.createDigitalDelivery(order));
            case PHYSICAL -> this.streamBridge.send(PHYSICAL_DELIVERY_OUTPUT_BINDING, this.createPhysicalDelivery(order));
        }
    }

    private DigitalDelivery createDigitalDelivery(Order order) {
        return new DigitalDelivery(
                order.id(),
                "user.%d@gmail.com".formatted(order.customerId())
        );
    }

    private PhysicalDelivery createPhysicalDelivery(Order order) {
        return new PhysicalDelivery(
                order.id(),
                "%d street".formatted(order.customerId()),
                "%d city".formatted(order.customerId())
        );
    }
}
