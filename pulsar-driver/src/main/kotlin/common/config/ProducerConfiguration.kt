package common.config

/** The value of the batching max byes. */
const val BATCHING_MAX_BYTES = 1024 * 1024

/**
 * The configuration file section for the Pulsar Producer.
 * - [batchingEnabled]: value to enable the batch mode.
 * - [batchingMaxPublishDelayMs]: the maximum batching delay.
 * - [batchingMaxBytes]: the maximum batching bytes.
 * - [blockIfQueueFull]: block the broker if the queue is full.
 * - [pendingQueueSize]: the size of the pending queue.
 */
data class ProducerConfiguration(
    val batchingEnabled: Boolean = true,
    val batchingMaxPublishDelayMs: Long = 1L,
    val batchingMaxBytes: Int = BATCHING_MAX_BYTES,
    val blockIfQueueFull: Boolean = true,
    val pendingQueueSize: Int = 0,
)
