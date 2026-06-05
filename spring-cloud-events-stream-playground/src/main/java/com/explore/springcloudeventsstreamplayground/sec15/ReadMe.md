## Concurrent Message Processing with Spring Cloud Stream

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

#### Exec into the Kafka container

```bash
$ docker exec -it kafka bash
```

#### 1: Create a topic with **3 partitions**.

```bash
$ ./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic order-events \
    --create \
    --partitions 3
```

#### 2: Start the **Digital Delivery Consumer** application.

```java
SpringApplication.run(
        DigitalDeliveryConsumer.class, "--section=sec15", "--config=01-digital-consumer"
);
```

#### 3: Start the **Physical Delivery Consumer** application.

```java
SpringApplication.run(
        PhysicalDeliveryConsumer.class, "--section=sec15", "--config=02-physical-consumer"
);
```

#### 4: Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec15", "--config=03-processor"
);
```

#### 5: Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec15", "--config=04-producer"
);
```

**Observe:**

* The producer publishes one order event every **100 ms**.
* The processor builds a delivery object for each order, and this takes **200 ms** per event.
* The processor runs with **3 consumer threads**, each consuming from **one partition**, allowing it to keep up with the producer.
* Events are routed to different consumers based on the **product type**.

---

### What If `concurrency = 1`?

* The processor uses **only one consumer thread**.
* All **3 partitions** are assigned to that single consumer.
* Each event still takes **200 ms** to process.
* The producer publishes events faster (**100 ms per event**) than the processor can handle.
* As a result, **lag increases** and the processor falls behind.