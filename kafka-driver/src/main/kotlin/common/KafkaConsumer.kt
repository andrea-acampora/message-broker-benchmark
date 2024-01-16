package common

import consumer.BenchmarkConsumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * A Kafka Consumer for a given [topic].
 */
class KafkaConsumer(
    properties: Properties,
    private val topic: String,
) : BenchmarkConsumer {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var consumerTask: Future<*>

    /** Property used to check if the consumer is closed. */
    @Volatile var closing = false

    private val consumer: KafkaConsumer<String, ByteArray> =
        KafkaConsumer<String, ByteArray>(properties).apply {
            this.subscribe(listOf(topic))
        }

    override fun receive(logger: Boolean) {
        this.consumerTask = executor.submit {
            while (!closing) {
                val records: ConsumerRecords<String, ByteArray> = consumer.poll(Duration.ofMillis(100))
                records.forEach {
                    messagesTimestamp.add(System.currentTimeMillis())
                    if (logger) println("[Kafka Consumer] received message: ${String(it.value())}")
                    consumer.commitAsync()
                }
            }
        }
    }

    override fun close() {
        println("[Kafka Consumer] closing...")
        closing = true
        executor.shutdown()
        consumerTask.get()
        consumer.close()
    }
}
