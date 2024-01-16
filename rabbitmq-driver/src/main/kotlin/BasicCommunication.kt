import common.RabbitMQConsumer
import common.RabbitMQLoader
import common.RabbitMQProducer

/**
 * Basic usage of a RabbitMQ Broker with a Producer and a Consumer.
 */
fun main() {
    val loader = RabbitMQLoader("queue-1")
    val producer: RabbitMQProducer = loader.producer
    val consumer: RabbitMQConsumer = loader.consumer

    producer.send("Hello World!".toByteArray(), true)
    consumer.receive(true)

    producer.close()
    consumer.close()
}
