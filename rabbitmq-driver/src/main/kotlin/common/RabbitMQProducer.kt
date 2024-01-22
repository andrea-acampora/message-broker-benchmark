package common

import com.rabbitmq.client.Connection
import producer.BenchmarkProducer

/**
 * A RabbitMQ Producer given a [queueName].
 */
class RabbitMQProducer(
    private val queueName: String,
    connection: Connection,
) : BenchmarkProducer<ByteArray> {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()
    private val channel = connection.createChannel()

    override fun send(message: ByteArray, logger: Boolean) {
        channel.basicPublish("", queueName, null, message)
        messagesTimestamp.add(System.currentTimeMillis())
        if (logger) println("[RabbitMQ Producer] sent message: ${String(message)}")
    }

    override fun close() {
        channel.queuePurge(queueName)
        channel.close()
    }
}
