package common

import BenchmarkProducer
import common.config.ProducerConfiguration
import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient

class PulsarProducer(
    client: PulsarClient,
    configuration: ProducerConfiguration,
    topicName: String
): BenchmarkProducer<String> {
    override val timeList: ArrayList<Long> = arrayListOf()

    private val producer: Producer<ByteArray> =
        client.newProducer()
            .blockIfQueueFull(configuration.blockIfQueueFull)
            .topic(topicName)
            .create()

    override fun send(message: String) {
        producer.send(message.encodeToByteArray())
        timeList.add(System.currentTimeMillis())
        println("[Pulsar Producer] sent message: $message")
    }

    fun close() {
        producer.closeAsync().thenRun {
            println("[Pulsar Producer] closing..")
        }.exceptionally {
            throw it
        }
    }
}
