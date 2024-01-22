package common

import com.rabbitmq.client.Channel
import producer.BenchmarkProducer

/**
 * A RabbitMQ Producer given a [queueName].
 */
class RabbitMQProducer(
    private val queueName: String,
    private val channel: Channel
) : BenchmarkProducer<ByteArray> {

    override val messagesTimestamp: ArrayList<Long> = arrayListOf()

    override fun send(message: ByteArray, logger: Boolean) {
        try {
            channel.basicPublish("", queueName, null, message)
            messagesTimestamp.add(System.currentTimeMillis())
            if (logger) println("[RabbitMQ Producer] sent message: ${String(message)}")
        } catch (e: Exception) {
            println("Waiting channel opening...")
        }
    }

    override fun close() {
        println("[RabbitMQ Producer] closing..")
    }
}
