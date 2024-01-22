package common.config

/**
 * A Class for a RabbitMQ Configuration file.
 * - [username], the credentials username
 * - [password], the credentials password
 * - [nodes], a list of addresses
 */
data class RabbitMQConfig(
    val username: String,
    val password: String,
    val nodes: List<RabbitAddress>,
)
