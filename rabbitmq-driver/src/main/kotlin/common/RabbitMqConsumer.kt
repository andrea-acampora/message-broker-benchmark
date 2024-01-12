package common

import BenchmarkConsumer
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import java.nio.charset.Charset

class RabbitMQConsumer(
    private val queueName: String
): BenchmarkConsumer{
    private val factory = ConnectionFactory()

    override val timeList: ArrayList<Long> = arrayListOf()

    init {
        factory.host = "localhost"
    }

    private val connection: Connection = factory.newConnection()
    private val channel: Channel = connection.createChannel()

    override fun receive() {
        channel.queueDeclare(queueName, false, false, true, null)
        val callback = DeliverCallback { _, _ ->
                timeList.add(System.currentTimeMillis())
               // val message = String(delivery.body, Charset.defaultCharset())
               // println("[RabbitMQ Consumer]: Received message: $message")
        }
        channel.basicConsume(queueName, callback, CancelCallback {  })
    }

    fun close() {
        channel.close()
        connection.close()
    }
}
