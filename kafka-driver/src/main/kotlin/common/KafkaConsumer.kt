package common

import BenchmarkConsumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*

class KafkaConsumer(
    properties: Properties,
    private val topic: String,
) : BenchmarkConsumer {

    override val timeList: ArrayList<Long> = arrayListOf()

    private val consumer: KafkaConsumer<String, ByteArray> = KafkaConsumer<String, ByteArray>(properties).also {
        it.subscribe(listOf(topic))
    }

    override fun receive() {
        while (true) {
            val records: ConsumerRecords<String, ByteArray> = consumer.poll(Duration.ofMillis(100))
            records.forEach {
                timeList.add(System.currentTimeMillis())
               // println("[Kafka Consumer] received message: ${String(record.value())}")
                consumer.commitAsync()
            }
        }
    }


    fun close() {
        println("[Kafka Consumer] closing...")
        consumer.close()
    }
}
