
import common.PulsarConsumer
import common.PulsarLoader
import common.PulsarProducer

fun main() {

    val loader = PulsarLoader("/pulsar.yml","topic-1")
    val producer: PulsarProducer = loader.producer
    val consumer: PulsarConsumer = loader.consumer
    producer.send("Hello World!".toByteArray())
    consumer.receive()
}
