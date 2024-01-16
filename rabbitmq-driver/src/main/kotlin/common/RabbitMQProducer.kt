package common

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import producer.BenchmarkProducer

/**
 * A RabbitMQ Producer given a [queueName].
 */
class RabbitMQProducer(
    private val queueName: String,
) : BenchmarkProducer<ByteArray> {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()
    private val factory = ConnectionFactory()
    private val connection: Connection = factory.newConnection()
    private val channel: Channel = connection.createChannel().also {
        it.queueDeclare(queueName, false, false, true, null)
    }

    init {
        factory.host = "localhost"
    }

    override fun send(message: ByteArray, logger: Boolean) {
        channel.basicPublish("", queueName, null, message)
        messagesTimestamp.add(System.currentTimeMillis())
        if (logger) println("[RabbitMQ Producer] sent message: ${String(message)}")
    }

    override fun close() {
        channel.queuePurge(queueName)
        channel.close()
        connection.close()
    }
}
