package com.explore.springcloudeventsstreamplayground.sec05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class Consumer {
        static void main() {
            SpringApplication.run(
                    Consumer.class, "--section=sec05", "--config=01-consumer" // config for reactive kafka consumer
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.producer")
    static class Producer {
        static void main() {
            SpringApplication.run(
                    Producer.class, "--section=sec05", "--config=02-producer" // config for reactive kafka producer
            );
        }
    }
}