# Message Broker Benchmark
 
The project aims to evaluate some of the well-known message brokers: **Apache Kafka**, **Apache Pulsar** and **RabbitMQ**.

The benchmark consists in the following tests:
1. **Easy of use**
2. **End-to-end Latency**
3. **Throughput**
4. **Node Failure**
---
## Usage
In order to start the brokers create the folders `data/zookeeper` and `data/bookkepper` with the right permissions and then run the following command in the root of the project:
 ```bash 
docker-compose up
 ```
1. **Easy of use** \
   For each Message Broker there's a Gradle Task to run an Hello World with a Producer and a Consumer.
   ```bash
   ./gradlew kafka-driver:run
   ./gradlew pulsar-driver:run
   ./gradlew rabbimq-driver:run
   ```

2. **Latency** \
   Run the latency test with the dedicated Gradle Task:
   ```bash
   ./gradlew runLatencyBenchmark
   ```

3. **Throughput** \
   Run the throughput test with the dedicated Gradle Task:
   ```bash
   ./gradlew runThroughputBenchmark
   ```
4. **Node Failure** \
   In this test you have to run another docker-compose file because there are multiple brokers for each technology. \
   Go to /src/main/resources and for each technology run: 
   ```bash
   docker-compose up
   ```
   Run the node failure test with the dedicated Gradle Task:
   ```bash
   ./gradlew runNodeFailureBenchmark
   ```
---
## Authors
- _Andrea Acampora_
- _Giacomo Accursi_
