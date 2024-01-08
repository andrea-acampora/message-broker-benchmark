fun main(){
    val producer = RabbitMQProducer()
    val consumer = RabbitMQConsumer()

    consumer.consume()
    producer.produce()
}