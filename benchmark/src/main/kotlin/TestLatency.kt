class TestLatency(val producer: BenchmarkProducer<ByteArray>, val consumer: BenchmarkConsumer) {
    fun runTest(messageCount: Int) {
        Thread{
            consumer.receive()
        }.start()
        (1..messageCount).forEach { num ->
            producer.send("message number $num".toByteArray())
            Thread.sleep(1000)
        }
    }

    fun collectResult(): List<Long> {
        val allLatency = consumer.timeList.zip(producer.timeList).map { (a, b) ->
            a - b
        }

        println(producer.timeList)

        return allLatency
    }
}