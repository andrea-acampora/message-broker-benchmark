import common.KafkaLoader
import common.PulsarLoader
import common.RabbitMQLoader
import producer.BenchmarkProducer

/** The duration of the node failure benchmark for each Broker. */
const val DURATION = 20000L

/**
 * Run a Throughput Benchmark for all Message Brokers.
 */
fun main() {
    testKafkaThroughput(DURATION)
    testPulsarThroughput(DURATION)
    testRabbitMQThroughput(DURATION)
}

private fun testThroughput(producer: BenchmarkProducer<ByteArray>, duration: Long): Double =
    ThroughputBenchmark(producer, duration).let { benchmark ->
        benchmark.runTest()
        producer.close()
        return benchmark.collectResult()
    }

private fun testKafkaThroughput(duration: Long) =
    with(KafkaLoader("/kafka.yml", "topic-1")) {
        testThroughput(this.producer, duration)
    }

private fun testPulsarThroughput(duration: Long) =
    with(PulsarLoader("/pulsar.yml", "topic-1")) {
        testThroughput(this.producer, duration)
    }

private fun testRabbitMQThroughput(duration: Long) =
    with(RabbitMQLoader("/rabbitmq.yml","queue-1")) {
        testThroughput(this.producer, duration)
    }
