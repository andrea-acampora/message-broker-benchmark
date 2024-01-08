import common.RabbitMQConsumer
import common.RabbitMQProducer

fun main(){

    val producer = RabbitMQProducer()
    val consumer = RabbitMQConsumer()

    producer.produce("Ciao")
    consumer.consume()

    producer.close()
    consumer.close()
}
