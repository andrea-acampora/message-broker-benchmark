package common.config

/**
 * The Configuration file properties for a Pulsar Broker.
 * There are three sections: the [clientConfiguration], the [producerConfiguration]
 * and the [consumerConfiguration].
 */
data class PulsarConfiguration(
    val clientConfiguration: ClientConfiguration,
    val producerConfiguration: ProducerConfiguration,
    val consumerConfiguration: ConsumerConfiguration,
)
