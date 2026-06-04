package com.explore.springcloudeventsstreamplayground.sec14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class Consumer {
        static void main() {
            SpringApplication.run(
                    Consumer.class, "--section=sec14", "--config=01-consumer"
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.producer")
    static class Producer {
        static void main() {
            SpringApplication.run(
                    Producer.class, "--section=sec14", "--config=02-producer"
            );
        }
    }
}