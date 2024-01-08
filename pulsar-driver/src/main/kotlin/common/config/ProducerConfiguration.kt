package common.config

data class ProducerConfiguration(
    val batchingEnabled: Boolean = true,
    val batchingMaxPublishDelayMs: Long = 1L,
    val batchingMaxBytes: Int = 1024 * 1024,
    val blockIfQueueFull: Boolean = true,
    val pendingQueueSize: Int = 0
)
