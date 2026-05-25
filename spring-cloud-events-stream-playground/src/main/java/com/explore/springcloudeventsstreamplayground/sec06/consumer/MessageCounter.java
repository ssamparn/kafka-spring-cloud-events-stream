package com.explore.springcloudeventsstreamplayground.sec06.consumer;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class MessageCounter {

    private final AtomicLong counter = new AtomicLong();

    public void increment() {
        counter.incrementAndGet();
    }

    @PreDestroy
    public void onShutdown() {
        log.info("Total messages consumed: {}", counter.get());
    }
}
