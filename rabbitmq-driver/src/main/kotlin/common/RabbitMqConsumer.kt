package common

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import java.nio.charset.Charset

class RabbitMQConsumer(
    private val queueName: String
) {
    private val factory = ConnectionFactory()

    init {
        factory.host = "localhost"
    }

    private val connection: Connection = factory.newConnection()
    private val channel: Channel = connection.createChannel()

    fun consume() {
        channel.queueDeclare(queueName, false, false, true, null)
        val callback = DeliverCallback { _, delivery ->
                val message = String(delivery.body, Charset.defaultCharset())
                println("[RabbitMQ Consumer]: Received message: $message")
        }
        channel.basicConsume(queueName, callback, CancelCallback {  })
    }

    fun close() {
        channel.close()
        connection.close()
    }
}
