package common

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.Properties

class KafkaProducer(
    properties: Properties,
    private val topic: NewTopic
) {

    private val producer: KafkaProducer<String, ByteArray> = KafkaProducer(properties)

    fun sendMessage(message: String) {
        val record: ProducerRecord<String, ByteArray> = ProducerRecord(topic.name(), message.toByteArray())
        producer.send(record)
        println("[Kafka Producer] sent message: $message")
    }

    fun close() {
        println("[Kafka Producer] closing...")
        producer.close()
    }
}
