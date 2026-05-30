## Content-Based Routing using Tuples

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.


#### 1: Start the **Digital Delivery Consumer** application.

```java
SpringApplication.run(
        DigitalDeliveryConsumer.class, "--section=sec10", "--config=01-digital-consumer"
);
```

#### 2: Start the **Physical Delivery Consumer** application.

```java
SpringApplication.run(
        PhysicalDeliveryConsumer.class, "--section=sec10", "--config=02-physical-consumer"
);
```

#### 3: Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec10", "--config=03-processor"
);
```

#### 4: Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec10", "--config=04-producer"
);
```

**Observe:**

* The processor builds delivery objects for each order.
* Events are routed to two different consumers based on the product type.