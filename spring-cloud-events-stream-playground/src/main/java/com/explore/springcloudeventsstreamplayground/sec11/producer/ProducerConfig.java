package com.explore.springcloudeventsstreamplayground.sec11.producer;

import com.explore.springcloudeventsstreamplayground.sec11.dto.Order;
import com.explore.springcloudeventsstreamplayground.sec11.dto.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ProducerConfig {

    @Bean
    public Supplier<Order> orderProducer() {
        var counter = new AtomicInteger(0);
        return () -> {
            int id = counter.incrementAndGet();
            ProductType productType = id % 2 == 0 ? ProductType.PHYSICAL : ProductType.DIGITAL;
            Order order = new Order(id, id, ThreadLocalRandom.current().nextInt(1, 1000), productType);
            log.info("Order produced: {}", order);
            return order;
        };
    }
}
