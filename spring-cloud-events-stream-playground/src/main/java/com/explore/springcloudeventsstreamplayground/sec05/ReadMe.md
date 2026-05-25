## Building a Reactive Producer

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.


### Reactive Consumer & Producer

1. Start the `SectionRunner.Consumer` with below config.

```java
SpringApplication.run(
        Consumer.class, "--section=sec05", "--config=01-consumer"
);
```

2. Start the `SectionRunner.Producer` with below config.

```java
SpringApplication.run(
        Producer.class, "--section=sec05", "--config=02-producer"
);
```

#### Observe
- The producer publishes messages periodically based on `Flux.interval(duration)`.
- The consumer receives those messages.

## Note
- `Flux` emits continuous stream of messages and not an individual message. So this supplier will be invoked once.

```java
@Bean
public Supplier<Flux<String>> reactiveProducer() {
    return () -> Flux.interval(Duration.ofMillis(500))
                     .map(i -> "msg-" + i)
                     .doOnNext(msg -> log.info("sending: {}", msg));
}
```