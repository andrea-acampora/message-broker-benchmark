import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import common.Consumer
import common.KafkaConfig
import common.Producer
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.io.File
import java.io.StringReader
import java.util.*


fun main(){
    val file = File(object {}.javaClass.getResource("kafka.yaml")?.toURI())
    val config: KafkaConfig = ObjectMapper(YAMLFactory()).registerKotlinModule().readValue(file.readText())
    val commonProperties = Properties()
    commonProperties.load(StringReader(config.commonConfig))
    val consumerProperties = Properties()
    commonProperties.forEach { key, value ->
        consumerProperties[key] = value
    }
    consumerProperties[ConsumerConfig.GROUP_ID_CONFIG] = "test-group"
    consumerProperties.load(StringReader(config.consumerConfig))
    consumerProperties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.getName()
    consumerProperties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.getName()
    val producerProperties = Properties()
    commonProperties.forEach { key, value ->
        producerProperties[key] = value
    }
    producerProperties.load(StringReader(config.producerConfig))
    producerProperties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.getName()
    producerProperties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.getName()
    val adminClient = AdminClient.create(commonProperties)
    val topic = NewTopic("topic-1", 1, 1)
    adminClient.createTopics(listOf(topic))
    val consumer = Consumer(consumerProperties, topic)
    val producer = Producer(producerProperties, topic)
    producer.sendMessage()
    consumer.init()
}
