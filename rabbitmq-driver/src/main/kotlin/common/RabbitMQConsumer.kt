package common

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Connection
import com.rabbitmq.client.DeliverCallback
import consumer.BenchmarkConsumer
import java.nio.charset.Charset

/**
 * A RabbitMQ Consumer given a [queueName].
 */
class RabbitMQConsumer(
    private val queueName: String,
    connection: Connection,
    ) : BenchmarkConsumer {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()
    private val channel = connection.createChannel()

    override fun receive(logger: Boolean) {
        channel.queueDeclare(queueName, false, false, true, null)
        channel.basicConsume(
            queueName,
            true,
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
    }
}
