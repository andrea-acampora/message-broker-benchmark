fun main(){
    val producer = RabbitMQProducer()
    val consumer = RabbitMQConsumer()

    Thread {
        for (i in 1..10) {
            val message = "Message $i"
            producer.produce(message)
            Thread.sleep(1000)
        }
    }.start()

    consumer.consume()
}