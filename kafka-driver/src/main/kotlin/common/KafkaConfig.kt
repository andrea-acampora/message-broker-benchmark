package common

data class KafkaConfig(
    val replicationFactor: Short,
    val topicConfig: String,
    val commonConfig: String,
    val producerConfig: String,
    val consumerConfig: String
)
