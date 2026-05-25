## Scaling Consumers with Consumer Groups

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

---

### 1: Create a Topic with Multiple Partitions

```bash
$ docker exec -it kafka bash

$ ./kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --create \
  --partitions 3
```

### 2: Start the Producer

Start the producer that continuously emits messages.

```java
SpringApplication.run(
        Producer.class, "--section=sec06", "--config=02-producer"
);
```

### 3: Start Consumers (Same Consumer Group)

Launch **multiple consumer instances one by one** to observe partition rebalancing.

```java
SpringApplication.run(
        Consumer1.class, "--section=sec06", "--config=01-consumer"
);

SpringApplication.run(
        Consumer2.class, "--section=sec06", "--config=01-consumer"
);

SpringApplication.run(
        Consumer3.class, "--section=sec06", "--config=01-consumer"
);
```

### Observe

* Partitions are **redistributed** as consumers join the group
* Each partition is assigned to **only one consumer at a time**
* **No messages are lost** during rebalancing

> **Total messages produced = Total messages consumed (across all consumers)**