package common

import BenchmarkProducer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.Properties

class KafkaProducer(
    properties: Properties,
    private val topic: String
): BenchmarkProducer<ByteArray> {
    override val timeList: ArrayList<Long> = arrayListOf()

    private val producer: KafkaProducer<String, ByteArray> = KafkaProducer<String, ByteArray>(properties)


    override fun send(message: ByteArray, logger: Boolean) {
        val record: ProducerRecord<String, ByteArray> = ProducerRecord(topic, message)
        producer.send(record)
        timeList.add(System.currentTimeMillis())
        if(logger) println("[Kafka Producer] sent message: ${String(message)}")
    }

    fun close() {
        println("[Kafka Producer] closing...")
        producer.close()
    }
}
