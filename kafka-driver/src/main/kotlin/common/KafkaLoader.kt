package common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.io.File
import java.io.StringReader
import java.util.Properties

class KafkaLoader(
    configurationFile: String,
    topicName: String
) {

    lateinit var producer: KafkaProducer
    lateinit var consumer: KafkaConsumer

    init {
        object {}.javaClass.getResource(configurationFile)?.let {
            val config: KafkaConfig = ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(File(it.toURI()))
            val commonProperties = Properties()
            commonProperties.load(StringReader(config.commonConfig))
            val consumerProperties = Properties()
            commonProperties.forEach { key, value ->
                consumerProperties[key] = value
            }
            consumerProperties[ConsumerConfig.GROUP_ID_CONFIG] = "test-group"
            consumerProperties.load(StringReader(config.consumerConfig))
            consumerProperties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
            consumerProperties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ByteArrayDeserializer::class.java.name
            val producerProperties = Properties()
            commonProperties.forEach { key, value ->
                producerProperties[key] = value
            }
            producerProperties.load(StringReader(config.producerConfig))
            producerProperties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
            producerProperties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = ByteArraySerializer::class.java.name

            this.producer = KafkaProducer(producerProperties, topicName)
            this.consumer = KafkaConsumer(consumerProperties, topicName)
        }
    }
}
