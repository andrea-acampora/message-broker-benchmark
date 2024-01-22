import com.rabbitmq.client.Address
import common.RabbitMQLoader
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.delay
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.utility.DockerImageName

/**
 * Testing RabbitMQ Producer and RabbitMQ Consumer using a RabbitMQ Container.
 */
class RabbitMQTests : StringSpec({

    with(RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management-alpine"))) {

        this.start()
        val loader = RabbitMQLoader(
            "/rabbitmq.yml",
            "topic-test",
            listOf(Address(this.host, this.amqpPort)),
        )

        "It should be possible to create a RabbitMQ Producer" {
            loader.producer shouldNotBe null
        }

        "RabbitMQ Producer should have an empty messages list at start" {
            loader.producer.messagesTimestamp shouldHaveSize 0
        }

        "RabbitMQ Producer should be able to send a message" {
            loader.producer.send("Testing producer....".encodeToByteArray(), true)
            loader.producer.messagesTimestamp shouldHaveSize 1
        }

        "It should be possible to create a RabbitMQ Consumer" {
            loader.consumer shouldNotBe null
        }

        "RabbitMQ Consumer should have an empty messages list at start" {
            loader.consumer.messagesTimestamp shouldHaveSize 0
        }

        "RabbitMQ Consumer should be able to receive a message" {
            loader.producer.send("Testing producer....".encodeToByteArray(), true)
            loader.consumer.receive(true)
            delay(2000)
            loader.consumer.messagesTimestamp.size shouldBeGreaterThan 0
        }
    }
})
