package com.explore.springcloudeventsstreamplayground.sec16.processor;

import com.explore.springcloudeventsstreamplayground.sec16.dto.DigitalDelivery;
import com.explore.springcloudeventsstreamplayground.sec16.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Gatherers;

@Slf4j
@Configuration
@ConditionalOnProperty(
        name = "processing.mode",
        havingValue = "unordered"
)
public class UnorderedMessageProcessorConfig {

    /**
     * Header Routing
     * */
    private static final String SPRING_CLOUD_STREAM_BINDING_DESTINATION = "spring.cloud.stream.sendto.destination";

    /**
     * Custom binding details for header routing
     * */
    private static final String DIGITAL_DELIVERY_OUTPUT_BINDING = "digital-delivery-output-binding";
    private static final String PHYSICAL_DELIVERY_OUTPUT_BINDING = "physical-delivery-output-binding";

    private static final int MAX_CONCURRENCY = 500;

    @Bean
    public Function<List<Order>, List<Message<Object>>> orderProcessor(DeliveryService deliveryService) {
        return orders -> orders.stream()
                .gather(Gatherers.mapConcurrent(MAX_CONCURRENCY, deliveryService::toDelivery))
                .map(this::toMessage)
                .toList();
    }

    private Message<Object> toMessage(Object payload) {
        log.info("dispatching: {}", payload);
        var destination = (payload instanceof DigitalDelivery) ? DIGITAL_DELIVERY_OUTPUT_BINDING : PHYSICAL_DELIVERY_OUTPUT_BINDING;
        return MessageBuilder.withPayload(payload)
                .setHeader(SPRING_CLOUD_STREAM_BINDING_DESTINATION, destination)
                .build();
    }
}
