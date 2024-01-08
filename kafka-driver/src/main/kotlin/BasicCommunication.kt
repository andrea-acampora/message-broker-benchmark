
import common.KafkaConsumer
import common.KafkaLoader
import common.KafkaProducer


fun main(){
    val loader = KafkaLoader("/kafka.yml","topic-1")
    val producer: KafkaProducer = loader.producer
    val consumer: KafkaConsumer = loader.consumer

    producer.sendMessage("Hello World!")
    consumer.consume()
}
