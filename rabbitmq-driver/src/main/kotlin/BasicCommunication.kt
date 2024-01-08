import common.RabbitMQConsumer
import common.RabbitMQProducer
import common.RabbitMqLoader

fun main(){
    val loader = RabbitMqLoader("queue-1")
    val producer: RabbitMQProducer = loader.producer
    val consumer: RabbitMQConsumer = loader.consumer

    producer.produce("Ciao")
    consumer.consume()

    producer.close()
    consumer.close()
}
