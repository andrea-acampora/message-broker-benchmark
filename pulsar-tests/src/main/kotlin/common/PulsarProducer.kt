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
        println("Producer sending message: $message")
        producer.sendAsync(message.encodeToByteArray())
    }

    fun close() {
        producer.closeAsync().thenRun {
            println("Producer closed..")
        }.exceptionally {
            throw it
        }
    }
}
