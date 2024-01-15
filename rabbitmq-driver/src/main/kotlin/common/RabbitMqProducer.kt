package common

import BenchmarkProducer
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets

class RabbitMQProducer(
    private val queueName: String
): BenchmarkProducer<ByteArray> {

    private val factory = ConnectionFactory()
    override val timeList: ArrayList<Long> = arrayListOf()

    init {
        factory.host = "localhost"
    }

    private val connection: Connection = factory.newConnection()
    private val channel: Channel = connection.createChannel().also {
        it.queueDeclare(queueName, false, false, true, null)
    }

    override fun send(message: ByteArray, logger: Boolean) {
        channel.basicPublish("", queueName, null, message)
        timeList.add(System.currentTimeMillis())
        if(logger) println("[RabbitMQ Producer] sent message: ${String(message)}")
    }

    fun close(){
        channel.queuePurge(queueName)
        channel.close()
        connection.close()
    }
}
