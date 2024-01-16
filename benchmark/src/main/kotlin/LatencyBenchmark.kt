import consumer.BenchmarkConsumer
import producer.BenchmarkProducer

/**
 * The end-to-end latency benchmark for a Message Broker.
 * It uses a [producer] to send messages and a [consumer] to receive messages.
 * It calculates the end-to-end latency between the send and the reception of each message.
 */
class LatencyBenchmark(
    private val producer: BenchmarkProducer<ByteArray>,
    private val consumer: BenchmarkConsumer,
    private val messageCount: Int,
) : Benchmark<List<Long>> {

    override fun runTest() {
        consumer.receive(true)
        Thread.sleep(STARTING_SLEEP_TIME)
        repeat(messageCount) {
            producer.send("message number $it".toByteArray(), true)
            Thread.sleep(SLEEP_TIME)
        }
    }

    override fun collectResult(): List<Long> =
        consumer.messagesTimestamp.zip(producer.messagesTimestamp).map { (a, b) ->
            a - b
        }

    companion object {
        /** The starting sleep time before sending messages. */
        const val STARTING_SLEEP_TIME: Long = 5000

        /** The sleep time between each message. */
        const val SLEEP_TIME: Long = 1000
    }
}
