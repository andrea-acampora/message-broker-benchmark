import common.KafkaLoader
import common.PulsarLoader
import common.RabbitMQLoader
import consumer.BenchmarkConsumer
import producer.BenchmarkProducer

/** The duration of the node failure benchmark for each Broker. */
const val DURATION = 60000L

/**
 * Run a Throughput Benchmark for all Message Brokers.
 */
fun main() {
    testKafkaThroughput(DURATION)
    testPulsarThroughput(DURATION)
    testRabbitMQThroughput(DURATION)
}

private fun testThroughput(producer: BenchmarkProducer<ByteArray>, consumer: BenchmarkConsumer, duration: Long) =
    ThroughputBenchmark(producer, consumer, duration).let { benchmark ->
        benchmark.runTest()
        producer.close()
        consumer.close()
        println(benchmark.collectResult())
    }

private fun testKafkaThroughput(duration: Long) =
    with(KafkaLoader("/kafka.yml", "topic-2")) {
        testThroughput(this.producer, this.consumer, duration)
    }

private fun testPulsarThroughput(duration: Long) =
    with(PulsarLoader("/pulsar.yml", "topic-2")) {
        testThroughput(this.producer, this.consumer, duration)
    }

private fun testRabbitMQThroughput(duration: Long) =
    with(RabbitMQLoader("/rabbitmq.yml", "queue-2")) {
        testThroughput(this.producer, this.consumer, duration)
    }
