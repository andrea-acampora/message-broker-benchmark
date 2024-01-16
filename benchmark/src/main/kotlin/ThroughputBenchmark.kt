import producer.BenchmarkProducer

/**
 * The throughput benchmark for a Message Broker.
 * It uses a [producer] to send messages in a given [testDuration].
 * It calculates the megabytes sent in the given time.
 */
class ThroughputBenchmark(
    private val producer: BenchmarkProducer<ByteArray>,
    private val testDuration: Long,
) : Benchmark<Double> {

    override fun runTest() {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < testDuration) {
            producer.send(ByteArray(MEGABYTE_SIZE), false)
        }
    }

    override fun collectResult(): Double =
        (producer.messagesTimestamp.size.toDouble() / MEGABYTE_SIZE) /
            (testDuration.toDouble() / MILLISECONDS_IN_SECONDS)

    companion object {
        /** Property used to calculate the result in Megabytes. */
        const val MEGABYTE_SIZE = 1024

        /** Property used to calculate the result in seconds. */
        const val MILLISECONDS_IN_SECONDS = 1000
    }
}
