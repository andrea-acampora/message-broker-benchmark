interface Benchmark<T> {

    fun runTest()

    fun collectResult() : T
}
