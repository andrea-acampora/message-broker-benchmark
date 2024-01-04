package common.config

data class PulsarConfiguration(
    val clientConfiguration: ClientConfiguration,
    val producerConfiguration: ProducerConfiguration,
    val consumerConfiguration: ConsumerConfiguration
)
