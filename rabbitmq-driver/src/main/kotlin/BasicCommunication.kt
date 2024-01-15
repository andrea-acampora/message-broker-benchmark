import common.RabbitMQConsumer
import common.RabbitMQProducer
import common.RabbitMqLoader

fun main(){
    val loader = RabbitMqLoader("queue-1")
    val producer: RabbitMQProducer = loader.producer
    val consumer: RabbitMQConsumer = loader.consumer

    producer.send("Hello World!".toByteArray(), true)
    consumer.receive(true)

    producer.close()
    consumer.close()
}
