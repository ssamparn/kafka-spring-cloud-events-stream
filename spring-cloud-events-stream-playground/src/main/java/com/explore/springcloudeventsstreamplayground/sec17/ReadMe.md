## Manual Acknowledgement

**Note:** To avoid issues during the demos, it is recommended to spin up a fresh Kafka container for each demo to clean up the state.
Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

#### 1: Start the **Consumer** application.

```java
public class SectionRunner {

    static void main() {
        SpringApplication.run(SectionRunner.class, "--section=sec17", "--config=01-consumer");
    }
}
```

#### 2: Open two terminals

### Terminal 1 – Console Producer

```bash
$ docker exec -it kafka bash

$ ./kafka-console-producer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --timeout 0
```

### Terminal 2 – Consumer Group Status

```bash
$ ./kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --describe \
    --group demo-group
```

### Demo Steps

1. **Produce messages:** `1`, `2`, `3`

    * Application acknowledges the messages
    * Offsets are committed
    * LAG remains `0`

2. **Produce messages:** `4`, `5`, `6`

    * Application does **not** acknowledge
    * Offsets are **not committed**
    * LAG increases
    * Restart the consumer application. Messages `4`, `5`, `6` are re-delivered because offsets were not committed

3. **Produce message:** `7`

    * Application simulates a temporary failure
    * Message is **NACKed**
    * Kafka re-delivers the message after a delay

### Observe

* Acknowledged messages → offsets committed
* Unacknowledged messages → LAG increases
* NACK → delayed retry without offset commit
