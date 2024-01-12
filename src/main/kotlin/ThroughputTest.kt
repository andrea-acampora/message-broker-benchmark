import common.KafkaLoader
import common.PulsarLoader
import common.RabbitMqLoader

fun main() {
    val kafkaThroughput = testKafkaThroughput()
    val pulsarThroughput = testPulsarThroughput()
    val rabbitThroughput = testRabbitThroughput()
    println("kafka = $kafkaThroughput")
    println("pulsar = $pulsarThroughput")
    println("rabbit = $rabbitThroughput")
}

fun testKafkaThroughput(): Double {
    val kafkaLoader = KafkaLoader("/kafka.yml", "topic-1")
    val kafkaBenchmarkThroughput = TestThroughput(kafkaLoader.producer, 5000)
    kafkaBenchmarkThroughput.runTest()
    return kafkaBenchmarkThroughput.collectResult()
}

fun testPulsarThroughput(): Double {
    val pulsarLoader = PulsarLoader("/pulsar.yml", "topic-1")
    val pulsarBenchmarkThroughput = TestThroughput(pulsarLoader.producer, 5000)
    pulsarBenchmarkThroughput.runTest()
    return pulsarBenchmarkThroughput.collectResult()
}

fun testRabbitThroughput(): Double {
    val rabbitMqLoader = RabbitMqLoader("queue-1")
    val rabbitMQBenchmarkThroughput = TestThroughput(rabbitMqLoader.producer, 5000)
    rabbitMQBenchmarkThroughput.runTest()
    return rabbitMQBenchmarkThroughput.collectResult()
}