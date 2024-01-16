package common

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import consumer.BenchmarkConsumer
import java.nio.charset.Charset

/**
 * A RabbitMQ Consumer given a [queueName].
 */
class RabbitMQConsumer(
    private val queueName: String,
) : BenchmarkConsumer {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()
    private val factory = ConnectionFactory()
    private val connection: Connection = factory.newConnection()
    private val channel: Channel = connection.createChannel()

    init {
        factory.host = "localhost"
    }

    override fun receive(logger: Boolean) {
        channel.queueDeclare(queueName, false, false, true, null)
        channel.basicConsume(
            queueName,
            DeliverCallback { _, delivery ->
                messagesTimestamp.add(System.currentTimeMillis())
                if (logger) {
                    val message = String(delivery.body, Charset.defaultCharset())
                    println("[RabbitMQ Consumer]: Received message: $message")
                }
            },
            CancelCallback { },
        )
    }

    override fun close() {
        channel.close()
        connection.close()
    }
}
