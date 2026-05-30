package com.explore.springcloudeventsstreamplayground.sec10.processor;

import com.explore.springcloudeventsstreamplayground.sec10.dto.DigitalDelivery;
import com.explore.springcloudeventsstreamplayground.sec10.dto.Order;
import com.explore.springcloudeventsstreamplayground.sec10.dto.PhysicalDelivery;
import com.explore.springcloudeventsstreamplayground.sec10.dto.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Function;

@Slf4j
@Configuration
public class ProcessorConfig {

    @Bean
    public Function<Flux<Order>, Tuple2<Flux<DigitalDelivery>, Flux<PhysicalDelivery>>> orderProcessor() {
        return sharedOrderFlux -> {
            var orderFlux = sharedOrderFlux.doOnNext(order -> log.info("dispatching order: {}", order))
                    .publish()
                    .autoConnect(2);

            return Tuples.of(
                    this.digitalDeliveryFlux(orderFlux),
                    this.physicalDeliveryFlux(orderFlux)
            );
        };
    }

    private Flux<DigitalDelivery> digitalDeliveryFlux(Flux<Order> flux) {
        return flux.filter(o -> ProductType.DIGITAL.equals(o.productType()))
                   .map(this::createDigitalDelivery);
    }

    private Flux<PhysicalDelivery> physicalDeliveryFlux(Flux<Order> flux) {
        return flux.filter(o -> ProductType.PHYSICAL.equals(o.productType()))
                   .map(this::createPhysicalDelivery);
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
