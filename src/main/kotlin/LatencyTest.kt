import common.KafkaLoader
import common.PulsarLoader
import common.RabbitMqLoader

fun main(){
    val kafkaLatency = testKafkaLatency()
    val pulsarLatency = testPulsarLatency()
    val rabbitMQLatency = testRabbitMQLatency()
    print(kafkaLatency)
    println(pulsarLatency)
    println(rabbitMQLatency)
}

fun testKafkaLatency(): List<Long>{
    val kafkaLoader = KafkaLoader("/kafka.yml", "topic-1")
    val kafkaBenchmarkLatency = TestLatency(kafkaLoader.producer, kafkaLoader.consumer)
    kafkaBenchmarkLatency.runTest(50)
    return kafkaBenchmarkLatency.collectResult()
}

fun testPulsarLatency(): List<Long>{
    val pulsarLoader = PulsarLoader("/pulsar.yml", "topic-1")
    val pulsarBenchmarkLatency = TestLatency(pulsarLoader.producer, pulsarLoader.consumer)
    pulsarBenchmarkLatency.runTest(50)
    return pulsarBenchmarkLatency.collectResult()
}

fun testRabbitMQLatency(): List<Long>{
    val rabbitMqLoader = RabbitMqLoader("queue-1")
    val rabbitMQBenchmarkLatency = TestLatency(rabbitMqLoader.producer, rabbitMqLoader.consumer)
    rabbitMQBenchmarkLatency.runTest(50)
    return rabbitMQBenchmarkLatency.collectResult()
}