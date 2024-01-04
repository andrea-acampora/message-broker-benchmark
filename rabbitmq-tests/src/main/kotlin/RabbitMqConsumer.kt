import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import java.io.IOException
import java.nio.charset.StandardCharsets

class RabbitMQConsumer {
    private val QUEUE_NAME = "hello"

    fun consume() {
        val factory = ConnectionFactory()
        factory.host = "localhost"

        try {
            val connection: Connection = factory.newConnection()
            val channel: Channel = connection.createChannel()

            channel.queueDeclare(QUEUE_NAME, false, false, false, null)

            val consumer: Consumer = object : DefaultConsumer(channel) {
                @Throws(IOException::class)
                override fun handleDelivery(
                    consumerTag: String, envelope: Envelope,
                    properties: AMQP.BasicProperties, body: ByteArray
                ) {
                    val message = String(body, StandardCharsets.UTF_8)
                    println(" [x] Received '$message'")
                }
            }

            channel.basicConsume(QUEUE_NAME, true, consumer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}