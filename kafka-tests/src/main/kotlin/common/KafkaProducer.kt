package common

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*
import java.util.concurrent.Executors

class Producer(properties: Properties, private val topic: NewTopic) {
    private val producer: KafkaProducer<String, String> = KafkaProducer(properties)

    fun sendMessage() {
        val executor = Executors.newSingleThreadExecutor()
        executor.submit {
            listOf(3, 2, 4).forEach { num ->
                val record: ProducerRecord<String, String> = ProducerRecord(topic.name(), num.toString())
                println("produced $record")
                producer.send(record)
            }
        }
    }

    private fun close() {
        producer.close()
    }
}