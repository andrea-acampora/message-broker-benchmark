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
            (1..10).forEach { num ->
                val record: ProducerRecord<String, String> = ProducerRecord(topic.name(), "$num")
                println("sent message number $num")
                producer.send(record)
            }
        }
    }

    private fun close() {
        producer.close()
    }
}