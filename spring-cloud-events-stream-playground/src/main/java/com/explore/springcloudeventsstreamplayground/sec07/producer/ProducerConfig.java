package com.explore.springcloudeventsstreamplayground.sec07.producer;

import com.explore.springcloudeventsstreamplayground.sec07.dto.Order;
import com.explore.springcloudeventsstreamplayground.sec07.dto.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ProducerConfig {

    private final AtomicInteger counter = new AtomicInteger();
    private final Random random = new Random();

    @Bean
    public Supplier<Order> orderProducer() {
        return () -> {
            var order = new Order(
                    counter.incrementAndGet(),
                    random.nextInt(1000),
                    random.nextInt(500) + 1,
                    random.nextBoolean() ? ProductType.DIGITAL : ProductType.PHYSICAL
            );
            log.info("produced: {}", order);
            return order;
        };
    }
}
