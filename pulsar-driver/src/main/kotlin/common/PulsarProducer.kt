package common

import common.config.ProducerConfiguration
import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient
import producer.BenchmarkProducer
import java.util.concurrent.TimeUnit

/**
 * A Pulsar Producer for a given topic.
 */
class PulsarProducer(
    client: PulsarClient,
    configuration: ProducerConfiguration,
    topic: String,
) : BenchmarkProducer<ByteArray> {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()
    private val producer: Producer<ByteArray> =
        client.newProducer()
            .enableBatching(configuration.batchingEnabled)
            .batchingMaxPublishDelay(configuration.batchingMaxPublishDelayMs, TimeUnit.MILLISECONDS)
            .batchingMaxBytes(configuration.batchingMaxBytes)
            .blockIfQueueFull(configuration.blockIfQueueFull)
            .topic(topic)
            .create()

    override fun send(message: ByteArray, logger: Boolean) {
        producer.sendAsync(message)
        messagesTimestamp.add(System.currentTimeMillis())
        if (logger) println("[Pulsar Producer] sent message: ${String(message)}")
    }

    override fun close() {
        producer.close()
        println("[Pulsar Producer] closing..")
    }
}
