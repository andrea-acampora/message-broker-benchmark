package common

data class KafkaConfig(
    val replicationFactor: Short,
    val commonConfig: String,
    val producerConfig: String,
    val consumerConfig: String
)
