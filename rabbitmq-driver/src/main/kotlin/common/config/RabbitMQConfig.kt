package common.config


data class RabbitMQConfig(
    val username: String,
    val password: String,
    val nodes: List<RabbitAddress>
)
