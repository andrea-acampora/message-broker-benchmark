package common.config

/**
 * A Generic RabbitMQ Address with the [host] and a [port].
 */
data class RabbitAddress(val host: String, val port: Int)
