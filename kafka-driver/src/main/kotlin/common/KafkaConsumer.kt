package common

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.Properties

class KafkaConsumer(
    properties: Properties,
    private val topic: NewTopic
) {

    private val consumer: KafkaConsumer<String, ByteArray> = KafkaConsumer<String, ByteArray>(properties).also {
        it.subscribe(listOf(topic.name()))
    }

    fun consume() {
        while (true) {
            val records: ConsumerRecords<String, ByteArray> = consumer.poll(Duration.ofMillis(100))
            records.forEach { record ->
                println("[Kafka Consumer] received message: ${String(record.value())}")
            }
        }
    }

    fun close() {
        println("[Kafka Consumer] closing...")
        consumer.close()
    }
}
