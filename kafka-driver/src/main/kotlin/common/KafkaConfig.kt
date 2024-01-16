package common

/**
 * Class representing a Kafka Configuration File.
 * Contains the Kafka Broker properties:
 * - [partitions]: the number of topic partitions.
 * - [replicationFactor]: the number of replicas.
 * - [commonConfig]: the properties needed by client, producer and consumer.
 * - [producerConfig]: the properties needed by the Kafka Producer.
 * - [consumerConfig]: the properties needed by the Kafka Consumer.
 */
data class KafkaConfig(
    val partitions: Int,
    val replicationFactor: Short,
    val commonConfig: String,
    val producerConfig: String,
    val consumerConfig: String,
)
