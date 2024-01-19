import common.PulsarLoader
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.delay
import org.testcontainers.containers.PulsarContainer
import org.testcontainers.utility.DockerImageName

/**
 * Testing Pulsar Producer and Pulsar Consumer using a Pulsar Container.
 */
class PulsarTests : StringSpec({

    with(PulsarContainer(DockerImageName.parse("apachepulsar/pulsar:latest"))) {

        this.start()
        val loader = PulsarLoader("/pulsar.yml", "topic-test", this.pulsarBrokerUrl)

        "It should be possible to create a Pulsar Producer" {
            loader.producer shouldNotBe null
        }

        "Pulsar Producer should have an empty messages list at start" {
            loader.producer.messagesTimestamp shouldHaveSize 0
        }

        "Pulsar Producer should be able to send a message" {
            loader.producer.send("Testing producer....".encodeToByteArray(), true)
            loader.producer.messagesTimestamp shouldHaveSize 1
        }

        "It should be possible to create a Pulsar Consumer" {
            loader.consumer shouldNotBe null
        }

        "Pulsar Consumer should have an empty messages list at start" {
            loader.consumer.messagesTimestamp shouldHaveSize 0
        }

        "Pulsar Consumer should be able to receive a message" {
            loader.producer.send("Testing producer....".encodeToByteArray(), true)
            loader.consumer.receive(true)
            delay(2000)
            loader.consumer.messagesTimestamp.size shouldBeGreaterThan 0
        }
    }
})
