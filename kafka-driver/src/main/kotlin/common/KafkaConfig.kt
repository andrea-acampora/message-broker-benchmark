package common

data class KafkaConfig(
    val partitions: Int,
    val replicationFactor: Short,
    val commonConfig: String,
    val producerConfig: String,
    val consumerConfig: String,
)
