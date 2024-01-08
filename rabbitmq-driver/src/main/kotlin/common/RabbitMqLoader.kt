package common

data class RabbitMqLoader(
    val queueName: String,
    val producer: RabbitMQProducer = RabbitMQProducer(queueName),
    val consumer: RabbitMQConsumer = RabbitMQConsumer(queueName)
)
