import consumer.BenchmarkConsumer
import producer.BenchmarkProducer

/**
 * The Node Failure benchmark for a Message Broker.
 * It uses a [producer] to send messages and a [consumer] to receive messages.
 * It checks if they are able to work for the whole [testDuration] under sudden node failures of the broker.
 */
class NodeFailureBenchmark(
    private val producer: BenchmarkProducer<ByteArray>,
    private val consumer: BenchmarkConsumer,
    private val testDuration: Long,
) : Benchmark<Pair<List<Long>, List<Long>>> {

    override fun runTest() {
        consumer.receive(true)
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < testDuration) {
            producer.send(ByteArray(MEGABYTE_SIZE), false)
        }
    }

    override fun collectResult() = Pair(producer.messagesTimestamp, consumer.messagesTimestamp)

    companion object {

        /** Property used to send messages of 1 Megabyte. */
        const val MEGABYTE_SIZE = 1024
    }
}
