package common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import common.config.PulsarConfiguration
import org.apache.pulsar.client.api.PulsarClient
import java.util.concurrent.TimeUnit

/**
 * A Loader of a Pulsar Producer and Pulsar Consumer given a configuration file and a [serviceUrl].
 */
class PulsarLoader(
    configurationFile: String,
    topicName: String,
    private val serviceUrl: String? = null,
) {

    /** The Pulsar Producer. */
    lateinit var producer: PulsarProducer

    /** The Pulsar Consumer. */
    lateinit var consumer: PulsarConsumer

    init {
        object {}.javaClass.getResourceAsStream(configurationFile)?.let {
            val pulsarConfiguration: PulsarConfiguration =
                ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(it.readAllBytes())
            val client: PulsarClient = PulsarClient.builder()
                .ioThreads(pulsarConfiguration.clientConfiguration.ioThreads)
                .connectionsPerBroker(pulsarConfiguration.clientConfiguration.connectionsPerBroker)
                .serviceUrl(serviceUrl ?: pulsarConfiguration.clientConfiguration.serviceUrl)
                .maxConcurrentLookupRequests(pulsarConfiguration.clientConfiguration.maxConcurrentLookupRequests)
                .connectionTimeout(10, TimeUnit.MINUTES)
                .operationTimeout(OPERATION_TIMEOUT, TimeUnit.MINUTES)
                .listenerThreads(Runtime.getRuntime().availableProcessors())
                .build()

            producer = PulsarProducer(client, pulsarConfiguration.producerConfiguration, topicName)
            consumer = PulsarConsumer(client, pulsarConfiguration.consumerConfiguration, topicName)
        }
    }

    companion object {
        /** The timeout of operation. */
        const val OPERATION_TIMEOUT = 60
    }
}
