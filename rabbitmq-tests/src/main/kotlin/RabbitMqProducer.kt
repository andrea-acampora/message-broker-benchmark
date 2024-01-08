import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class RabbitMQProducer {
    private val queueName = "simpleQueue"
    private val factory = ConnectionFactory()

    init {
        factory.host = "localhost"
    }

    private val connection: Connection = factory.newConnection()
    private val channel: Channel = connection.createChannel()

    fun produce() {
        channel.queueDeclare(queueName, false, false, false, null)
        Thread{
            (1..5).forEach { num ->
                channel.basicPublish("", queueName, null, num.toString().toByteArray(StandardCharsets.UTF_8))
                println(" sent message number '$num'")
            }
        }.start()
    }

    fun close(){
        channel.close()
        connection.close()
    }
}

