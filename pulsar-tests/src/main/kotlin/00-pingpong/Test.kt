package `00-pingpong`

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import common.PulsarConsumer
import common.PulsarProducer
import common.config.PulsarConfiguration
import org.apache.pulsar.client.api.PulsarClient
import java.io.File
import java.util.concurrent.TimeUnit

fun main() {
    object {}.javaClass.getResource("/pulsar.yml")?.let {
        val pulsarConfiguration: PulsarConfiguration =
            ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(File(it.toURI()))

        val client = PulsarClient.builder()
            .ioThreads(pulsarConfiguration.clientConfiguration.ioThreads)
            .connectionsPerBroker(pulsarConfiguration.clientConfiguration.connectionsPerBroker)
            .serviceUrl(pulsarConfiguration.clientConfiguration.serviceUrl)
            .maxConcurrentLookupRequests(pulsarConfiguration.clientConfiguration.maxConcurrentLookupRequests)
            .connectionTimeout(10, TimeUnit.MINUTES)
            .operationTimeout(60, TimeUnit.MINUTES)
            .listenerThreads(Runtime.getRuntime().availableProcessors())
            .build()

        val topicName = "base_communication"

        val producer = PulsarProducer(client, pulsarConfiguration.producerConfiguration, topicName)
        val consumer = PulsarConsumer(client, pulsarConfiguration.consumerConfiguration, topicName)
        producer.sendMessage("Hello World!")
        consumer.consume()
    }
}
