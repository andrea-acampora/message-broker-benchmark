package producer

/**
 * A producer for a Message Broker Benchmark.
 */
interface BenchmarkProducer<T> {

    /** The list with the timestamp of the messages sent. */
    val messagesTimestamp: ArrayList<Long>

    /** Send a [message] to the broker.  */
    fun send(message: T, logger: Boolean)

    /** Close the connection with the broker. */
    fun close()
}
