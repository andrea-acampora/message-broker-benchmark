import common.RabbitMQConsumer
import common.RabbitMQLoader
import common.RabbitMQProducer

/**
 * Basic usage of a RabbitMQ Broker with a Producer and a Consumer.
 */
fun main() {
    val loader = RabbitMQLoader("/rabbitmq.yml","queue-1")
    val producer: RabbitMQProducer = loader.producer
    val consumer: RabbitMQConsumer = loader.consumer
    consumer.receive(true)
    producer.send("Hello World!".toByteArray(), true)
}
