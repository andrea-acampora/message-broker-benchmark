package common

import BenchmarkProducer
import common.config.ProducerConfiguration
import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient
import java.util.concurrent.TimeUnit

class PulsarProducer(
    client: PulsarClient,
    configuration: ProducerConfiguration,
    topicName: String
): BenchmarkProducer<ByteArray> {
    override val timeList: ArrayList<Long> = arrayListOf()

    private val producer: Producer<ByteArray> =
        client.newProducer()
            .enableBatching(configuration.batchingEnabled)
            .batchingMaxPublishDelay(configuration.batchingMaxPublishDelayMs, TimeUnit.MILLISECONDS)
            .batchingMaxBytes(configuration.batchingMaxBytes)
            .blockIfQueueFull(configuration.blockIfQueueFull)
            .topic(topicName)
            .create()

    override fun send(message: ByteArray) {
        producer.sendAsync(message)
        timeList.add(System.currentTimeMillis())
        // println("[Pulsar Producer] sent message: ${String(message)}")
    }

    fun close() {
        producer.closeAsync().thenRun {
            println("[Pulsar Producer] closing..")
        }.exceptionally {
            throw it
        }
    }
}
