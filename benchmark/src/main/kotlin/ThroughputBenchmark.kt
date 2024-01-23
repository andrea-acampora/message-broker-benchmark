import consumer.BenchmarkConsumer
import producer.BenchmarkProducer

/**
 * The throughput benchmark for a Message Broker.
 * It uses a [producer] to send messages in a given [testDuration]
 * and a [consumer] to consume them.
 * It calculates the megabytes sent in the given time.
 */
class ThroughputBenchmark(
    private val producer: BenchmarkProducer<ByteArray>,
    private val consumer: BenchmarkConsumer,
    private val testDuration: Long,
) : Benchmark<Double> {

    override fun runTest() {
        consumer.receive(false)
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < testDuration) {
            producer.send(ByteArray(KILOBYTE_SIZE), false)
        }
    }

    override fun collectResult(): Double =
        (producer.messagesTimestamp.size.toDouble() / KILOBYTE_SIZE) /
            (testDuration.toDouble() / MILLISECONDS_IN_SECONDS)

    companion object {
        /** Property used to send messages of 1 Kilobytes. */
        const val KILOBYTE_SIZE = 1024

        /** Property used to calculate the result in seconds. */
        const val MILLISECONDS_IN_SECONDS = 1000
    }
}
