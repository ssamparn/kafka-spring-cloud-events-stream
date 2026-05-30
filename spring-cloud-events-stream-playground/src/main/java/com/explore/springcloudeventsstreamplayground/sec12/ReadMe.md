## Dynamic Routing

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.


#### 1: Start the **Digital Delivery Consumer** application.
Start the **Digital Delivery Consumer** application.

```java
SpringApplication.run(
        DigitalDeliveryConsumer.class, "--section=sec12", "--config=01-digital-consumer"
);
```

#### 2: Start the **Fedex Delivery Consumer** application.

```java
SpringApplication.run(
        FedExDeliveryConsumer.class, "--section=sec12", "--config=02-fedex-consumer"
);
```

#### 3: Start the **Usps Delivery Consumer** application.

```java
SpringApplication.run(
        UspsDeliveryConsumer.class, "--section=sec12", "--config=03-usps-consumer"
);
```

#### 4: Start the **Processor** application.

```java
SpringApplication.run(
        Processor.class, "--section=sec12", "--config=04-processor"
);
```

#### 5: Start the **Producer** application.

```java
SpringApplication.run(
        Producer.class, "--section=sec12", "--config=05-producer"
);
```

**Observe:**

* The processor builds delivery objects for each order.
* Events are routed to different consumers based on runtime carrier availability, not just product type.
* When the preferred carrier is unavailable, events are dynamically rerouted to an alternate consumer.