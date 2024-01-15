interface BenchmarkConsumer {
    val timeList: ArrayList<Long>
    fun receive(logger: Boolean)
}