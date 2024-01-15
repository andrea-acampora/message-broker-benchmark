class TestLatency(val producer: BenchmarkProducer<ByteArray>, val consumer: BenchmarkConsumer) {
    fun runTest(messageCount: Int) {
        consumer.receive(false)
        Thread.sleep(5000)
        (1..messageCount).forEach { num ->
            producer.send("message number $num".toByteArray(), false)
            Thread.sleep(1000)
        }
    }

    fun collectResult(): List<Long> {
        val allLatency = consumer.timeList.zip(producer.timeList).map { (a, b) ->
            a - b
        }
        return allLatency
    }
}