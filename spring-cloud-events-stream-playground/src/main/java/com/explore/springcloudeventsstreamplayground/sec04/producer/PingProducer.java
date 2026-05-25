package com.explore.springcloudeventsstreamplayground.sec04.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PingProducer implements CommandLineRunner {

    private static final String PING_OUT = "ping-out";

    private final StreamBridge streamBridge;

    public PingProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void run(String... args) throws Exception {
        var process = new ProcessBuilder("ping", "-c", "10", "google.com")
                .redirectErrorStream(true)
                .start();

        try (var reader = process.inputReader()) {
            reader.lines()
                    .forEach(line -> {
                        log.info("sending: {}", line);
                        this.streamBridge.send(PING_OUT, line);
                    });
        }
    }
}
