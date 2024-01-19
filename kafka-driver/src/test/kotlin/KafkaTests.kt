import common.KafkaLoader
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.delay
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

/**
 * Testing Kafka Producer and Kafka Consumer using a Kafka Container.
 */
class KafkaTests : StringSpec({

    with(KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0")).withKraft()) {

        this.start()
        val loader = KafkaLoader("/kafka-test.yml", "topic-test", this.bootstrapServers)

        "It should be possible to create a Kafka Producer" {
            loader.producer shouldNotBe null
        }

        "Kafka Producer should have an empty messages list at start" {
            loader.producer.messagesTimestamp shouldHaveSize 0
        }

        "Kafka Producer should be able to send a message" {
            loader.producer.send("Testing producer....".encodeToByteArray(), true)
            loader.producer.messagesTimestamp shouldHaveSize 1
        }

        "It should be possible to create a Kafka Consumer" {
            loader.consumer shouldNotBe null
        }

        "Kafka Consumer should have an empty messages list at start" {
            loader.consumer.messagesTimestamp shouldHaveSize 0
        }

        "Kafka Consumer should be able to receive a message" {
            loader.producer.send("Testing producer....".encodeToByteArray(), true)
            loader.consumer.receive(true)
            delay(2000)
            loader.consumer.messagesTimestamp.size shouldBeGreaterThan 0
        }
    }
})
