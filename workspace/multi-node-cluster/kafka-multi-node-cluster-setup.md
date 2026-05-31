## Running Multi Node Kafka Cluster using Docker Compose

Navigate to the path where the **multi-node-kafka-cluster.yml** is located and then run the below command.

```bash
$ cd workspace/multi-node-cluster/
$ docker-compose -f multi-node-kafka-cluster.yml up
$ docker ps -a
```

Wait ~10 seconds until the all the kafka brokers are fully up and running.

Verify that all three containers (`kafka1`, `kafka2`, `kafka3`) are running:

```bash
$ docker ps -a
```
You should see all three Kafka containers in the `Up` state.

## Accessing the Kafka container
- You can use `-d` option to run in the detached mode.

#### Exec into the Kafka container

```bash
$ docker exec -it kafka1 bash
$ docker exec -it kafka2 bash
$ docker exec -it kafka3 bash
```

#### You will land in directory
```
/opt/kafka/bin
```

### Kafka CLI tools

- List the available command-line tools

```bash
$ ls -l
```

#### You will see Kafka CLI scripts such as

> `kafka-topics.sh`
> `kafka-console-producer.sh`
> `kafka-console-consumer.sh`

### Important note about PATH

Since the directory `/opt/kafka/bin` is **not part of the `PATH` environment variable**. So, you must invoke tools/scripts using `./`:

```bash
$ ./kafka-topics.sh
```

### Kafka configuration files

- Kafka configuration files are located at

```
/opt/kafka/config
```

- Key files you may notice:

* `server.properties`
* `broker.properties`
* `controller.properties`
* `producer.properties`
* `consumer.properties`

### Official configuration reference

Please refer to the [official documentation](https://kafka.apache.org/42/configuration/)

### Create a Topic

Create a topic with:
* **2 partitions**
* **Replication factor = 3**

```bash
$ ./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --create \
    --partitions 2 \
    --replication-factor 3
```

### Describe the Topic

Describe the topic to inspect its metadata:

```bash
$ ./kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic demo-topic \
    --describe
```

Observe:

* Number of partitions
* Partition leaders
* Replica assignments
* In-sync replicas (ISR)

Stop one of the brokers:

```bash
$ docker stop kafka2
```

Describe the topic again and observe:

* How partition leaders change
* How replicas and ISR are updated

You can repeat this by stopping and starting different brokers to see how Kafka maintains availability.

>Note: Please ensure that at least 2 nodes are running in a 3-node cluster for the cluster to function properly, as a majority is required to elect a controller.