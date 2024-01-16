import common.KafkaLoader
import consumer.BenchmarkConsumer
import producer.BenchmarkProducer

/** The duration of the node failure benchmark for each Broker. */
const val TEST_DURATION = 20000L

/**
 * Run a Node Failure Benchmark for all Message Brokers.
 */
fun main() {
    testKafkaNodeFailure(TEST_DURATION)
}

private fun testNodeFailure(producer: BenchmarkProducer<ByteArray>, consumer: BenchmarkConsumer, duration: Long) =
    NodeFailureBenchmark(producer, consumer, duration).let { benchmark ->
        benchmark.runTest()
        producer.close()
        consumer.close()
        benchmark.collectResult()
    }

private fun testKafkaNodeFailure(duration: Long) =
    with(KafkaLoader("/kafka-partitioned.yml", "topic-1")) {
        testNodeFailure(this.producer, this.consumer, duration)
    }

// private fun testPulsarNodeFailure(duration: Long) =
//    with(PulsarLoader("/pulsar.yml", "topic-1")) {
//        testNodeFailure(this.producer, this.consumer, duration)
//    }
//
// private fun testRabbitMQNodeFailure(duration: Long) =
//    with(RabbitMQLoader("queue-1")) {
//        testNodeFailure(this.producer, this.consumer, duration)
//    }
