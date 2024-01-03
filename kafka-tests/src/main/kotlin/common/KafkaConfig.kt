package common

data class KafkaConfig(
    val replicationFactor: Number,
    val topicConfig: String,
    val commonConfig: String,
    val producerConfig: String,
    val consumerConfig: String
)

