package common.config

import org.apache.pulsar.client.api.SubscriptionType

data class ConsumerConfiguration(
    val receiverQueueSize: Int = 10000,
    val subscriptionType: SubscriptionType = SubscriptionType.Failover,
    val subscriptionName: String
)
