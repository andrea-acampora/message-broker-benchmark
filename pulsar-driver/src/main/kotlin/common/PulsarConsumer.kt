package common

import BenchmarkConsumer
import common.config.ConsumerConfiguration
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.shade.org.apache.commons.lang.SystemUtils
import java.util.concurrent.Executors
import java.util.concurrent.Future

class PulsarConsumer(
    client: PulsarClient,
    configuration: ConsumerConfiguration,
    topicName: String
) : BenchmarkConsumer {
    override val timeList: ArrayList<Long> = arrayListOf()
    private val executor = Executors.newSingleThreadExecutor()
    @Volatile var closing = false
    private lateinit var consumerTask: Future<*>


    private val consumer = client.newConsumer()
        .subscriptionType(configuration.subscriptionType)
        .subscriptionName(configuration.subscriptionName)
        .topic(topicName)
        .subscribe()

    override fun receive(logger: Boolean) {

        executor.submit{
            while(true){
                val message = consumer.receive()
                if(logger) println("[Pulsar Consumer]: Received Message: " + String(message.data))
                timeList.add(System.currentTimeMillis())
                consumer.acknowledge(message)
            }
        }
    }

    fun close() {
        closing = true
        executor.shutdown()
        consumerTask.get()
        consumer.closeAsync().thenRun {
            println("[Pulsar Consumer] closing..")
        }.exceptionally {
            throw it
        }
    }
}
