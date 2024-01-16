package common

import common.config.ConsumerConfiguration
import consumer.BenchmarkConsumer
import org.apache.pulsar.client.api.PulsarClient
import java.util.concurrent.Executors

/**
 * A Pulsar Consumer for a given topic.
 */
class PulsarConsumer(
    client: PulsarClient,
    configuration: ConsumerConfiguration,
    topic: String,
) : BenchmarkConsumer {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()
    private val executor = Executors.newSingleThreadExecutor()

    private val consumer = client.newConsumer()
        .subscriptionType(configuration.subscriptionType)
        .subscriptionName(configuration.subscriptionName)
        .topic(topic)
        .subscribe()

    override fun receive(logger: Boolean) {
        executor.submit {
            while (true) {
                val message = consumer.receive()
                messagesTimestamp.add(System.currentTimeMillis())
                if (logger) println("[Pulsar Consumer]: Received Message: " + String(message.data))
                consumer.acknowledge(message)
            }
        }
    }

    override fun close() {
        println("[Pulsar Consumer] closing..")
        consumer.close()
    }
}
