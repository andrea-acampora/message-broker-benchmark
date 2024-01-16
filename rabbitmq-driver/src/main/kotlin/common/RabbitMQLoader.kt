package common

/**
 * A Loader of RabbitMQ producer and consumer given a [queueName].
 */
data class RabbitMQLoader(
    val queueName: String,

    /** The RabbitMQ Producer. */
    val producer: RabbitMQProducer = RabbitMQProducer(queueName),

    /** The RabbitMQ Consumer. */
    val consumer: RabbitMQConsumer = RabbitMQConsumer(queueName),
)
