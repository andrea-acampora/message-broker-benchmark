package consumer

/**
 * A consumer for a Message Broker Benchmark.
 */
interface BenchmarkConsumer {

    /** The list with the timestamp of the received messages. */
    val messagesTimestamp: ArrayList<Long>

    /** Start receiving messages from the Broker. */
    fun receive(logger: Boolean)

    /** Close the connection with the broker. */
    fun close()
}
