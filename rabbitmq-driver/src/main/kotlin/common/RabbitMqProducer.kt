package common

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets

class RabbitMQProducer(
    private val queueName: String = "queue-1"
) {

    private val factory = ConnectionFactory()

    init {
        factory.host = "localhost"
    }

    private val connection: Connection = factory.newConnection()
    private val channel: Channel = connection.createChannel().also {
        it.queueDeclare(queueName, false, false, true, null)
    }

    fun produce(message: String) {
        channel.basicPublish("", queueName, null, message.toByteArray(StandardCharsets.UTF_8))
        println("[RabbitMQ Producer] sent message: $message")
    }

    fun close(){
        channel.queuePurge(queueName)
        channel.close()
        connection.close()
    }
}
