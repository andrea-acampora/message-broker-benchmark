package common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import common.config.PulsarConfiguration
import org.apache.pulsar.client.api.PulsarClient
import java.io.File
import java.util.concurrent.TimeUnit

class PulsarLoader(
    configurationFile: String,
    topicName: String
) {

    lateinit var producer: PulsarProducer
    lateinit var consumer: PulsarConsumer

    init {
        object {}.javaClass.getResource(configurationFile)?.let {
            val pulsarConfiguration: PulsarConfiguration =
                ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(File(it.toURI()))

            val client: PulsarClient = PulsarClient.builder()
                .ioThreads(pulsarConfiguration.clientConfiguration.ioThreads)
                .connectionsPerBroker(pulsarConfiguration.clientConfiguration.connectionsPerBroker)
                .serviceUrl(pulsarConfiguration.clientConfiguration.serviceUrl)
                .maxConcurrentLookupRequests(pulsarConfiguration.clientConfiguration.maxConcurrentLookupRequests)
                .connectionTimeout(10, TimeUnit.MINUTES)
                .operationTimeout(60, TimeUnit.MINUTES)
                .listenerThreads(Runtime.getRuntime().availableProcessors())
                .build()
            producer = PulsarProducer(client, pulsarConfiguration.producerConfiguration, topicName)
            consumer = PulsarConsumer(client, pulsarConfiguration.consumerConfiguration, topicName)
        }
    }
}
