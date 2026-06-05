package com.explore.springcloudeventsstreamplayground.sec15.producer;

import com.explore.springcloudeventsstreamplayground.sec15.dto.Order;
import com.explore.springcloudeventsstreamplayground.sec15.dto.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ProducerConfig {

    @Bean
    public Supplier<Message<Order>> orderProducer() {
        var counter = new AtomicInteger(0);
        return () -> {
            int id = counter.incrementAndGet();
            Message<Order> message = this.toMessage(id);
            log.info("Order produced: {}", message);
            return message;
        };
    }

    private Message<Order> toMessage(int id) {
        ProductType productType = id % 2 == 0 ? ProductType.PHYSICAL : ProductType.DIGITAL;
        Order order = new Order(id, id, ThreadLocalRandom.current().nextInt(1, 1000), productType);
        return MessageBuilder.withPayload(order).setHeader(KafkaHeaders.KEY, id).build();
    }
}
