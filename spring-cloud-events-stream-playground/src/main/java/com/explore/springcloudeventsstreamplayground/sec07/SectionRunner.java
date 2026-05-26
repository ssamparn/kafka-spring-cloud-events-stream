package com.explore.springcloudeventsstreamplayground.sec07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.consumer")
    static class Consumer {
        static void main() {
            SpringApplication.run(
                    Consumer.class, "--section=sec07", "--config=01-consumer" // config for kafka consumer
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.processor")
    static class Processor {
        static void main() {
            SpringApplication.run(
//                    Processor.class, "--section=sec07", "--config=03-payment-processor" // config for kafka payment processor
//                    Processor.class, "--section=sec07", "--config=04-shipment-processor" // config for kafka shipment processor
                    Processor.class, "--section=sec07", "--config=05-notification-processor" // config for kafka notification processor
            );
        }
    }

    @SpringBootApplication(scanBasePackages = "com.explore.springcloudeventsstreamplayground.${section}.producer")
    static class Producer {
        static void main() {
            SpringApplication.run(
                    Producer.class, "--section=sec07", "--config=02-producer" // config for kafka producer
            );
        }
    }
}
