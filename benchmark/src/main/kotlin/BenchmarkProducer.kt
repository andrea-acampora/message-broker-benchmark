interface BenchmarkProducer<T> {
    val timeList: ArrayList<Long>
    fun send(message: T, logger: Boolean)
}