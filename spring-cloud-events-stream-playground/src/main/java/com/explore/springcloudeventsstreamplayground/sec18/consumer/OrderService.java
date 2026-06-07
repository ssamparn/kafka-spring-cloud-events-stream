package com.explore.springcloudeventsstreamplayground.sec18.consumer;

import com.explore.springcloudeventsstreamplayground.sec18.exceptions.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class OrderService {

    public void saveOrder(Integer orderId) {
        if (orderId > 5) {
            this.simulateTransientFailure();
        }
        log.info("Order {} saved successfully", orderId);
    }

    private void simulateTransientFailure() {
        int random = ThreadLocalRandom.current().nextInt(1, 11);
        log.info("random: {}", random);

        if (random < 8) {
            log.warn("service unavailable");
            throw new ServiceUnavailableException();
        }
    }
}
