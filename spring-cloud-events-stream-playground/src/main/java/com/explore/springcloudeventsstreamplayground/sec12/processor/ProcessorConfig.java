package com.explore.springcloudeventsstreamplayground.sec12.processor;

import com.explore.springcloudeventsstreamplayground.sec12.dto.DigitalDelivery;
import com.explore.springcloudeventsstreamplayground.sec12.dto.PhysicalDelivery;
import com.explore.springcloudeventsstreamplayground.sec12.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Slf4j
@Configuration
public class ProcessorConfig {

    /**
     * Header Routing
     * */
    private static final String SPRING_CLOUD_STREAM_BINDING_DESTINATION = "spring.cloud.stream.sendto.destination";

    /**
     * Custom binding details for header routing
     * */
    private static final String DIGITAL_DELIVERY_OUTPUT_BINDING = "digital-delivery-output-binding";
    private static final String FEDEX_PHYSICAL_DELIVERY_OUTPUT_BINDING = "fedex-physical-delivery-output-binding";
    private static final String USPS_PHYSICAL_DELIVERY_OUTPUT_BINDING = "usps-physical-delivery-output-binding";

    private final CarrierAvailabilityService carrierAvailabilityService;

    @Autowired
    public ProcessorConfig(final CarrierAvailabilityService carrierAvailabilityService) {
        this.carrierAvailabilityService = carrierAvailabilityService;
    }

    @Bean
    public Function<Order, Message<?>> orderProcessor() {
        return this::dispatch;
    }

    private Message<?> dispatch(Order order) {
        log.info("dispatching order: {}", order);
        return switch (order.productType()) {
            case DIGITAL -> this.toMessage(this.createDigitalDelivery(order), DIGITAL_DELIVERY_OUTPUT_BINDING);
            case PHYSICAL -> {
                var outputBinding = carrierAvailabilityService.isFedexAvailable() ? FEDEX_PHYSICAL_DELIVERY_OUTPUT_BINDING : USPS_PHYSICAL_DELIVERY_OUTPUT_BINDING;
                yield this.toMessage(this.createPhysicalDelivery(order), outputBinding);
            }
        };
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

    private Message<?> toMessage(Object payload, String bindingDestination) {
        return MessageBuilder
                .withPayload(payload)
                .setHeader(SPRING_CLOUD_STREAM_BINDING_DESTINATION, bindingDestination)
                .build();
    }
}
