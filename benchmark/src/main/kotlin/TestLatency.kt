class TestLatency(val producer: BenchmarkProducer<String>, val consumer: BenchmarkConsumer) {
    fun runTest(messageCount: Int) {
        Thread{
            consumer.receive()
        }.start()
        (1..messageCount).forEach { num ->
            producer.send("message number $num")
            Thread.sleep(500)
        }
    }

    fun collectResult(): List<Long> {
        val allLatency = consumer.timeList.zip(producer.timeList).map { (a, b) ->
            a - b
        }

        return allLatency
    }
}