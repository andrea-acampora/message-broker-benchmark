package common

import common.config.ProducerConfiguration
import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient

class PulsarProducer(
    client: PulsarClient,
    configuration: ProducerConfiguration,
    topicName: String
) {

    private val producer: Producer<ByteArray> =
        client.newProducer()
            .blockIfQueueFull(configuration.blockIfQueueFull)
            .topic(topicName)
            .create()

    fun sendMessage(message: String) {
        producer.sendAsync(message.encodeToByteArray()).thenRun {
            println("[Pulsar Producer] sent message: $message")
        }
    }

    fun close() {
        producer.closeAsync().thenRun {
            println("[Pulsar Producer] closing..")
        }.exceptionally {
            throw it
        }
    }
}
