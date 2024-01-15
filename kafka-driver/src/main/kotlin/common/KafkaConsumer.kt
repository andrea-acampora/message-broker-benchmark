package common

import BenchmarkConsumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Future

class KafkaConsumer(
    properties: Properties,
    private val topic: String,
) : BenchmarkConsumer {

    override val timeList: ArrayList<Long> = arrayListOf()
    private val executor = Executors.newSingleThreadExecutor()
    @Volatile var closing = false
    private lateinit var consumerTask: Future<*>



    private val consumer: KafkaConsumer<String, ByteArray> = KafkaConsumer<String, ByteArray>(properties).also {
        it.subscribe(listOf(topic))
    }

    override fun receive(logger: Boolean) {
        this.consumerTask = executor.submit {
            while (!closing) {
                val records: ConsumerRecords<String, ByteArray> = consumer.poll(Duration.ofMillis(100))
                records.forEach {
                    timeList.add(System.currentTimeMillis())
                    if (logger) println("[Kafka Consumer] received message: ${String(it.value())}")
                    consumer.commitAsync()
                }
            }
        }
    }


    fun close() {
        println("[Kafka Consumer] closing...")
        closing = true
        executor.shutdown()
        consumerTask.get()
        consumer.close()
    }
}
