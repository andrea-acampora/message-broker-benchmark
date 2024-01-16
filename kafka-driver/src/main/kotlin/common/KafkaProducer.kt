package common

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import producer.BenchmarkProducer
import java.util.Properties

/**
 * A Kafka Producer given some properties and a [topic].
 */
class KafkaProducer(
    properties: Properties,
    private val topic: String,
) : BenchmarkProducer<ByteArray> {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()
    private val producer: KafkaProducer<String, ByteArray> = KafkaProducer<String, ByteArray>(properties)

    override fun send(message: ByteArray, logger: Boolean) {
        val record: ProducerRecord<String, ByteArray> = ProducerRecord(topic, message)
        producer.send(record)
        messagesTimestamp.add(System.currentTimeMillis())
        if (logger) println("[Kafka Producer] sent message: ${String(message)}")
    }

    override fun close() {
        println("[Kafka Producer] closing...")
        producer.close()
    }
}
