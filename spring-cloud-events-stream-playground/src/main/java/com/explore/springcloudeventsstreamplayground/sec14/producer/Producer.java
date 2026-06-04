package com.explore.springcloudeventsstreamplayground.sec14.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class Producer implements CommandLineRunner {

    private static final String DEMO_BINDING = "demo-out";

    private final StreamBridge streamBridge;

    public Producer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    /**
     * The Producer App:
     *   - Sends 1,000,000 messages
     *   - Uses StreamBridge to publish / send messages dynamically to output bindings.
     *   - Logs progress every 10000 messages
     *   - Slightly throttles sending by adding a small delay, which prevents overwhelming the system / broker
     * */
    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 1_000_000 ; i++) {
            this.streamBridge.send(DEMO_BINDING, "msg-" + i);
            if (i % 1_000_0 == 0) {
                log.info("Total messages produced: {}", i); // log for every 10000 messages produced
                Thread.sleep(Duration.ofMillis(10)); // intentional for demo
            }
        }
    }
}
