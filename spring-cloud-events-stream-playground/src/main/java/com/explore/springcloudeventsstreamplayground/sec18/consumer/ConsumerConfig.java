package com.explore.springcloudeventsstreamplayground.sec18.consumer;

import com.explore.springcloudeventsstreamplayground.sec18.exceptions.InputValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumerConfig {

    private final OrderService orderService;

    public ConsumerConfig(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    public Consumer<Message<Integer>> consumer() {
        return this::validateAndProcess;
    }

    private void validateAndProcess(Message<Integer> message) {
        log.info("message: {}", message);
        var orderId = message.getPayload();
        if (orderId < 1) {
            throw new InputValidationException(orderId);
        }
        this.orderService.saveOrder(orderId);
    }
}
