import common.KafkaConsumer
import common.KafkaLoader
import common.KafkaProducer

/**
 * Basic usage of a Kafka Broker with a Producer and a Consumer.
 */
fun main() {
    val loader = KafkaLoader("/kafka.yml", "topic-1")
    val producer: KafkaProducer = loader.producer
    val consumer: KafkaConsumer = loader.consumer
    consumer.receive(true)
    producer.send("hello world!".toByteArray(), true)
}
