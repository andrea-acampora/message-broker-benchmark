package common

import common.config.ConsumerConfiguration
import org.apache.pulsar.client.api.PulsarClient

class PulsarConsumer(
    client: PulsarClient,
    configuration: ConsumerConfiguration,
    topicName: String
) {

    private val consumer = client.newConsumer()
        .subscriptionType(configuration.subscriptionType)
        .subscriptionName(configuration.subscriptionName)
        .topic(topicName)
        .subscribe()

    fun consume() {
        consumer.receiveAsync().thenAccept {
            println("[Pulsar Consumer]: Received Message: " + String(it.data))
            consumer.acknowledge(it)
        }
    }

    fun close() {
        consumer.closeAsync().thenRun {
            println("[Pulsar Consumer] closing..")
        }.exceptionally {
            throw it
        }
    }
}
