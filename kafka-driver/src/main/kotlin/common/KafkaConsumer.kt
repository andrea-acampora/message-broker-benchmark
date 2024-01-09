package common

import BenchmarkConsumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.Properties

class KafkaConsumer(
    properties: Properties,
    private val topic: String,
): BenchmarkConsumer {

    override val timeList: ArrayList<Long> = arrayListOf()

    private val consumer: KafkaConsumer<String, ByteArray> = KafkaConsumer<String, ByteArray>(properties).also {
        it.subscribe(listOf(topic))
    }

    override fun receive() {
        Thread {
            while (true) {
                val records: ConsumerRecords<String, ByteArray> = consumer.poll(Duration.ofMillis(10))
                records.forEach { record ->
                    timeList.add(System.currentTimeMillis())
                    println("[Kafka Consumer] received message: ${String(record.value())}")
                }
            }
        }.start()
    }

    fun close() {
        println("[Kafka Consumer] closing...")
        consumer.close()
    }
}
