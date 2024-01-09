package common

import BenchmarkProducer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.Properties

class KafkaProducer(
    properties: Properties,
    private val topic: String
): BenchmarkProducer<String> {
    override val timeList: ArrayList<Long> = arrayListOf()

    private val producer: KafkaProducer<String, ByteArray> = KafkaProducer(properties)

    override fun send(message: String) {
        val record: ProducerRecord<String, ByteArray> = ProducerRecord(topic, message.toByteArray())
        producer.send(record)
        timeList.add(System.currentTimeMillis())
        println("[Kafka Producer] sent message: $message")
    }

    fun close() {
        println("[Kafka Producer] closing...")
        producer.close()
    }
}
