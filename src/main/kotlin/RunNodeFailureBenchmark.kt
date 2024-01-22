import common.KafkaLoader
import common.PulsarLoader
import common.RabbitMQLoader
import consumer.BenchmarkConsumer
import producer.BenchmarkProducer

/** The duration of the node failure benchmark for each Broker. */
const val TEST_DURATION = 1000000L

/**
 * Run a Node Failure Benchmark for all Message Brokers.
 */
fun main() {
    testKafkaNodeFailure(TEST_DURATION)
    testPulsarNodeFailure(TEST_DURATION)
    testRabbitMQNodeFailure(TEST_DURATION)
}

private fun testNodeFailure(producer: BenchmarkProducer<ByteArray>, consumer: BenchmarkConsumer, duration: Long) =
    NodeFailureBenchmark(producer, consumer, duration).let { benchmark ->
        benchmark.runTest()
        producer.close()
        consumer.close()
        benchmark.collectResult()
    }

private fun testKafkaNodeFailure(duration: Long) =
    with(KafkaLoader("/kafka-multi-broker.yml", "topic-1")) {
        testNodeFailure(this.producer, this.consumer, duration)
    }

 private fun testPulsarNodeFailure(duration: Long) =
    with(PulsarLoader("/pulsar-multi-broker.yml", "topic-1")) {
        testNodeFailure(this.producer, this.consumer, duration)
    }

 private fun testRabbitMQNodeFailure(duration: Long) =
    with(RabbitMQLoader("/rabbitmq-multi-broker.yml","queue-1")) {
        testNodeFailure(this.producer, this.consumer, duration)
    }
