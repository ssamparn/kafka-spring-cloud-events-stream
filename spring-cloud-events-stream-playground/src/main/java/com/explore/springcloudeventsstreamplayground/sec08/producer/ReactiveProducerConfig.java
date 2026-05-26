package com.explore.springcloudeventsstreamplayground.sec08.producer;

import com.explore.springcloudeventsstreamplayground.sec08.dto.Order;
import com.explore.springcloudeventsstreamplayground.sec08.dto.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ReactiveProducerConfig {

    private final AtomicInteger counter = new AtomicInteger();
    private final Random random = new Random();

    @Bean
    public Supplier<Flux<Order>> orderProducer() {
            return () -> Flux.interval(Duration.ofSeconds(1))
                .map(_ -> {
                    var order = new Order(
                            counter.incrementAndGet(),
                            random.nextInt(1000),
                            random.nextInt(500) + 1,
                            random.nextBoolean() ? ProductType.DIGITAL : ProductType.PHYSICAL
                    );
                    log.info("produced: {}", order);
                    return order;
                });
    }
}
