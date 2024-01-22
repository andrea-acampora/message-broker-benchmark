import common.KafkaLoader
import common.PulsarLoader
import common.RabbitMQLoader
import consumer.BenchmarkConsumer
import producer.BenchmarkProducer

/** The number of messages sent in the Latency Benchmark for each broker. */
const val MESSAGE_COUNT = 5000

/**
 * Run a Latency Benchmark for all Message Brokers.
 */
fun main() {
    testKafkaLatency(MESSAGE_COUNT)
    testPulsarLatency(MESSAGE_COUNT)
    testRabbitMQLatency(MESSAGE_COUNT)
}

private fun testLatency(producer: BenchmarkProducer<ByteArray>, consumer: BenchmarkConsumer, messageCount: Int) =
    LatencyBenchmark(producer, consumer, messageCount).let { benchmark ->
        benchmark.runTest()
        producer.close()
        consumer.close()
        println(benchmark.collectResult())
    }

private fun testKafkaLatency(messageCount: Int) =
    with(KafkaLoader("/kafka.yml", "topic-1")) {
        testLatency(this.producer, this.consumer, messageCount)
    }

private fun testPulsarLatency(messageCount: Int) =
    with(PulsarLoader("/pulsar.yml", "topic-1")) {
        testLatency(this.producer, this.consumer, messageCount)
    }

private fun testRabbitMQLatency(messageCount: Int) =
    with(RabbitMQLoader("/rabbitmq-multi-broker.yml","queue-1")) {
        testLatency(this.producer, this.consumer, messageCount)
    }
