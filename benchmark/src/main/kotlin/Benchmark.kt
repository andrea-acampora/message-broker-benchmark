/**
 * A generic [Benchmark] for a Message Broker.
 */
interface Benchmark<T> {

    /** Run the benchmark test. */
    fun runTest()

    /** Collect the result of the benchmark. */
    fun collectResult(): T
}
