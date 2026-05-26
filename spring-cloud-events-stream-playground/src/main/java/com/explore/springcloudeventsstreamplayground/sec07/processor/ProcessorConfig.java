package com.explore.springcloudeventsstreamplayground.sec07.processor;

import com.explore.springcloudeventsstreamplayground.sec07.dto.Notification;
import com.explore.springcloudeventsstreamplayground.sec07.dto.NotificationChannel;
import com.explore.springcloudeventsstreamplayground.sec07.dto.Order;
import com.explore.springcloudeventsstreamplayground.sec07.dto.Payment;
import com.explore.springcloudeventsstreamplayground.sec07.dto.ProductType;
import com.explore.springcloudeventsstreamplayground.sec07.dto.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Configuration
public class ProcessorConfig {

    /**
     * 1-to-1 mapping (Map):
     * For every order event received, the processor emits a payment event.
     * */
    @Bean //
    public Function<Order, Payment> paymentProcessor() {
        return order -> {
            Payment payment = new Payment(
                    order.id(),
                    order.amount(),
                    UUID.randomUUID()
            );
            log.info("Processed Order → Payment: {}", payment);
            return payment;
        };
    }

    /**
     * 1 → 0/1 Mapping (Filter):
     * The processor emits a shipment event only for physical orders.
     * Digital orders are filtered out.
     *
     * Note: Spring cloud stream drops the message on null
     * */
    @Bean
    public Function<Order, Shipment> shipmentProcessor() {
        return order -> {
            if (ProductType.PHYSICAL == order.productType()) {
                Shipment shipment = new Shipment(order.id(), "FEDEX-" + order.id());
                log.info("Processed Order → Shipment: {}", shipment);
                return shipment;
            }
            return null;
        };
    }

    /**
     * 1-to-N mapping (FlatMap):
     * For every order event, the processor emits two notification events
     * */
    @Bean
    public Function<Order, List<Message<Notification>>> notificationProcessor() {
        return order -> {
            Message<Notification> sms = MessageBuilder
                    .withPayload(createSMSNotification(order))
                    .build();

            Message<Notification> email = MessageBuilder
                    .withPayload(createEmailNotification(order))
                    .build();

            log.info("Outgoing notification: {}", sms.getPayload());
            log.info("Outgoing notification: {}", email.getPayload());

            return List.of(sms, email);
        };
    }

    private Notification createSMSNotification(Order order) {
        return new Notification(
                order.id(),
                NotificationChannel.SMS,
                String.valueOf(9_000_000_000L + order.customerId())
        );
    }

    private Notification createEmailNotification(Order order) {
        return new Notification(
                order.id(),
                NotificationChannel.EMAIL,
                "user.%d@gmail.com".formatted(order.customerId())
        );
    }
}
