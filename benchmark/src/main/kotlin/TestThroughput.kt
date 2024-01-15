class TestThroughput(val producer: BenchmarkProducer<ByteArray>, private val testDuration: Long) {
    fun runTest() {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < testDuration) {
            producer.send(ByteArray(1024), false)
        }
    }

    fun collectResult(): Double {
        val throughput: Double = (producer.timeList.size.toDouble() / 1024) / (testDuration.toDouble() / 1000)
        return throughput
    }
}