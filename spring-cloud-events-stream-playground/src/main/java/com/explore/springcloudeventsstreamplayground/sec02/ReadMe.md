## Building a Reactive Consumer

### Do NOT use `Consumer<Flux<T>>`

```java
@Bean
public Consumer<Flux<String>> consumer() {
    return flux -> flux
        .doOnNext(msg -> log.info("received: {}", msg))
        .subscribe();
}
```

**Why this is wrong ?**

* You are **subscribing manually**
* Errors and backpressure are **outside Spring Cloud Stream control**

### Use `Function` instead

```java
@Bean
public Function<Flux<String>, Mono<Void>> consumer() {
    return flux -> flux
        .doOnNext(msg -> log.info("received: {}", msg))
        .then();
}
```

**Why this is correct ?**

* Spring Cloud Stream manages the subscription
* Backpressure is respected
* Errors are propagated correctly
* Aligns with reactive programming principles

Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

### Access the container
```bash
$ docker exec -it kafka bash
```
#### Ensure that you are in the `/opt/kafka/bin` directory.
---

## Reactive Consumer

#### Open a terminal and check that the topic `demo-topic` does not exist yet.

```bash
$ docker exec -it kafka bash

$ ./kafka-topics.sh --bootstrap-server localhost:9092 --list
```

#### Start the `SectionRunner` with below configuration

```java
SpringApplication.run(
        SectionRunner.class,
        "--section=sec02",
        "--config=01-reactive-consumer"
);
```

#### Produce messages using the console producer
```bash
$ ./kafka-console-producer.sh \
   --bootstrap-server localhost:9092 \
   --topic demo-topic \
   --timeout 0
```
> `--timeout 0` ensures messages are sent immediately without buffering.