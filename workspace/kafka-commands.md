## 1. Topics

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


Kafka provides the **`kafka-topics.sh`** CLI tool to manage topics.

### Create a topic

> All commands require `--bootstrap-server` because the CLI must first connect to a broker to discover the cluster.

```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic order-events
```

#### Let’s create a few more topics

```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic payment-events
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic shipping-events
```

### List all topics in the cluster

```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --list
```

####  Shows all topics currently present in the cluster

### Describe a topic

```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --topic order-events --describe
```

### Delete a topic

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --topic order-events --delete
```

## 2. Kafka Console Producer

Kafka provides a **console producer tool** that allows us to send messages to a topic directly from the command line. 
This is mainly used for **learning, testing, and debugging** purposes. 
Not meant for production workloads.

### Access the container
```bash
$ docker exec -it kafka bash
```
#### Ensure that you are in the `/opt/kafka/bin` directory.

### Create a topic

```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic demo-topic
```

### Produce messages to a topic

```bash
$ ./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

- The producer waits for input from the console
- **Each line** followed by pressing **Enter** is sent as a separate message
- Messages are sent in **plain text** by default
- Press `Ctrl + C` to stop the producer.


## 3. Kafka Console Consumer

Kafka provides a **console consumer tool** to read messages from a topic directly from the command line. 
This is mainly used for **learning, testing, and debugging**.
Not meant for production workloads.

### Access the container
```bash
$ docker exec -it kafka bash
```
#### Ensure that you are in the `/opt/kafka/bin` directory.

### Consume messages from a topic

```bash
$ ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

- Starts consuming **new messages only**
- The consumer waits and prints messages as they arrive
- Press `Ctrl + C` to stop

### Consume messages from the beginning

```bash
$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --from-beginning
```

- Reads **all existing messages** in the topic from beginning
- Useful for demos and validation

## 4. Kafka Producer Timeout

### Access the container
```bash
$ docker exec -it kafka bash
```
#### Ensure that you are in the `/opt/kafka/bin` directory.

### Create a topic

```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic demo-topic
```

### Produce messages to a topic

```bash
$ ./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

### Produce messages to a topic with a timeout
```bash
$ ./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic --timeout 0
```

> By default, timeout value is `1 sec`. That means kafka producer keeps the message in a queue for `1 sec` before consumer can consume it.
> If you want the consumer to consume messages as and when producer produces the message then set timeout to `0`
> When we set `--timeout` to `0`, `linger.ms` gets set to `0`, & the kafka producer driver sends the message immediately. 

### Consume messages from a topic
```bash
$ ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

## 5. Print Offset

### Start Kafka with a clean state

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


### Create a topic
```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --topic demo-topic --create
```

### Start a console producer
- Open another terminal (or another shell in the container)

```bash
$ docker exec -it kafka bash
$ ./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

### Start a console consumer with property `print.offset=true`

```bash
$ ./kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --property print.offset=true
```
#### This property `print.offset=true` enables offset printing along with each message

#### Type some messages to observe the consumer output.

> Offset Max Value = Long.MAX_VALUE. So, consider an app which produces 1M messages/second, it will take 292,000 years to reach Long.MAX_VALUE


## 6. Print Timestamp

### Start Kafka with a clean state

Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml up
```

### Access the container
```bash
$ docker exec -it kafka bash
```
#### Ensure that you are in the `/opt/kafka/bin` directory.

### Create a topic
```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --topic demo-topic --create
```

### Start a console producer
- Open another terminal (or another shell in the container)

```bash
$ docker exec -it kafka bash
$ ./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic demo-topic
```

### Start a console consumer with property `print.timestamp=true`

```bash
$ ./kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --property print.offset=true \
    --property print.timestamp=true
```
#### This property `print.timestamp=true` enables kafka message creation time along with each message


## 7. Consumer Group

### Start Kafka with a clean state

Navigate to the path where the **single-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/single-node-cluster/
$ docker-compose -f single-node-kafka-cluster.yml down
$ docker-compose -f single-node-kafka-cluster.yml up
```
#### Restart Kafka to ensure a clean demo. Wait until the Kafka container is fully up and running. Since, restarting Kafka ensures there are no leftover topics or offsets from earlier runs.

### Access the kafka container.
Open **two terminals** (you will start one consumer in each). In **each terminal**

```bash
$ docker exec -it kafka bash
```
#### Ensure that you are in the `/opt/kafka/bin` directory.

### Create a topic
```bash
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --topic demo-topic --create
```

### Consumer Group 1: `payment-service`

```bash
$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --property print.offset=true \
  --group payment-service
```

### Consumer Group 2: `inventory-service`

```bash
$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic \
  --property print.offset=true \
  --group inventory-service
```
## Start a Console Producer

Open **another terminal**, access the container.

```bash
$ docker exec -it kafka bash
```

### Start a console producer
```bash
$ ./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo-topic
```

Type some messages and press **Enter**.

## List all the consumer groups
```bash
$ ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```

## 8. Kafka Topic with 2 Partitions

### Start Kafka with a clean state
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

#### Create a topic with 2 partitions

```bash
$ ./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic order-events \
    --create \
    --partitions 2
```

#### Describe the topic

```bash
$ ./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic order-events \
    --describe
```

## 9. Consumer Group Demo (Kafka Topic with 2 Partitions)

### Prerequisite

* Ensure you have completed the **8.cKafka Topic with 2 Partitions**
* The topic `order-events` must already exist **with 2 partitions**

---
### Start a Consumer Group with Two Consumers

We will start **two consumers in the same consumer group** `payment-service`.
Each consumer will run in a **separate terminal**.

#### Terminal 1

```bash
$ docker exec -it kafka bash

$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property print.offset=true \
  --property print.key=true \
  --group payment-service
```

#### Terminal 2

```bash
$ docker exec -it kafka bash

$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property print.offset=true \
  --property print.key=true \
  --group payment-service
```

#### Both consumers belong to the **same consumer group**
#### Kafka will distribute partitions between them

### Start a Console Producer

Open **another terminal** and access the Kafka container:

```bash
$ docker exec -it kafka bash
```

#### Start the console producer. We will send messages **with a key**, using the format `key:value`. `:` is used as the key separator.

```bash
$ ./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --property parse.key=true \
  --property key.separator=:
```

### Observe

* Produce multiple messages
* Notice how messages are **distributed across the two consumers**
* Each partition is consumed by **only one consumer in the group**


## 10. Null Key Demo

### Goal
#### Send messages **without a key** to a **2-partition topic** and observe how Kafka distributes the messages across partitions and consumers.

---
### Create a topic with 2 partitions

```bash
$ docker exec -it kafka bash

$ ./kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic null-key-topic \
  --create \
  --partitions 2
```

### Start a consumer group with 2 consumers

#### Terminal 1

```bash
$ docker exec -it kafka bash

$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic null-key-topic \
  --property print.offset=true \
  --property print.key=true \
  --group null-key-group
```

#### Terminal 2

```bash
$ docker exec -it kafka bash

$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic null-key-topic \
  --property print.offset=true \
  --property print.key=true \
  --group null-key-group
```

### Start a producer

Produce one message every **50 milliseconds**. Messages are sent **without a key**.

```bash
i=1
while true; do
  echo "msg$i"
  i=$((i+1))
  sleep 0.05
done | ./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic null-key-topic \
  --timeout 0
```

## 11. Offset Tracking

### Goal
Understand how Kafka tracks **consumer group offsets** and how consumers resume from the last committed position.

### Create a topic with 2 partitions

```bash
$ docker exec -it kafka bash

$ ./kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --create \
  --partitions 2
```

### Start a producer

Produce **5 messages with a key** so they are consistently routed to the same partition.

```bash
$ docker exec -it kafka bash

$ ./kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --property parse.key=true \
  --property key.separator=:
```

Example input:

```bash
1:a
2:a
3:a
4:a
5:a
```

### Start a consumer group with 2 consumers

> We intentionally do **not** use `--from-beginning`.
> Consumers will read **only new messages**.

#### Terminal 1

```bash
$ docker exec -it kafka bash

$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --property print.offset=true \
  --property print.key=true \
  --group cg
```

#### Terminal 2

```bash
$ docker exec -it kafka bash

$ ./kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --property print.offset=true \
  --property print.key=true \
  --group cg
```

### Describe the consumer group

```bash
$ docker exec -it kafka bash

$ ./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --group cg
```
We can observe the following columns

* **LOG-END-OFFSET**

    * The **latest offset** available in the partition
    * Represents **the total data currently available** in Kafka for that partition

* **CURRENT-OFFSET**

    * The **last offset committed** by the consumer group
    * Indicates **how much data the consumer group has processed**

* **LAG**

    * The difference between `LOG-END-OFFSET` and `CURRENT-OFFSET`
    * Shows **how far behind the consumer group is**

```
LAG = LOG-END-OFFSET − CURRENT-OFFSET
```

> Lag tells us whether consumers are keeping up with producers.

## 12. Offset Reset

### Prerequisite

Please ensure you have completed the `11. Offset Tracking` and the consumer group `cg` exists.

### Offset Reset Options

Kafka allows resetting the offsets **tracked for a consumer group** using different strategies.

| Option                                  | Description                                         |
| --------------------------------------- | --------------------------------------------------- |
| `--shift-by 3`                          | Move the offset **forward** by 3                    |
| `--shift-by -2`                         | Move the offset **backward** by 2                   |
| `--by-duration PT5M`                    | Reset offsets to **5 minutes ago**                  |
| `--to-datetime 2026-01-01T00:00:00.000` | Reset offsets to a **specific date-time**           |
| `--to-earliest`                         | Reset offsets to the **beginning** of the partition |
| `--to-latest`                           | Reset offsets to the **end** of the partition       |

### Dry Run vs Execute

* `--dry-run`
    * Does **not** change offsets
    * Shows the **new offsets** that would be applied

* `--execute`
    * Actually **resets** the offsets

### Commands

#### Dry run (preview the change)

```bash
$ ./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --shift-by -3 \
  --dry-run
```

#### Reset offsets by shifting backward
```bash
$ ./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --shift-by -3 \
  --execute
```

#### Reset offsets by duration
```bash
$ ./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --by-duration PT5M \
  --execute
```

#### Reset offsets to the beginning
```bash
$ ./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --to-earliest \
  --execute
```

#### Reset offsets to the end
```bash
$ ./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --to-latest \
  --execute
```

#### Reset offsets to a specific date-time
```bash
$ ./kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --topic offset-tracking-topic \
  --group cg \
  --reset-offsets \
  --to-datetime 2023-01-01T01:00:00.000 \
  --execute
```