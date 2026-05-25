package com.explore.springcloudeventsstreamplayground.sec06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class Consumer1 {
        static void main() {
            SpringApplication.run(
                    Consumer1.class, "--section=sec06", "--config=01-consumer" // config for kafka consumer
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class Consumer2 {
        static void main() {
            SpringApplication.run(
                    Consumer2.class, "--section=sec06", "--config=01-consumer" // config for kafka consumer
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class Consumer3 {
        static void main() {
            SpringApplication.run(
                    Consumer3.class, "--section=sec06", "--config=01-consumer" // config for kafka consumer
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.producer")
    static class Producer {
        static void main() {
            SpringApplication.run(
                    Producer.class, "--section=sec06", "--config=02-producer" // config for kafka producer
            );
        }
    }
}