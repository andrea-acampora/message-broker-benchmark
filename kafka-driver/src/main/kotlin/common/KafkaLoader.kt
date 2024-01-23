package common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.clients.admin.Admin
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.io.StringReader
import java.util.Properties

/**
 * A Loader for a Kafka Producer and Kafka Consumer given a configuration file, a topic
 * and optionally a bootstrap server.
 */
class KafkaLoader(
    configurationFile: String,
    topicName: String,
    private val bootstrapServer: String? = null,
) {

    /** The Kafka Producer. */
    lateinit var producer: KafkaProducer

    /** The Kafka Consumer. */
    lateinit var consumer: KafkaConsumer

    init {
        object {}.javaClass.getResourceAsStream(configurationFile)?.let {
            val config: KafkaConfig = ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(it.readAllBytes())
            val commonProperties = Properties()
            val consumerProperties = Properties()
            val producerProperties = Properties()

            commonProperties.load(StringReader(config.commonConfig))
            commonProperties["bootstrap.servers"] = this.bootstrapServer ?: commonProperties["bootstrap.servers"]
            commonProperties.forEach { key, value ->
                consumerProperties[key] = value
                producerProperties[key] = value
            }
            consumerProperties.load(StringReader(config.consumerConfig))
            consumerProperties[ConsumerConfig.GROUP_ID_CONFIG] = "test-group"
            consumerProperties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
            consumerProperties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ByteArrayDeserializer::class.java.name

            producerProperties.load(StringReader(config.producerConfig))
            producerProperties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
            producerProperties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = ByteArraySerializer::class.java.name

            with(Admin.create(commonProperties)) {
                if (!this.listTopics().names().get().contains(topicName)) {
                    this.createTopics(
                        arrayListOf(NewTopic(topicName, config.partitions, config.replicationFactor)),
                    ).values()[topicName]?.get()
                }
                this.close()
            }
            this.producer = KafkaProducer(producerProperties, topicName)
            this.consumer = KafkaConsumer(consumerProperties, topicName)
        }
    }
}
