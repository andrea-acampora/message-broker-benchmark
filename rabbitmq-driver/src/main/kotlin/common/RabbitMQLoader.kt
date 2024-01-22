package common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rabbitmq.client.Address
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import common.config.RabbitMQConfig

/**
 * A Loader of RabbitMQ producer and consumer given a [queueName],
 * a [configurationFile] and an optional [serviceUrl].
 */
class RabbitMQLoader(
    private val configurationFile: String,
    private val queueName: String,
    private val serviceUrl: String? = null,
) {

    /** The Kafka Producer. */
    lateinit var producer: RabbitMQProducer

    /** The Kafka Consumer. */
    lateinit var consumer: RabbitMQConsumer

    init {

        object {}.javaClass.getResourceAsStream(configurationFile)?.let {
            val config: RabbitMQConfig = ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(it.readAllBytes())
            val factory = ConnectionFactory().also { factory ->
                factory.username = config.username
                factory.password = config.password
            }
            val connection: Connection = factory.newConnection(config.nodes.map { address ->
                Address(address.host, address.port)
            })
            val channel = connection.createChannel()
            channel.queueDeclare(
                queueName, true, false, false, mapOf("x-queue-type" to "quorum","ha-mode" to "all")
            )
            producer = RabbitMQProducer(queueName, channel)
            consumer = RabbitMQConsumer(queueName, channel)
        }

    }

}
