package common

import BenchmarkConsumer
import common.config.ConsumerConfiguration
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.shade.org.apache.commons.lang.SystemUtils

class PulsarConsumer(
    client: PulsarClient,
    configuration: ConsumerConfiguration,
    topicName: String
) : BenchmarkConsumer {
    override val timeList: ArrayList<Long> = arrayListOf()


    private val consumer = client.newConsumer()
        .subscriptionType(configuration.subscriptionType)
        .subscriptionName(configuration.subscriptionName)
        .topic(topicName)
        .subscribe()

    override fun receive() {
        while(true){
            val message = consumer.receive()
           // println("[Pulsar Consumer]: Received Message: " + String(message.data))
            timeList.add(System.currentTimeMillis())
            consumer.acknowledge(message)
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
