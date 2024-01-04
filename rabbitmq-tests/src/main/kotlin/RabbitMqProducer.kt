import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class RabbitMQProducer {
    private val QUEUE_NAME = "hello"

    fun produce(message: String) {
        val factory = ConnectionFactory()
        factory.host = "localhost" // Assicurati di inserire l'indirizzo corretto del tuo server RabbitMQ

        try {
            val connection: Connection = factory.newConnection()
            val channel: Channel = connection.createChannel()

            channel.queueDeclare(QUEUE_NAME, false, false, false, null)
            channel.basicPublish("", QUEUE_NAME, null, message.toByteArray(StandardCharsets.UTF_8))

            println(" [x] Sent '$message'")

            channel.close()
            connection.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

