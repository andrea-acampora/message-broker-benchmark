package common

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*

class Consumer(properties: Properties, private val topic: NewTopic) {
    private val consumer: KafkaConsumer<String, String> = KafkaConsumer<String, String>(properties).also {
        it.subscribe(listOf(topic.name()))
    }

    fun init() {
        while (true) {
            val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofMillis(1000))
            records.forEach { record ->
                println("Consumed message number ${record.value()}")
            }
        }
    }

    fun close() {
        consumer.close()
    }
}
