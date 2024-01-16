
import common.PulsarConsumer
import common.PulsarLoader
import common.PulsarProducer

/**
 * Basic usage of a Pulsar Broker with a Producer and a Consumer.
 */
fun main() {
    val loader = PulsarLoader("/pulsar.yml", "topic-1")
    val producer: PulsarProducer = loader.producer
    val consumer: PulsarConsumer = loader.consumer
    producer.send("Hello World!".toByteArray(), true)
    consumer.receive(true)
}
