package common

import common.config.ConsumerConfiguration
import org.apache.pulsar.client.api.MessageListener
import org.apache.pulsar.client.api.PulsarClient

class PulsarConsumer(
    client: PulsarClient,
    configuration: ConsumerConfiguration,
    topicName: String
) {

    private val messageListener: MessageListener<ByteArray> =
        MessageListener { consumer, msg ->
            try {
                println("Message received: $msg")
                consumer.acknowledge(msg)
            } catch (ex: Exception) {
                consumer.negativeAcknowledge(msg)
            }
        }

    private val consumer = client.newConsumer()
        .subscriptionType(configuration.subscriptionType)
        .subscriptionName(configuration.subscriptionName)
        .topic(topicName)
        .messageListener(messageListener)
        .subscribe()

    fun receive() {
        consumer.receiveAsync().thenAccept {
            println("Message: $it")
            consumer.acknowledge(it)
        }
    }

    fun close() {
        consumer.closeAsync().thenRun {
            println("Consumer closed..")
        }.exceptionally {
            throw it
        }
    }
}
